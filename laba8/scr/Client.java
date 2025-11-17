import java.util.*;

public class Client {
    int id;
    String name;
    int age;
    List<Phone> phones;

    public Client(int id, String name, int age, List<Phone> phones){
        this.id=id;
        this.name=name;
        this.age=age;
        this.phones=phones;
    }
}