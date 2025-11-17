import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println(LabTasks.notNullAndNotEmpty.test("abc"));
        System.out.println(LabTasks.startsCheck.test("JAVA"));
        LabTasks.load.andThen(LabTasks.send).accept(new HeavyBox(10));
        System.out.println(LabTasks.numberType.apply(-4));
        System.out.println(LabTasks.random.get());

        System.out.println(LabTasks.average(List.of(1,2,3)));
        System.out.println(LabTasks.toUpperNew(List.of("a","b")));
        System.out.println(LabTasks.uniqueSquares(List.of(1,2,2,3)));
        System.out.println(LabTasks.startsWith(List.of("aa","ab","b"),"a"));
        System.out.println(LabTasks.last(List.of(5,10,15)));
        System.out.println(LabTasks.sumEven(new int[]{1,2,4}));
        System.out.println(LabTasks.toMap(List.of("hello","apple")));

        List<Client> clients = List.of(
                new Client(1,"Egor",19,List.of(new Phone("666", Phone.Type.MOBILE))),
                new Client(2,"Masha",25,List.of(new Phone("123", Phone.Type.HOME))),
                new Client(3,"Ilya",18,List.of(new Phone("228", Phone.Type.MOBILE)))
        );
        System.out.println(LabTasks.findYoungest(clients).name);

        StudentTasks.facLoops("КБ");
        StudentTasks.fcStream("ПМИ",3);
        StudentTasks.bornStream(2006);
    }
}