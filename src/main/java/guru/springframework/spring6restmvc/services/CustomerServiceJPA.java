package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDto);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(customerId).ifPresentOrElse(customer -> {
            customer.setCustomerName(customerDTO.getCustomerName());
            Customer savedCustomer = customerRepository.save(customer);
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(savedCustomer)));
        }, () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CustomerDTO> patchById(UUID id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(customer -> {
            if (StringUtils.hasText(customerDTO.getCustomerName())) {
                customer.setCustomerName(customerDTO.getCustomerName());
            }
            Customer savedCustomer = customerRepository.save(customer);
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(savedCustomer)));
        },
        () -> atomicReference.set(Optional.empty()));
        return atomicReference.get();
    }
}
