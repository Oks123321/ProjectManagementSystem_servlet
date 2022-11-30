package ua.goit.dev6.service;


import ua.goit.dev6.model.SkillLevel;
import ua.goit.dev6.model.dao.DeveloperDao;
import ua.goit.dev6.model.dao.DeveloperSkillRelationDao;
import ua.goit.dev6.model.dao.SkillDao;
import ua.goit.dev6.model.dto.DeveloperDto;
import ua.goit.dev6.repository.DeveloperRepository;
import ua.goit.dev6.repository.DeveloperSkillRelationRepository;
import ua.goit.dev6.repository.SkillRepository;
import ua.goit.dev6.service.converter.DeveloperConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperService {
    private final DeveloperRepository developerRepository;
    private final DeveloperSkillRelationRepository developerSkillRelationRepository;
    private final SkillRepository skillRepository;
    private final DeveloperConverter developerConverter;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperSkillRelationRepository developerSkillRelationRepository, SkillRepository skillRepository, DeveloperConverter developerConverter) {
        this.developerRepository = developerRepository;
        this.developerSkillRelationRepository = developerSkillRelationRepository;
        this.skillRepository = skillRepository;
        this.developerConverter = developerConverter;
    }

    public DeveloperDto create(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.save(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public Optional<DeveloperDto> findById(Long id) {
        Optional<DeveloperDao> developerDao = developerRepository.findById(id);
        return developerDao.map(developerConverter::from);
    }
    public List<DeveloperDto> findAll(){
        return developerRepository.findAll().stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }

    public DeveloperDto update(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerRepository.update(developerConverter.to(developerDto));
        return developerConverter.from(developerDao);
    }

    public void delete(DeveloperDto developerDto) {
        developerRepository.delete(developerConverter.to(developerDto));
    }
    public List<DeveloperDto> findByProjectId(Long id){
        return developerRepository.findByProjectId(id).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public List<DeveloperDto> findBySkillId(Long id){
        return developerRepository.findBySkillId(id).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public long addSkill(Long developerId, Long skillId){
        DeveloperSkillRelationDao dao = new DeveloperSkillRelationDao();
        dao.setDeveloperId(developerId);
        dao.setSkillId(skillId);
        return developerSkillRelationRepository.save(dao).getId();
    }
    public void deleteSkill(Long developerId, Long skillId){
        DeveloperSkillRelationDao dao = new DeveloperSkillRelationDao();
        dao.setDeveloperId(developerId);
        dao.setSkillId(skillId);
        developerSkillRelationRepository.delete(dao);
    }
    public List<DeveloperDto> findBySkillLevel(SkillLevel skillLevel){
        List<Long> skillIds = skillRepository.findByLevel(skillLevel).stream()
                .map(SkillDao::getId)
                .collect(Collectors.toList());
        return developerRepository.findBySkillIdList(skillIds).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }
    public List<DeveloperDto> findBySkillLanguage(String language){
        List<Long> skillIds = skillRepository.findByLanguage(language).stream()
                .map(SkillDao::getId)
                .collect(Collectors.toList());
        return developerRepository.findBySkillIdList(skillIds).stream()
                .map(developerConverter::from)
                .collect(Collectors.toList());
    }

}
