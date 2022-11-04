package Services;

import Model.Person;
import TreeObjects.FemaleNames;
import TreeObjects.MaleNames;
import TreeObjects.Surnames;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

class PersonGeneratorTest {
    private Gson gson = new Gson();
    private PersonGenerator personGenerator;
    private Person father;
    private Person mother;
    private Person person;
    private Surnames surnames;
    private FemaleNames femaleNames;
    private MaleNames maleNames;
    private String gender;
    private String username;


    @BeforeEach
    void setUp() throws FileNotFoundException {
        Reader readerSurnames = new FileReader("json/snames.json");
        surnames = (Surnames)gson.fromJson(readerSurnames, Surnames.class);

        Reader readerFemaleNames = new FileReader("json/fnames.json");
        femaleNames = (FemaleNames)gson.fromJson(readerFemaleNames, FemaleNames.class);

        Reader readerMaleNames = new FileReader("json/mnames.json");
        maleNames = (MaleNames)gson.fromJson(readerMaleNames, MaleNames.class);

        personGenerator = new PersonGenerator();
        father = new Person("fatherID", "person", "father", "mont", "m", "father", "father", "motherID");
        mother = new Person("motherID", "person", "mother", "mont", "f", "father", "father", "fatherID");

        gender = "m";
        username = "person";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void personWithoutParentsPass() {
        person = personGenerator.person(username, gender, surnames, femaleNames, maleNames);
        assertNotNull(person);
        assertEquals("person", person.getAssociatedUsername());
        assertEquals("m", person.getGender());
    }

    @Test
    void personWithParentsFail() {
        person = personGenerator.person(username, "4", surnames, femaleNames, maleNames);
        assertNull(person);
    }

    @Test
    void testPersonWithParentsPass() {
        person = personGenerator.person(username, mother, father, gender, surnames, femaleNames, maleNames);
        assertNotNull(person);
        assertEquals("person", person.getAssociatedUsername());
        assertEquals("m", person.getGender());
        assertEquals("fatherID", person.getFatherID());
        assertEquals("motherID", person.getMotherID());
    }

    @Test
    void testPersonWithParentsFail() {
        person = personGenerator.person(username, mother, father, "4", surnames, femaleNames, maleNames);
        assertNull(person);
    }
}