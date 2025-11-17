import java.util.*;

public class StudentTasks {
    static List<Student> students = List.of(
            new Student(1,"Иванов","Иван","Иванович","06.06.2006","Кемерово","1234567","КБ",2,"241"),
            new Student(2,"Петров","Пётр","Петрович","05.05.2005","Кемерово","2394087","ПМИ",3,"231"),
            new Student(3,"Сидоров","Сидор","Сидорович","07.07.2007","Кемерово","8274224","ФИТ",1,"251")
    );

    public static void facLoops(String f){ for(Student s:students) if(s.faculty.equals(f)) System.out.println(s); }
    public static void facCollections(String f){ students.stream().filter(s->s.faculty.equals(f)).forEach(System.out::println); }
    public static void facStream(String f){ facCollections(f); }

    public static void fcLoops(String f,int c){ for(Student s:students) if(s.faculty.equals(f)&&s.course==c) System.out.println(s); }
    public static void fcCollections(String f,int c){ students.stream().filter(s->s.faculty.equals(f)&&s.course==c).forEach(System.out::println); }
    public static void fcStream(String f,int c){ fcCollections(f,c); }

    public static void bornLoops(int y){ for(Student s: students){ int year = Integer.parseInt(s.datebirth.substring(6)); if(year > y) System.out.println(s);}}
    public static void bornCollections(int y){students.stream().filter(s -> Integer.parseInt(s.datebirth.substring(6)) > y).forEach(System.out::println);}
    public static void bornStream(int y){ bornCollections(y); }
}