package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.User;
import TreeObjects.FemaleNames;
import TreeObjects.LocationData;
import TreeObjects.MaleNames;
import TreeObjects.Surnames;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FamilyTreeGeneratorTest {
    private Database db;
    private FamilyTreeGenerator familyTreeGenerator;
    private FemaleNames femaleNames;
    private MaleNames maleNames;
    private Surnames surnames;
    private LocationData locationData;
    private int generations;
    private int peopleCount;
    private int eventCount;
    private int birthYear;
    private int marriageYear;
    private int deathYear;
    private User user;
    private String gender;
    private PersonDao personDao;
    private EventDao eventDao;
    private Connection connection;

    private Gson gson = new Gson();


    @BeforeEach
    void setUp() throws FileNotFoundException, DataAccessException {
        familyTreeGenerator = new FamilyTreeGenerator();
        generations = 4;
        peopleCount = 0;
        eventCount = 0;
        birthYear = 1900;
        marriageYear = 1920;
        deathYear = 2000;
        user = new User("person", "password", "email", "person", "mont", "m", "personID");

        Reader reader = new FileReader("json/locations.json");
        locationData = (LocationData)gson.fromJson(reader, LocationData.class);

        Reader readerSurnames = new FileReader("json/snames.json");
        surnames = (Surnames)gson.fromJson(readerSurnames, Surnames.class);

        Reader readerFemaleNames = new FileReader("json/fnames.json");
        femaleNames = (FemaleNames)gson.fromJson(readerFemaleNames, FemaleNames.class);

        Reader readerMaleNames = new FileReader("json/mnames.json");
        maleNames = (MaleNames)gson.fromJson(readerMaleNames, MaleNames.class);

        db = new Database();
        connection = db.openConnection();
        personDao = new PersonDao(connection);
        eventDao = new EventDao(connection);
        personDao.clear();
        eventDao.clear();
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }


    @Test
    void generateDataPass() {
        familyTreeGenerator.generateData();
        assertNotNull(familyTreeGenerator);
    }


    @Test
    void generateFamilyTreePass() throws FileNotFoundException {
        familyTreeGenerator.generateFamilyTree(user.getUserName(), gender, generations, user, connection);
        assertNotNull(familyTreeGenerator);
        assertNotNull(personDao);
        assertNotNull(eventDao);
        assert (familyTreeGenerator.getPeopleCount() > 0);
        assert (familyTreeGenerator.getEventCount() > 0);
    }

    @Test
    void generateFamilyTreeFail() throws FileNotFoundException{
        familyTreeGenerator.generateFamilyTree(user.getUserName(), "4", -10, user, connection);
        assertEquals(0, familyTreeGenerator.getPeopleCount());
        assertEquals(0, familyTreeGenerator.getEventCount());
    }

    @Test
    void generatePeoplePass() throws SQLException, DataAccessException {
        familyTreeGenerator.generateData();
        familyTreeGenerator.generatePeople(user, user.getUserName(), gender, generations, birthYear, marriageYear, deathYear, connection);
        assertNotNull(familyTreeGenerator);
        assertNotNull(personDao);
        assertNotNull(eventDao);
    }
}