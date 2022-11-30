package ua.goit.dev6.service.converter;

import ua.goit.dev6.model.dao.CustomerDao;
import ua.goit.dev6.model.dto.CustomerDto;

public class CustomersConverter implements Converter<CustomerDto, CustomerDao> {
    @Override
    public CustomerDto from(CustomerDao customerDao) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerDao.getId());
        customerDto.setName(customerDao.getName());
        customerDto.setDescriptions(customerDao.getDescriptions());
        return customerDto;
    }

    @Override
    public CustomerDao to(CustomerDto customerDto) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setId(customerDto.getId());
        customerDao.setName(customerDto.getName());
        customerDao.setDescriptions(customerDto.getDescriptions());
        return customerDao;
    }
}
