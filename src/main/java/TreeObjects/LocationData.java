package TreeObjects;

public class LocationData {
    Location[] data;

    public Location[] getData() {
        return data;
    }

    public void setData(Location[] data) {
        this.data = data;
    }

    public Location getRandomLocation() {
        int random = (int) (Math.random() * data.length);
        Location location = data[random];
        return location;
    }
}
