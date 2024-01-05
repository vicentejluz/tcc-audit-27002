package com.fatec.tcc.tccaudit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.tcc.tccaudit.models.dto.AddressDTO;
import com.fatec.tcc.tccaudit.services.clients.ViaCepClientService;
import com.fatec.tcc.tccaudit.services.exceptions.ResourceNotFoundException;

import feign.FeignException;

@Service
public class ViaCepClientServiceImpl {
    @Autowired
    private ViaCepClientService viaCepClientService;

    public AddressDTO findAddressByPostalCode(String postalCode) {
        try {
            AddressDTO addressDTO = viaCepClientService.findAddressByPostalCode(postalCode);
            if (addressDTO.street() == null || addressDTO.city() == null || addressDTO.state() == null) {
                throw new ResourceNotFoundException("CEP não encontrado!");
            }
            return addressDTO;
        } catch (FeignException e) {
            throw new RuntimeException("Error while accessing ViaCep service: " + e.getMessage());
        }
    }

}