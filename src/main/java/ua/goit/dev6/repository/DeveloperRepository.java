package ua.goit.dev6.repository;

import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.model.dao.DeveloperDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperRepository implements Repository<DeveloperDao> {
    private static final String INSERT = "INSERT INTO developers (first_name, last_name, age, salary) VALUES(?, ?, ?, ?)";

    private static final String DELETE = "DELETE FROM developers WHERE first_name = ? and last_name = ? and age =? and " +
            "salary = ?";
    private static final String FIND_BY_ID = "SELECT id, first_name, last_name, age, salary FROM developers where id = ?";
    private static final String UPDATE = "UPDATE DEVELOPERS SET first_name = ?, last_name = ?, age = ?, " +
            "salary = ? WHERE id = ? RETURNING id, first_name, last_name, age, salary";
    private static final String FIND_ALL = "SELECT id, first_name, last_name, age, salary FROM developers";
    private static final String FIND_ALL_BY_IDS = "SELECT id, first_name, last_name, age, salary FROM " +
            "developers WHERE id in (%s)";
    private static final String FIND_ALL_WITH_SKILL_ID = "SELECT id, developers_id, skills_id " +
            "FROM developers_skills_relation" +
            " WHERE skills_id = ?";
    private static final String FIND_ALL_WITH_SKILL_ID_LIST = "SELECT distinct d.id, d.first_name, d.last_name, " +
            "d.age, d.salary FROM developers d " +
            "INNER JOIN developers_skills_relation dsr ON d.id = dsr.developers_id " +
            "INNER JOIN skills s ON dsr.skills_id = s.id " +
            "WHERE s.id in (%s)";
    private static final String FIND_ALL_WITH_PROJECT_ID = "SELECT d.id, d.first_name, d.last_name, d.age, d.salary " +
            "FROM developers d " +
            "INNER JOIN projects_developers_relation pdr ON d.id = pdr.developers_id " +
            "INNER JOIN projects p ON pdr.projects_id = p.id " +
            "WHERE p.id = ?";
    private final DatabaseManagerConnector manager;

    public DeveloperRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public DeveloperDao save(DeveloperDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFirst_name());
            statement.setString(2, entity.getLast_name());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getSalary());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating developer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DeveloperCommand not created");
        }
        return entity;
    }

    @Override
    public void delete(DeveloperDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getFirst_name());
            statement.setString(2, entity.getLast_name());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete developer failed");
        }
    }

    @Override
    public Optional<DeveloperDao> findById(Long id) {
        DeveloperDao developerDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    developerDao = new DeveloperDao();
                    getEntity(resultSet, developerDao);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DeveloperCommand not updated");
        }
        return Optional.ofNullable(developerDao);
    }

    @Override
    public DeveloperDao update(DeveloperDao entity) {
        DeveloperDao developerDao = new DeveloperDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getFirst_name());
            statement.setString(2, entity.getLast_name());
            statement.setInt(3, entity.getAge());
            statement.setInt(4, entity.getSalary());
            statement.setLong(5, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, developerDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("DeveloperCommand not updated");
        }
        return developerDao;
    }

    @Override
    public List<DeveloperDao> findAll() {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL)) {
            List<DeveloperDao> result = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DeveloperDao developerDao = new DeveloperDao();
                getEntity(resultSet, developerDao);
                result.add(developerDao);
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Select all developers failed");
        }
    }

    @Override
    public List<DeveloperDao> findByListOfID(List<Long> idList) {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
        String stmt = String.format(FIND_ALL_BY_IDS,
                idList.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(",")));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(stmt)) {
            int index = 1;
            for (Long id : idList) {
                statement.setLong(index++, id);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DeveloperDao developerDao = new DeveloperDao();
                    getEntity(resultSet, developerDao);
                    developerDaoList.add(developerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Find developers failed");
        }
        return developerDaoList;
    }

    public List<DeveloperDao> findBySkillId(Long skillId) {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_SKILL_ID)) {
            statement.setLong(1, skillId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DeveloperDao developerDao = new DeveloperDao();
                    getEntity(resultSet, developerDao);
                    developerDaoList.add(developerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Find developers with skill failed");
        }
        return developerDaoList;
    }

    public List<DeveloperDao> findBySkillIdList(List<Long> skillIdList) {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
        String stmt = String.format(FIND_ALL_WITH_SKILL_ID_LIST,
                skillIdList.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(",")));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(stmt)) {
            int index = 1;
            for (Long id : skillIdList) {
                statement.setLong(index++, id);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DeveloperDao developerDao = new DeveloperDao();
                    getEntity(resultSet, developerDao);
                    developerDaoList.add(developerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Find developers with skills is failed.");
        }
        return developerDaoList;
    }

    public List<DeveloperDao> findByProjectId(Long projectId) {
        List<DeveloperDao> developerDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_PROJECT_ID)) {
            statement.setLong(1, projectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DeveloperDao developerDao = new DeveloperDao();
                    getEntity(resultSet, developerDao);
                    developerDaoList.add(developerDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select developers with project failed");
        }
        return developerDaoList;
    }

    protected static void getEntity(ResultSet resultSet, DeveloperDao developerDao) throws SQLException {
        developerDao.setId(resultSet.getLong("id"));
        developerDao.setFirst_name(resultSet.getString("first_name"));
        developerDao.setLast_name(resultSet.getString("last_name"));
        developerDao.setAge(resultSet.getInt("age"));
        developerDao.setSalary(resultSet.getInt("salary"));
    }
}

