package ua.goit.dev6.service;

import ua.goit.dev6.model.SkillLevel;
import ua.goit.dev6.model.dao.SkillDao;
import ua.goit.dev6.model.dto.SkillDto;
import ua.goit.dev6.repository.SkillRepository;
import ua.goit.dev6.service.converter.SkillConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillConverter skillConverter;

    public SkillService(SkillRepository skillRepository, SkillConverter skillConverter) {
        this.skillRepository = skillRepository;
        this.skillConverter = skillConverter;
    }

    public SkillDto create(SkillDto skillDto) {
        SkillDao skillDao = skillRepository.save(skillConverter.to(skillDto));
        return skillConverter.from(skillDao);
    }

    public Optional<SkillDto> findById(Long id) {
        Optional<SkillDao> skillDao = skillRepository.findById(id);
        return skillDao.map(skillConverter::from);
    }

    public SkillDto update(SkillDto skillDto) {
        SkillDao skillDao = skillRepository.update(skillConverter.to(skillDto));
        return skillConverter.from(skillDao);
    }

    public void delete(SkillDto skillDto) {
        skillRepository.delete(skillConverter.to(skillDto));
    }
    public List<SkillDto> findAll(){
        return skillRepository.findAll().stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
    public List<SkillDto> findByLanguage(String language){
        return skillRepository.findByLanguage(language).stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
    public List<SkillDto> findByLevel(SkillLevel skillLevel){
        return skillRepository.findByLevel(skillLevel).stream()
                .map(skillConverter::from)
                .collect(Collectors.toList());
    }
}
