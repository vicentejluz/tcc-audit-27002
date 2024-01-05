package com.fatec.tcc.tccaudit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.tcc.tccaudit.models.dto.AddressDTO;
import com.fatec.tcc.tccaudit.services.impl.ViaCepClientServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/via-cep")
public class AddressController {
    @Autowired
    private ViaCepClientServiceImpl viaCepServiceImpl;

    @GetMapping
    public ResponseEntity<AddressDTO> findAddressByPostalCode(@RequestBody String postalCode) {
        AddressDTO addressDTO = viaCepServiceImpl.findAddressByPostalCode(postalCode);
        return ResponseEntity.ok(addressDTO);
    }
}