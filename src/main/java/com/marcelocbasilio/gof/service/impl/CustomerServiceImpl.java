package com.marcelocbasilio.gof.service.impl;

import com.marcelocbasilio.gof.model.Address;
import com.marcelocbasilio.gof.model.AddressRepository;
import com.marcelocbasilio.gof.model.Customer;
import com.marcelocbasilio.gof.model.CustomerRepository;
import com.marcelocbasilio.gof.service.CustomerService;
import com.marcelocbasilio.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link CustomerService}, a qual pode ser injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author marcelocbasilio
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    // Singleton | Injetar os componentes do Spring com @Autowired.
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ViaCepService viaCepService;

    // Strategy | Implementar os métodos definidos na interface.
    // Facade | Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Customer> findAll() {
        // Buscar todos os clientes.
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(long id) {
        // Buscar cliente por id.
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.get();
    }

    @Override
    public void create(Customer customer) {
        saveCustomerWithZip(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        // Buscar cliente por id, caso exista:
        Optional<Customer> customerDb = customerRepository.findById(id);
        if (customerDb.isPresent()) {
            saveCustomerWithZip(customer);
        }
    }

    @Override
    public void delete(Long id) {
        // Excluir cliente por id.
        customerRepository.deleteById(id);
    }

    private void saveCustomerWithZip(Customer customer) {
        // Verificar se o Endereço do Cliente já existe (pelo CEP).
        String zip = customer.getAddress().getZip();
        Address address = addressRepository.findById(Long.valueOf(zip)).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Address newAddress = viaCepService.searchAddressByCep(zip);
            addressRepository.save(newAddress);
            return newAddress;
        });
        customer.setAddress(address);
        // Inserir cliente, vinculando o endereço (novo ou existente).
        customerRepository.save(customer);
    }
}
