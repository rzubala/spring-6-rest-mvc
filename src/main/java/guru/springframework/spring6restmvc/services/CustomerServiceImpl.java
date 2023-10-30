package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
            .id(UUID.randomUUID())
            .customerName("Customer First")
            .version(1)
            .createdDate(LocalDateTime.now())
            .lastModifiedDate(LocalDateTime.now()).build();
        this.customerMap.put(customer1.getId(), customer1);

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Customer Second")
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now()).build();
        this.customerMap.put(customer2.getId(), customer2);
    }

    @Override
    public List<Customer> listCustomers() {
        return new LinkedList<>(this.customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return this.customerMap.get(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();

        this.customerMap.put(newCustomer.getId(), newCustomer);

        return newCustomer;
    }

    @Override
    public Customer updateCustomerById(UUID customerId, Customer customer) {
        Customer existingCustomer = this.customerMap.get(customerId);
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setLastModifiedDate(LocalDateTime.now());
        return existingCustomer;
    }

    @Override
    public void deleteCustomerById(UUID id) {
        this.customerMap.remove(id);
    }


}
