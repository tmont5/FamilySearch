package Request;

import Model.Event;
import Model.Person;
import Model.User;

public class LoadRequest {
    private User[] users;
    private Person[] persons;
    private Event[] events;


    public LoadRequest(User[] user, Person[] person, Event[] event) {
        this.users = user;
        this.persons = person;
        this.events = event;
    }


    public Person[] getPerson() {
        return persons;
    }

    public void setPerson(Person[] person) {
        this.persons = person;
    }

    public User[] getUser() {
        return users;
    }

    public void setUser(User[] user) {
        this.users = user;
    }

    public Event[] getEvent() {
        return events;
    }

    public void setEvent(Event[] event) {
        this.events = event;
    }
}
