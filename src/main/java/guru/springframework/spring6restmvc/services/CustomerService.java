package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO createCustomer(CustomerDTO customer);

    CustomerDTO updateCustomerById(UUID customerId, CustomerDTO customer);

    void deleteCustomerById(UUID id);

    void patchById(UUID id, CustomerDTO customer);
}
