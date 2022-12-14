package com.example.teste.Teste.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
//        String error = "Categoria não encontrada ou não existente";
//        HttpStatus status = HttpStatus.NOT_FOUND;
//        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
//        return ResponseEntity.status(status).body(err);
//    }
//    @ExceptionHandler
//    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
//
//        String error = "Database error";
//        HttpStatus status = HttpStatus.NOT_FOUND;
//        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
//        return ResponseEntity.status(status).body(err);
//    }
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponseModel> handleApiExceptionApiOrdem(ExceptionApiOrdem ex, WebRequest request){
        ExceptionResponseModel  error = new ExceptionResponseModel(ex.getCodigoErro(), ex.getMsgCustom(), ex.getMessage());
        error.setCampos(ex.getObjetosSaida());
        return new ResponseEntity<>(error, ex.getStatus());
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorRequest> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorRequest errorRequest = ErrorRequest.builder()
                    .defaultMessage(error.getDefaultMessage())
                    .field(((FieldError) error).getField())
                    .build();
            errors.add(errorRequest);
        });

        ExceptionResponseModel problem = ExceptionResponseModel.builder()
                .dataHora(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(errors)
                .build();

        return new ResponseEntity<>(problem,HttpStatus.BAD_REQUEST);
    }
}
