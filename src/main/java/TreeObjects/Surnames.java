package TreeObjects;

public class Surnames {
    String[] data;

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getRandomSurname() {
        int random = (int) (Math.random() * data.length);
        String surname = data[random];
        return surname;
    }
}
