package ua.goit.dev6.service;

import ua.goit.dev6.model.dao.CompanyDao;
import ua.goit.dev6.model.dto.CompanyDto;
import ua.goit.dev6.repository.CompanyRepository;
import ua.goit.dev6.service.converter.CompanyConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    public CompanyService(CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    public CompanyDto create(CompanyDto companyDto) {
        CompanyDao companyDao = companyRepository.save(companyConverter.to(companyDto));
        return companyConverter.from(companyDao);
    }

    public Optional<CompanyDto> findById(Long id) {
        Optional<CompanyDao> companyDao = companyRepository.findById(id);
        return companyDao.map(companyConverter::from);
    }

    public CompanyDto update(CompanyDto companyDto) {
        CompanyDao companyDao = companyRepository.update(companyConverter.to(companyDto));
        return companyConverter.from(companyDao);
    }

    public void delete(CompanyDto companyDto) {
        companyRepository.delete(companyConverter.to(companyDto));
    }

    public List<CompanyDto> findAll(){
        return companyRepository.findAll().stream()
                .map(companyConverter::from)
                .collect(Collectors.toList());
    }
}
