package ua.goit.dev6.model.dao;

import ua.goit.dev6.model.SkillLevel;

import java.util.Objects;

public class SkillDao {
    private Long id;
    private String language;
    private SkillLevel level;

    public SkillDao() {

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
        if (!(o instanceof SkillDao)) return false;
        SkillDao skillDao = (SkillDao) o;
        return Objects.equals(id, skillDao.id) && Objects.equals(language, skillDao.language) && level == skillDao.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, level);
    }

    @Override
    public String toString() {
        return "SkillDao{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", level=" + level +
                '}';
    }
}
