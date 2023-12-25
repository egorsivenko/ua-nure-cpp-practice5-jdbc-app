package ua.nure.cpp.sivenko.practice5.dao.mysql;

import ua.nure.cpp.sivenko.practice5.util.ConnectionFactory;
import ua.nure.cpp.sivenko.practice5.dao.PawnbrokerDAO;
import ua.nure.cpp.sivenko.practice5.model.ItemCategory;
import ua.nure.cpp.sivenko.practice5.model.Pawnbroker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PawnbrokerDAOMySQlImpl implements PawnbrokerDAO {
    private static final String GET_BY_ID = "SELECT * FROM pawnbrokers WHERE pawnbroker_id = ?";
    private static final String GET_BY_CONTACT_NUMBER = "SELECT * FROM pawnbrokers WHERE contact_number = ?";
    private static final String GET_BY_EMAIL = "SELECT * FROM pawnbrokers WHERE email = ?";
    private static final String GET_ALL = "SELECT * FROM pawnbrokers";

    private static final String INSERT = "INSERT INTO pawnbrokers (first_name, last_name, birthdate, contact_number, email, address) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE pawnbrokers " +
            "SET first_name = ?, last_name = ?, birthdate = ?, contact_number = ?, email = ?, address = ? WHERE pawnbroker_id = ?";
    private static final String DELETE = "DELETE FROM pawnbrokers WHERE pawnbroker_id = ?";

    private static final String INSERT_PAWNBROKER_SPECIALIZATION = "INSERT INTO pawnbroker_specialization VALUES (?, ?)";

    @Override
    public Pawnbroker getPawnbrokerById(long pawnbrokerId) {
        if (pawnbrokerId < 1) {
            throw new IllegalArgumentException("Pawnbroker id cannot be <= 0");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, pawnbrokerId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapPawnbroker(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pawnbroker getPawnbrokerByContactNumber(String contactNumber) {
        String contactNumberRegex = "[0-9]{3}-[0-9]{3}-[0-9]{4}";
        if (!contactNumber.matches(contactNumberRegex)) {
            throw new IllegalArgumentException("Invalid contact number");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_CONTACT_NUMBER)) {
            ps.setString(1, contactNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapPawnbroker(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Pawnbroker getPawnbrokerByEmail(String email) {
        String emailRegex = "^[^@]+@[^@]+\\.[^@]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BY_EMAIL)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return mapPawnbroker(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pawnbroker> getAllPawnbrokers() {
        List<Pawnbroker> pawnbrokers = new ArrayList<>();

        try (Connection connection = ConnectionFactory.createMySQLConnection();
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(GET_ALL)) {
                while (rs.next()) {
                    pawnbrokers.add(mapPawnbroker(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pawnbrokers;
    }

    @Override
    public void addPawnbroker(Pawnbroker pawnbroker) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement ps_pawn_spec = connection.prepareStatement(INSERT_PAWNBROKER_SPECIALIZATION)) {
            ps.setString(1, pawnbroker.getFirstName());
            ps.setString(2, pawnbroker.getLastName());
            ps.setDate(3, Date.valueOf(pawnbroker.getBirthdate()));
            ps.setString(4, pawnbroker.getContactNumber());
            ps.setString(5, pawnbroker.getEmail());
            ps.setString(6, pawnbroker.getAddress());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    for (ItemCategory itemCategory : pawnbroker.getSpecializations()) {
                        long itemCategoryId = itemCategory.getItemCategoryId();
                        ps_pawn_spec.setLong(1, keys.getLong(1)); // pawnbrokerId
                        ps_pawn_spec.setLong(2, itemCategoryId);

                        ps_pawn_spec.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePawnbroker(Pawnbroker pawnbroker) {
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, pawnbroker.getFirstName());
            ps.setString(2, pawnbroker.getLastName());
            ps.setDate(3, Date.valueOf(pawnbroker.getBirthdate()));
            ps.setString(4, pawnbroker.getContactNumber());
            ps.setString(5, pawnbroker.getEmail());
            ps.setString(6, pawnbroker.getAddress());
            ps.setLong(7, pawnbroker.getPawnbrokerId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePawnbroker(long pawnbrokerId) {
        if (pawnbrokerId < 1) {
            throw new IllegalArgumentException("Pawnbroker id cannot be <= 0");
        }
        try (Connection connection = ConnectionFactory.createMySQLConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, pawnbrokerId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Pawnbroker mapPawnbroker(ResultSet rs) throws SQLException {
        Pawnbroker pawnbroker = new Pawnbroker();
        pawnbroker.setPawnbrokerId(rs.getLong("pawnbroker_id"));
        pawnbroker.setFirstName(rs.getString("first_name"));
        pawnbroker.setLastName(rs.getString("last_name"));
        pawnbroker.setBirthdate(rs.getDate("birthdate").toLocalDate());
        pawnbroker.setContactNumber(rs.getString("contact_number"));
        pawnbroker.setEmail(rs.getString("email"));
        pawnbroker.setAddress(rs.getString("address"));
        return pawnbroker;
    }
}
