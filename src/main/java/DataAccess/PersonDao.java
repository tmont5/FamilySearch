package DataAccess;

import Model.Person;
import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    public void insertSpouseID(String spouseID, String personID) throws DataAccessException {
        String sql = "UPDATE Person SET spouseID = ? WHERE personID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, spouseID);
            stmt.setString(2, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting spouseID into the database");
        }
    }
    /**
     * Inserts a person into the database
     * @param person
     * @throws DataAccessException
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Finds a person in the database
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Person find(String personID) throws DataAccessException {
        if(personID == null){
            throw new DataAccessException("PersonID is null");
        }
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
    }


    public Person[] findAll (String associatedUsername) throws DataAccessException {
        if (associatedUsername == null || associatedUsername.equals("")) {
            throw new DataAccessException("associatedUsername is null");
        }
        Person person = null;
        Person[] personArray = null;
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"), rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("gender"), rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                persons.add(person);//add person to array
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        }
        personArray = persons.toArray(new Person[persons.size()]);
        if(personArray.length == 0){
            return null;
        }
        return personArray;
    }

    /**
     * Deletes all persons associated with a user
     * @throws DataAccessException
     */
    public void clear () throws DataAccessException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Person")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing Person");
        }
    }

    /**
     * Deletes all persons associated with a user
     * @param username
     * @throws DataAccessException
     */
    public void deleteAll (String username) throws DataAccessException {
        if (username == null || username.equals("")) {
            throw new DataAccessException("username is null");
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Person WHERE associatedUsername = ?")) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing Person");
        }
    }

    /**
     * Deletes a person from the database
     * @param personID
     * @throws DataAccessException
     */
    public void delete (String personID) throws DataAccessException {
        if (personID == null || personID.equals("")) {
            throw new DataAccessException("personID is null");
        }
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Person WHERE personID = ?")) {
            if(find(personID) == null){
                throw new DataAccessException("Person does not exist");
            }
            stmt.setString(1, personID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL Error encountered while clearing Person");
        }
    }


}