package ua.goit.dev6.repository;

import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.model.dao.ProjectDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectRepository implements Repository<ProjectDao> {
    private static final String INSERT = "INSERT INTO projects(name, descriptions, cost, date) VALUES(?,?,?,?)";
    private static final String DELETE = "DELETE FROM projects WHERE name = ? and descriptions = ? and cost = ? and " +
            "date = ?";
    private static final String FIND_BY_ID = "SELECT id, name, descriptions, cost, date FROM projects WHERE id = ?";
    private static final String UPDATE = "UPDATE projects SET name = ?, descriptions = ?, cost = ?, date = ?  " +
            "WHERE id = ? RETURNING id, name, descriptions, cost, date";
    private static final String FIND_ALL = "SELECT id, name, descriptions, cost, date FROM projects";
    private static final String FIND_ALL_WITH_IDS = "SELECT id, name, descriptions, cost, date FROM projects " +
            "WHERE id IN (%s)";
    private static final String FIND_ALL_WITH_DEVELOPER_ID = "SELECT id, projects_id, developers_id " +
            "FROM projects_developers_relation" +
            " WHERE developers_id = ?";
    private final DatabaseManagerConnector manager;

    public ProjectRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public ProjectDao save(ProjectDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescriptions());
            statement.setInt(3, entity.getCost());
            statement.setObject(4, entity.getDate(), Types.DATE);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating project failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not created");
        }
        return entity;
    }

    @Override
    public void delete(ProjectDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescriptions());
            statement.setInt(3, entity.getCost());
            statement.setObject(4, entity.getDate(), Types.DATE);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Delete project failed");
        }
    }

    @Override
    public Optional<ProjectDao> findById(Long id) {
        ProjectDao projectDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select project by id failed");
        }
        return Optional.ofNullable(projectDao);
    }

    @Override
    public ProjectDao update(ProjectDao entity) {
        ProjectDao projectDao = new ProjectDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescriptions());
            statement.setInt(3,entity.getCost());
            statement.setObject(4, entity.getDate(),Types.DATE);
            statement.setLong(5, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, projectDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Project not updated");
        }
        return projectDao;
    }

    @Override
    public List<ProjectDao> findAll() {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProjectDao projectDao = new ProjectDao();
                getEntity(resultSet, projectDao);
                projectDaoList.add(projectDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all projects failed");
        }
        return projectDaoList;
    }

    @Override
    public List<ProjectDao> findByListOfID(List<Long> idList) {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        String stmt = String.format(FIND_ALL_WITH_IDS,
                idList.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(", ")));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(stmt)) {
            int index = 1;
            for (Long id : idList) {
                statement.setLong(index++, id);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                    projectDaoList.add(projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select projects failed");
        }
        return projectDaoList;
    }

    public List<ProjectDao> findByDeveloperId(Long developerId) {
        List<ProjectDao> projectDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_WITH_DEVELOPER_ID)) {
            statement.setLong(1, developerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDao projectDao = new ProjectDao();
                    getEntity(resultSet, projectDao);
                    projectDaoList.add(projectDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select projects with developer failed");
        }
        return projectDaoList;
    }

    private static void getEntity(ResultSet resultSet, ProjectDao projectDao) throws SQLException {
        projectDao.setId(resultSet.getLong("id"));
        projectDao.setName(resultSet.getString("name"));
        projectDao.setDescriptions(resultSet.getString("descriptions"));
        projectDao.setCost(resultSet.getInt("cost"));
        projectDao.setDate(resultSet.getObject("date"));

    }
}