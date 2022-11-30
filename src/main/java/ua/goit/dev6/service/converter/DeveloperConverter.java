package ua.goit.dev6.service.converter;

import ua.goit.dev6.model.dao.DeveloperDao;
import ua.goit.dev6.model.dto.DeveloperDto;

public class DeveloperConverter implements Converter<DeveloperDto, DeveloperDao> {
    @Override
    public DeveloperDto from(DeveloperDao developerDao) {
        DeveloperDto developerDto = new DeveloperDto();
        developerDto.setId(developerDao.getId());
        developerDto.setFirst_name(developerDao.getFirst_name());
        developerDto.setLast_name(developerDao.getLast_name());
        developerDto.setAge(developerDao.getAge());
        developerDto.setSalary(developerDao.getSalary());
        return developerDto;
    }

    @Override
    public DeveloperDao to(DeveloperDto developerDto) {
        DeveloperDao developerDao = new DeveloperDao();
        developerDao.setId(developerDto.getId());
        developerDao.setFirst_name(developerDto.getFirst_name());
        developerDao.setLast_name(developerDto.getLast_name());
        developerDao.setAge(developerDto.getAge());
        developerDao.setSalary(developerDto.getSalary());
        return developerDao;
    }
}
