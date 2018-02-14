import java.util.List;

public class User {

    public int id;
    public String name;
    public String city;
    public String zipcode;
    public List<String> likes;
    public boolean deactivated;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", likes=" + likes +
                ", deactivated=" + deactivated +
                '}';
    }



}
