package ua.goit.dev6.service;

import ua.goit.dev6.model.dao.CustomerDao;
import ua.goit.dev6.model.dto.CustomerDto;
import ua.goit.dev6.repository.CustomerRepository;
import ua.goit.dev6.service.converter.CustomersConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomersConverter customersConverter;

    public CustomerService(CustomerRepository customerRepository, CustomersConverter customersConverter) {
        this.customerRepository = customerRepository;
        this.customersConverter = customersConverter;
    }

    public CustomerDto create(CustomerDto customerDto) {
        CustomerDao customerDao = customerRepository.save(customersConverter.to(customerDto));
        return customersConverter.from(customerDao);
    }

    public Optional<CustomerDto> findById(Long id) {
        Optional<CustomerDao> companyDao = customerRepository.findById(id);
        return companyDao.map(customersConverter::from);
    }

    public CustomerDto update(CustomerDto customerDto) {
        CustomerDao customerDao = customerRepository.update(customersConverter.to(customerDto));
        return customersConverter.from(customerDao);
    }

    public void delete(CustomerDto customerDto) {
        customerRepository.delete(customersConverter.to(customerDto));
    }

    public List<CustomerDto> findAll(){
        return customerRepository.findAll().stream()
                .map(customersConverter::from)
                .collect(Collectors.toList());
    }
}
