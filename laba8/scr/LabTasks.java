import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class LabTasks {

    // ==== ЗАДАНИЕ 1 ====
    public static Predicate<String> notNull = s -> s != null;
    public static Predicate<String> notEmpty = s -> s != null && !s.isEmpty();
    public static Predicate<String> notNullAndNotEmpty = notNull.and(notEmpty);

    // ==== ЗАДАНИЕ 2 ====
    public static Predicate<String> startsCheck = s ->
            s != null && (s.startsWith("J") || s.startsWith("N")) && s.endsWith("A");

    // ==== ЗАДАНИЕ 3 ====
    public static Consumer<HeavyBox> load = b ->
            System.out.println("Отгрузили ящик с весом " + b.weight);
    public static Consumer<HeavyBox> send = b ->
            System.out.println("Отправляем ящик с весом " + b.weight);

    // ==== ЗАДАНИЕ 4 ====
    public static Function<Integer,String> numberType = n ->
            n>0?"Положительное":n<0?"Отрицательное":"Ноль";

    // ==== ЗАДАНИЕ 5 ====
    public static Supplier<Integer> random = () -> new Random().nextInt(11);

    // ==== ЗАДАНИЕ 6 ====
    public static double average(List<Integer> list){
        return list.stream().mapToInt(i -> i).average().orElse(0);
    }

    public static List<String> toUpperNew(List<String> list){
        return list.stream().map(s -> "new " + s.toUpperCase()).toList();
    }

    public static List<Integer> uniqueSquares(List<Integer> list){
        return list.stream().filter(i -> Collections.frequency(list,i)==1).map(i->i*i).toList();
    }

    public static List<String> startsWith(Collection<String> col,String l){
        return col.stream().filter(s->s.startsWith(l)).sorted().toList();
    }

    public static <T> T last(Collection<T> col){
        return col.stream().reduce((a,b)->b).orElseThrow();
    }

    public static int sumEven(int[] arr){
        return Arrays.stream(arr).filter(i->i%2==0).sum();
    }

    public static Map<Character,String> toMap(List<String> list){
        return list.stream().collect(Collectors.toMap(
                s->s.charAt(0), s->s.substring(1)
        ));
    }

    // ==== ЗАДАНИЕ 7 ====
    public static Client findYoungest(List<Client> clients){
        return clients.stream()
                .filter(c -> c.phones.stream().anyMatch(p->p.type==Phone.Type.MOBILE))
                .min(Comparator.comparingInt(c->c.age)).orElse(null);
    }
}

