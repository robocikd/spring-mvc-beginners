package com.packt.webstore.domain.repository.impl;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private List<Customer> customers = new ArrayList<>();

    public InMemoryCustomerRepository() {

        Customer cus1 = new Customer(1, "Adam", "20-000 Lublin, ul. Poważna 12/1");
        Customer cus2 = new Customer(1, "Damian", "22-000 Lublin, ul. Głęboka 12/33");
        Customer cus3 = new Customer(1, "Zenek", "14-000 Lublin, ul. Miła 1/1");

        customers.add(cus1);
        customers.add(cus2);
        customers.add(cus3);

    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }
}
