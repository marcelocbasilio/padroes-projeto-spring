package com.marcelocbasilio.gof.service;

import com.marcelocbasilio.gof.model.Customer;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de customer. Com isso, se necessário, podemos ter múltiplas implementações dessa mesma interface.
 * @author marcelocbasilio
 */
public interface CustomerService {

    Iterable<Customer> findAll();

    Customer findById(long id);

    void create(Customer customer);

    void update(Long id, Customer customer);

    void delete(Long id);

}
