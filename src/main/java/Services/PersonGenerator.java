package Services;

import Model.AuthToken;
import Model.Person;
import Model.User;
import TreeObjects.FemaleNames;
import TreeObjects.LocationData;
import TreeObjects.MaleNames;
import TreeObjects.Surnames;

public class PersonGenerator {


    public static Person person(String username, String gender, Surnames surnames, FemaleNames femaleNames, MaleNames maleNames){
        if(gender == "m" || gender == "f") {
            Person person = new Person();
            person.setGender(gender);
            if (gender.equals("m")) {
                person.setFirstName(maleNames.getRandomName());
            } else {
                person.setFirstName(femaleNames.getRandomName());
            }
            person.setAssociatedUsername(username);
            person.setLastName(surnames.getRandomSurname());
            person.setPersonID(person.getFirstName() + "_" + person.getLastName());
            //person.setAssociatedUsername(user);
            return person;
        }else{
            return null;
        }
    }

    public static Person person(String username, Person mother, Person father, String gender, Surnames surnames, FemaleNames femaleNames, MaleNames maleNames) {
        if(gender == "f" || gender == "m") {
            Person person = new Person();
            person.setGender(gender);
            if (gender.equals("f")) {
                person.setFirstName(femaleNames.getRandomName());
            } else {
                person.setFirstName(maleNames.getRandomName());
            }
            person.setAssociatedUsername(username);
            person.setLastName(surnames.getRandomSurname());
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());
            //person.setSpouseID("spouseID");
            person.setPersonID(person.getFirstName() + "_" + person.getLastName());
            //person.setAssociatedUsername(authToken.getUsername());

            return person;
        }else{
            return null;
        }
    }
}
