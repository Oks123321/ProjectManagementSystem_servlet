package ua.goit.dev6.repository;

import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.model.dao.ProjectDeveloperRelationDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectDeveloperRelationRepository implements Repository<ProjectDeveloperRelationDao> {
    private static final String INSERT = "INSERT INTO projects_developers_relation (projects_id, developers_id) VALUES(?,?)";
    private static final String DELETE = "DELETE FROM projects_developers_relation " +
            "WHERE projects_id = ? AND developers_id = ?";
    private static final String FIND_BY_ID = "SELECT id, projects_id, developers_id " +
            "FROM projects_developers_relation WHERE id = ?";
    private static final String UPDATE = "UPDATE projects_developers_relation " +
            "SET projects_id = ?, developers_id = ? WHERE id = ? " +
            "RETURNING id, projects_id, developers_id";
    private static final String FIND_ALL = "SELECT id, projects_id, developers_id FROM projects_developers_relation";
    private static final String FIND_ALL_WITH_IDS = "SELECT id, projects_id, developers_id " +
            "FROM projects_developers_relation" +
            " WHERE id IN (%s)";
    private final DatabaseManagerConnector manager;

    public ProjectDeveloperRelationRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public ProjectDeveloperRelationDao save(ProjectDeveloperRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Add developer to project failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Developer to project not added");
        }
        return entity;
    }

    @Override
    public void delete(ProjectDeveloperRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Developer from project not deleted");
        }
    }

    @Override
    public Optional<ProjectDeveloperRelationDao> findById(Long id) {
        ProjectDeveloperRelationDao pdRelationDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pdRelationDao = new ProjectDeveloperRelationDao();
                    getEntity(resultSet, pdRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relation between project and developer by id failed");
        }
        return Optional.ofNullable(pdRelationDao);
    }

    @Override
    public ProjectDeveloperRelationDao update(ProjectDeveloperRelationDao entity) {
        ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, entity.getProjectId());
            statement.setLong(2, entity.getDeveloperId());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, pdRelationDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation between project and developer not updated");
        }
        return pdRelationDao;
    }

    @Override
    public List<ProjectDeveloperRelationDao> findAll() {
        List<ProjectDeveloperRelationDao> pdRelationDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
                getEntity(resultSet, pdRelationDao);
                pdRelationDaoList.add(pdRelationDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all relation between projects and developers failed");
        }
        return pdRelationDaoList;
    }

    @Override
    public List<ProjectDeveloperRelationDao> findByListOfID(List<Long> idList) {
        List<ProjectDeveloperRelationDao> pdRelationDaoList = new ArrayList<>();
        String stmt = String.format(FIND_ALL_WITH_IDS,
                idList.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(", ")));
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(stmt)) {
            int index = 1;
            for( Long id : idList ) {
                statement.setLong(  index++, id );}
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProjectDeveloperRelationDao pdRelationDao = new ProjectDeveloperRelationDao();
                    getEntity(resultSet, pdRelationDao);
                    pdRelationDaoList.add(pdRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relations between projects and developers failed");
        }
        return pdRelationDaoList;
    }

    private static void getEntity(ResultSet resultSet, ProjectDeveloperRelationDao pdRelationDao) throws SQLException {
        pdRelationDao.setId(resultSet.getLong("id"));
        pdRelationDao.setProjectId(resultSet.getLong("developer_id"));
        pdRelationDao.setDeveloperId(resultSet.getLong("project_id"));
    }
}
