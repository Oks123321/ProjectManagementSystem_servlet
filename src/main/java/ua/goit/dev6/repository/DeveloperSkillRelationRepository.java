package ua.goit.dev6.repository;

import ua.goit.dev6.config.DatabaseManagerConnector;
import ua.goit.dev6.model.dao.DeveloperSkillRelationDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeveloperSkillRelationRepository implements Repository<DeveloperSkillRelationDao>{
    private static final String INSERT = "INSERT INTO developers_skills_relation(developers_id, skills_id) values(?,?)";
    private static final String DELETE = "DELETE FROM developers_skills_relation " +
            "WHERE developers_id = ? AND skills_id = ?";
    private static final String FIND_BY_ID = "SELECT id, developers_id, skills_id " +
            "FROM developers_skills_relation WHERE id = ?";
    private static final String UPDATE = "UPDATE developers_skills_relation " +
            "SET developers_id = ?, skills_id = ? WHERE id = ? " +
            "RETURNING id, developers_id, skills_id";
    private static final String FIND_ALL = "SELECT id, developers_id, skills_id FROM developers_skills_relation";
    private static final String FIND_ALL_WITH_IDS = "SELECT id, developers_id, skills_id FROM developers_skills_relation" +
            " WHERE id IN (%s)";
    private static final String FIND_ALL_WITH_DEVELOPER_ID = "SELECT id, developers_id, skills_id " +
            "FROM developers_skills_relation" +
            " WHERE developers_id = ?";
    private final DatabaseManagerConnector manager;

    public DeveloperSkillRelationRepository(DatabaseManagerConnector manager) {
        this.manager = manager;
    }

    @Override
    public DeveloperSkillRelationDao save(DeveloperSkillRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Add skill to developer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Skill to developer not added");
        }
        return entity;
    }

    @Override
    public void delete(DeveloperSkillRelationDao entity) {
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Skill from developer not deleted");
        }
    }

    @Override
    public Optional<DeveloperSkillRelationDao> findById(Long id) {
        DeveloperSkillRelationDao dsRelationDao = null;
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    dsRelationDao = new DeveloperSkillRelationDao();
                    getEntity(resultSet, dsRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relation between developer and skill by id failed");
        }
        return Optional.ofNullable(dsRelationDao);
    }

    @Override
    public DeveloperSkillRelationDao update(DeveloperSkillRelationDao entity) {
        DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setLong(1, entity.getDeveloperId());
            statement.setLong(2, entity.getSkillId());
            statement.setLong(3, entity.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    getEntity(resultSet, dsRelationDao);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Relation between developer and skill not updated");
        }
        return dsRelationDao;
    }

    @Override
    public List<DeveloperSkillRelationDao> findAll() {
        List<DeveloperSkillRelationDao> dsRelationDaoList = new ArrayList<>();
        try (Connection connection = manager.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
                getEntity(resultSet, dsRelationDao);
                dsRelationDaoList.add(dsRelationDao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select all relation between developers and skills failed");
        }
        return dsRelationDaoList;
    }

    @Override
    public List<DeveloperSkillRelationDao> findByListOfID(List<Long> idList) {
        List<DeveloperSkillRelationDao> dsRelationDaoList = new ArrayList<>();
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
                    DeveloperSkillRelationDao dsRelationDao = new DeveloperSkillRelationDao();
                    getEntity(resultSet, dsRelationDao);
                    dsRelationDaoList.add(dsRelationDao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Select relations between developers and skills failed");
        }
        return dsRelationDaoList;
    }

    private static void getEntity(ResultSet resultSet, DeveloperSkillRelationDao dsRelationDao) throws SQLException {
        dsRelationDao.setId(resultSet.getLong("id"));
        dsRelationDao.setDeveloperId(resultSet.getLong("project_id"));
        dsRelationDao.setSkillId(resultSet.getLong("skill_id"));
    }

}
