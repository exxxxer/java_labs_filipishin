public class Phone {
    public enum Type { MOBILE, HOME }

    String number;
    Type type;

    public Phone(String number, Type type){
        this.number=number;
        this.type=type;
    }
}