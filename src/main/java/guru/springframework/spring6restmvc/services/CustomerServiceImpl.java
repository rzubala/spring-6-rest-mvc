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
}
