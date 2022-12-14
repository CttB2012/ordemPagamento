package com.example.teste.Teste.controller;

import com.example.teste.Teste.DTO.BeneficiariosDTO;
import com.example.teste.Teste.database.BeneficiariosDB;
import com.example.teste.Teste.entity.Beneficiarios;
import com.example.teste.Teste.entity.EnvelopDataJson;
import com.example.teste.Teste.services.interfaces.InterfaceBeneficiariosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/beneficiarios")
public class BeneficiariosController {



    private InterfaceBeneficiariosService service;

    public BeneficiariosController(InterfaceBeneficiariosService beneficiariosInterface) {
        this.service = beneficiariosInterface;
    }

    @GetMapping
    public ResponseEntity<List<BeneficiariosDB>> listAll() {
        List<BeneficiariosDB> listBeneficiarios = service.listAll();
        return ResponseEntity.ok().body(listBeneficiarios);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<BeneficiariosDTO> findById(@PathVariable Long id) {
        BeneficiariosDTO beneficiariosDTO = service.findById(id);
        return ResponseEntity.ok().body(beneficiariosDTO);
    }
    @PostMapping
    public EnvelopDataJson<BeneficiariosDTO> insert(@Valid @RequestBody Beneficiarios beneficiarios) throws Exception{
        var response = service.insert(beneficiarios);
        return new EnvelopDataJson<BeneficiariosDTO>(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Beneficiarios> update(@PathVariable Long id, @Valid @RequestBody Beneficiarios beneficiarios) {
        var beneficiariosService = service.update(id, beneficiarios);
        return ResponseEntity.ok().body(beneficiariosService);
    }
}
