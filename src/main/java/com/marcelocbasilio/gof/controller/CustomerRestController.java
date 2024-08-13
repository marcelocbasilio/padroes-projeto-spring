package com.marcelocbasilio.gof.controller;

import com.marcelocbasilio.gof.model.Customer;
import com.marcelocbasilio.gof.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma interface simples e coesa (API REST).
 *
 * @author marcelocbasilio
 */
@RestController
@RequestMapping("customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<Iterable<Customer>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customerService.create(customer);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        customerService.update(id, customer);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
