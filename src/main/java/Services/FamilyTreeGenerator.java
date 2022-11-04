package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Model.Event;
import Model.Person;
import Model.User;
import TreeObjects.*;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class FamilyTreeGenerator {
    Gson gson = new Gson();
    int peopleCount = 0;
    int eventCount = 0;

    int generationInitial;
    public FamilyTreeGenerator() {}
        LocationData locationData = new LocationData();
        Surnames surnames = new Surnames();
        FemaleNames femaleNames = new FemaleNames();
        MaleNames maleNames = new MaleNames();

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public void generateData(){
            try{
                Reader reader = new FileReader("json/locations.json");
                locationData = (LocationData)gson.fromJson(reader, LocationData.class);

                Reader readerSurnames = new FileReader("json/snames.json");
                surnames = (Surnames)gson.fromJson(readerSurnames, Surnames.class);

                Reader readerFemaleNames = new FileReader("json/fnames.json");
                femaleNames = (FemaleNames)gson.fromJson(readerFemaleNames, FemaleNames.class);

                Reader readerMaleNames = new FileReader("json/mnames.json");
                maleNames = (MaleNames)gson.fromJson(readerMaleNames, MaleNames.class);
                System.out.println("Files found");
            }catch (FileNotFoundException e){
                System.out.println("Files not found");
            }
        }

    public void generateFamilyTree(String username, String gender, int generations, User user, Connection connection) throws FileNotFoundException {
        System.out.println("Generating family tree...");
        if(generations > 0 || gender == "f" || gender == "m") {
            try {
                generateData();
                int birthYear = 1988;
                int marriageYear = 2003;
                int deathYear = 2020;
                generationInitial = generations;
                Person person = generatePeople(user, username, gender, generations, birthYear, marriageYear, deathYear, connection);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: trouble generating family tree");
            }
        }else{
            System.out.println("Error: trouble generating family tree");
        }
    }

    public Person generatePeople(User user, String username, String gender, int generations,int birthYear, int marriageYear, int deathYear, Connection conn) throws DataAccessException, SQLException {

        try {
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);

            Person mother = null;
            Person father = null;

            if (generations >= 1) {
                mother = generatePeople(user, username,"f", generations - 1,birthYear - 25, marriageYear - 23, deathYear - 25, conn);
                father = generatePeople(user, username,"m",generations - 1,birthYear - 25,marriageYear - 23, deathYear - 25, conn);

                mother.setSpouseID(father.getPersonID());
                father.setSpouseID(mother.getPersonID());

                EventGenerator eventGenerator = new EventGenerator();
                Event[] weddings = eventGenerator.marriage(mother, father, marriageYear, locationData);
                eventCount += 2;

                personDao.insertSpouseID(mother.getSpouseID(), mother.getPersonID());
                personDao.insertSpouseID(father.getSpouseID(), father.getPersonID());

                eventDao.insert(weddings[0]);
                eventDao.insert(weddings[1]);

                Event[] deathMarriage = EventGenerator.death(mother, father, deathYear, locationData);
                eventCount += 2;

                eventDao.insert(deathMarriage[0]);
                eventDao.insert(deathMarriage[1]);
            }

            Person person = new Person();


            if(mother == null || father == null) {
                person = PersonGenerator.person(username,gender, surnames, femaleNames, maleNames);
                peopleCount++;

            }else {
                if(generations == generationInitial){
                    person.setGender(gender);
                    person.setPersonID(user.getPersonID());
                    person.setFirstName(user.getFirstName());
                    person.setLastName(user.getLastName());
                    person.setSpouseID(null);
                    person.setFatherID(father.getPersonID());
                    person.setMotherID(mother.getPersonID());
                    person.setAssociatedUsername(username);
                    birthYear += 25;
                    peopleCount++;
                }else {
                    person = PersonGenerator.person(username, mother, father, gender, surnames, femaleNames, maleNames);
                    peopleCount++;
                    birthYear += 25;
                }
            }

            Event birth = EventGenerator.birth(person, birthYear, locationData);
            eventCount++;


            personDao.insert(person);
            eventDao.insert(birth);
            conn.commit();

            return person;

        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
