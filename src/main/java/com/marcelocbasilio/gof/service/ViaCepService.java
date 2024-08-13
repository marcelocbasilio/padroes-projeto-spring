package com.marcelocbasilio.gof.service;

import com.marcelocbasilio.gof.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Client HTTP, criado via <b>OpenFeign</b>, para o consumo da API do <b>ViaCEP</b>.
 *
 * @see <a href="https://spring.io/projects/spring-cloud-openfeign" target="__blank">Spring Cloud OpenFeign</a>
 * @see <a href="https://viacep.com.br" target="__blank">ViaCEP</a>
 *
 * @author marcelocbasilio
 */
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {

    @GetMapping("/{zip}/json/")
    Address searchAddressByCep(@PathVariable("zip") String zip);
}
