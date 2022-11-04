package Services;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import TreeObjects.Location;
import TreeObjects.LocationData;

public class EventGenerator {

    public Event[] marriage(Person person1, Person person2, int marriageYear, LocationData locationData) {
        Event[] events = new Event[2];

        Event marriageMother = new Event();
        Location randomLocation = locationData.getRandomLocation();
        marriageMother.setYear(marriageYear);
        marriageMother.setEventType("Marriage");
        marriageMother.setCity(randomLocation.getCity());
        marriageMother.setCountry(randomLocation.getCountry());
        marriageMother.setLatitude(randomLocation.getLatitude());
        marriageMother.setLongitude(randomLocation.getLongitude());
        marriageMother.setPersonID(person1.getPersonID());
        marriageMother.setAssociatedUsername(person1.getAssociatedUsername());
        marriageMother.setEventID(person1.getPersonID() + "_marriage");

        Event marriageFather = new Event();
        marriageFather.setYear(marriageYear);
        marriageFather.setEventType("Marriage");
        marriageFather.setCity(randomLocation.getCity());
        marriageFather.setCountry(randomLocation.getCountry());
        marriageFather.setLatitude(randomLocation.getLatitude());
        marriageFather.setLongitude(randomLocation.getLongitude());
        marriageFather.setPersonID(person2.getPersonID());
        marriageFather.setAssociatedUsername(person2.getAssociatedUsername());
        marriageFather.setEventID(person2.getPersonID() + "_marriage");

        events[0] = marriageMother;
        events[1] = marriageFather;

        return events;
    }

    public static Event birth(Person person, int year, LocationData locationData) {
        Event event = new Event();
        Location randomLocation = locationData.getRandomLocation();
        event.setYear(year);
        event.setEventType("birth");
        event.setCity(randomLocation.getCity());
        event.setCountry(randomLocation.getCountry());
        event.setLatitude(randomLocation.getLatitude());
        event.setLongitude(randomLocation.getLongitude());
        event.setPersonID(person.getPersonID());
        event.setAssociatedUsername(person.getAssociatedUsername());
        event.setEventID(person.getPersonID() + "_birth");

        return event;
    }

    public static Event[] death(Person person, Person person2, int year, LocationData locationData) {
        Event[] events = new Event[2];
        Event event = new Event();
        Location randomLocation = locationData.getRandomLocation();
        event.setYear(year);
        event.setEventType("Death");
        event.setCity(randomLocation.getCity());
        event.setCountry(randomLocation.getCountry());
        event.setLatitude(randomLocation.getLatitude());
        event.setLongitude(randomLocation.getLongitude());
        event.setPersonID(person.getPersonID());
        event.setAssociatedUsername(person.getAssociatedUsername());
        event.setEventID(person.getPersonID() + "_death");

        Event eventDad = new Event();
        Location randomLocationDad = randomLocation;
        eventDad.setYear(year);
        eventDad.setEventType("Death");
        eventDad.setCity(randomLocationDad.getCity());
        eventDad.setCountry(randomLocationDad.getCountry());
        eventDad.setLatitude(randomLocationDad.getLatitude());
        eventDad.setLongitude(randomLocationDad.getLongitude());
        eventDad.setPersonID(person2.getPersonID());
        eventDad.setAssociatedUsername(person2.getAssociatedUsername());
        eventDad.setEventID(person2.getPersonID() + "_death");

        events[0] = event;
        events[1] = eventDad;

        return events;
    }
}
