package TreeObjects;

public class MaleNames {
    String[] data;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getRandomName() {
        int random = (int) (Math.random() * data.length);
        String name = data[random];
        return name;
    }
}
