package ua.goit.dev6.repository;

import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.model.SkillLevel;
import ua.goit.dev6.model.dao.SkillDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SkillRepository implements Repository<SkillDao> {
    private final DatabaseManagerConnector manager;
    private static final String INSERT = "INSERT INTO skills (language, level) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM skills WHERE language = ? AND level =?";
    private static final String UPDATE = "UPDATE skills SET language = ?, level = ? WHERE id = ? " +
            "RETURNING id, language, level";
    private static final String FIND_BY_ID = "SELECT id, language, level FROM skills WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, language, level FROM skills";
    private static final String FIND_ALL_WITH_IDS = "SELECT id, language, level FROM skills WHERE id IN (%s)";

    private static final String FIND_ALL_WITH_LANGUAGE = "SELECT id, language, level FROM skills WHERE language = ?";

    private static final String FIND_ALL_WITH_LEVEL = "SELECT id, language, level FROM skills WHERE level = ?";

    private static final String FIND_ALL_WITH_DEVELOPER_ID = "SELECT id, developers_id, skills_id " +
            "FROM developers_skills_relation" +
            " WHERE developers_id = ?";
    public SkillRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public SkillDao save(SkillDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating skill failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill not created");
        }
        return entity;
    }

    @Override
    public void delete(SkillDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete skill failed");
        }
    }

    @Override
    public SkillDao update(SkillDao entity) {
        SkillDao skillDao = new SkillDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getLanguage());
            statement.setString(2, entity.getLevel().name());
            statement.setLong(3,entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, skillDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("SkillCommand not updated");
        }
        return skillDao;
    }

      @Override
    public Optional<SkillDao> findById(Long id) {
        SkillDao skillDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skill by id failed");
        }
        return Optional.ofNullable(skillDao);
    }

    @Override
    public List<SkillDao> findAll() {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            List<SkillDao> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SkillDao skillDao = new SkillDao();
                getEntity(resultSet, skillDao);
                result.add(skillDao);
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Select all skills failed");
        }
    }


    @Override
    public List<SkillDao> findByListOfID(List<Long> idList) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        String stmt = String.format(FIND_ALL_WITH_IDS,
                idList.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(", ")));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_IDS)) {
            int index = 1;
            for (Long id : idList) {
                statement.setLong(index++, id);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills failed");
        }
        return skillDaoList;
    }

    public List<SkillDao> findByLanguage(String language) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_LANGUAGE)) {
            statement.setString(1, language);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with language failed");
        }
        return skillDaoList;
    }

    public List<SkillDao> findByLevel(SkillLevel level) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_LEVEL)) {
            statement.setString(1, level.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with level failed");
        }
        return skillDaoList;
    }

    public List<SkillDao> findByDeveloperId(Long developerId) {
        List<SkillDao> skillDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_DEVELOPER_ID)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SkillDao skillDao = new SkillDao();
                    getEntity(resultSet, skillDao);
                    skillDaoList.add(skillDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select skills with developerId failed");
        }
        return skillDaoList;
    }

    private static void getEntity(ResultSet resultSet, SkillDao skillDao) throws SQLException {
        skillDao.setId(resultSet.getLong("id"));
        skillDao.setLanguage(resultSet.getString("language"));
        skillDao.setLevel(SkillLevel.valueOf(resultSet.getString("level")));
    }
}
