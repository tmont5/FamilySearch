package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.Event;
import Model.Person;
import TreeObjects.Location;
import TreeObjects.LocationData;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class EventGeneratorTest {
    private Gson gson = new Gson();

    private EventGenerator eventGenerator;
    private LocationData locationData;
    private int year;
    private Person person1;
    private Person person2;
    private Event event1;
    private Event event2;
    private Event[] events;
    private Location randomLocation;


    @BeforeEach
    void setUp() throws DataAccessException, FileNotFoundException {
        Reader reader = new FileReader("json/locations.json");
        locationData = (LocationData)gson.fromJson(reader, LocationData.class);
        eventGenerator = new EventGenerator();
        randomLocation = locationData.getRandomLocation();
        year = 2000;
        person1 = new Person("person1", "username", "first", "last", "m", "father", "mother", "spouse");
        person2 = new Person("person2", "username", "first", "last", "m", "father", "mother", "spouse");
    }



    @Test
    void marriagePass() {
        events = eventGenerator.marriage(person1, person2, year, locationData);
        assertEquals(events[0].getEventType(), "Marriage");
        assertEquals(events[1].getEventType(), "Marriage");
        assertEquals(events[0].getPersonID(), person1.getPersonID());
        assertEquals(events[1].getPersonID(), person2.getPersonID());
        assertEquals(events[0].getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(events[1].getAssociatedUsername(), person2.getAssociatedUsername());
        assertEquals(events[0].getEventID(), person1.getPersonID() + "_marriage");
        assertEquals(events[1].getEventID(), person2.getPersonID() + "_marriage");

    }

    @Test
    void marriageFail(){
        events = eventGenerator.marriage(person1, person2, year, locationData);
        assertNotEquals(events[0].getEventType(), "Birth");
        assertNotEquals(events[1].getEventType(), "Birth");
        assertNotEquals(events[0].getPersonID(), person2.getPersonID());
        assertNotEquals(events[1].getPersonID(), person1.getPersonID());
        assertNotEquals(events[0].getEventID(), person2.getPersonID() + "_marriage");
        assertNotEquals(events[1].getEventID(), person1.getPersonID() + "_marriage");
    }

    @Test
    void birthPass() {
        event1 = eventGenerator.birth(person1, year, locationData);
        assertEquals(event1.getEventType(), "birth");
        assertEquals(event1.getPersonID(), person1.getPersonID());
        assertEquals(event1.getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(event1.getEventID(), person1.getPersonID() + "_birth");
    }

    @Test
    void birthFail() {
        event1 = eventGenerator.birth(person1, year, locationData);
        assertNotEquals(event1.getEventType(), "Marriage");
        assertNotEquals(event1.getPersonID(), person2.getPersonID());
        assertNotEquals(event1.getEventID(), person2.getPersonID() + "_birth");
    }

    @Test
    void deathPass() {
        events = eventGenerator.death(person1, person2, year, locationData);
        assertEquals(events[0].getEventType(), "Death");
        assertEquals(events[1].getEventType(), "Death");
        assertEquals(events[0].getPersonID(), person1.getPersonID());
        assertEquals(events[1].getPersonID(), person2.getPersonID());
        assertEquals(events[0].getAssociatedUsername(), person1.getAssociatedUsername());
        assertEquals(events[1].getAssociatedUsername(), person2.getAssociatedUsername());
        assertEquals(events[0].getEventID(), person1.getPersonID() + "_death");
        assertEquals(events[1].getEventID(), person2.getPersonID() + "_death");
    }

    @Test
    void deathFail(){
        events = eventGenerator.death(person1, person2, year, locationData);
        assertNotEquals(events[0].getEventType(), "Birth");
        assertNotEquals(events[1].getEventType(), "Birth");
        assertNotEquals(events[0].getPersonID(), person2.getPersonID());
        assertNotEquals(events[1].getPersonID(), person1.getPersonID());
        assertNotEquals(events[0].getEventID(), person2.getPersonID() + "_death");
        assertNotEquals(events[1].getEventID(), person1.getPersonID() + "_death");
    }
}