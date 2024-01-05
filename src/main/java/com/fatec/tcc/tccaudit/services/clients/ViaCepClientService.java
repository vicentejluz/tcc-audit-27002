package com.fatec.tcc.tccaudit.services.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fatec.tcc.tccaudit.models.dto.AddressDTO;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface ViaCepClientService {
    @GetMapping("{postalCode}/json")
    AddressDTO findAddressByPostalCode(@PathVariable("postalCode") String postalCode);
}