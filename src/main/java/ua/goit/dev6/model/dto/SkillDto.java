package ua.goit.dev6.model.dto;

import ua.goit.dev6.model.SkillLevel;

import java.util.Objects;

public class SkillDto {
    private Long id;
    private String language;
    private SkillLevel level;

    public SkillDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillDto)) return false;
        SkillDto skillDao = (SkillDto) o;
        return Objects.equals(id, skillDao.id) && Objects.equals(language, skillDao.language) && level == skillDao.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, level);
    }

    @Override
    public String toString() {
        return "SkillDto{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", level=" + level +
                '}';
    }
}
