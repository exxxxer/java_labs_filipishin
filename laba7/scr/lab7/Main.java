package lab7;

import java.util.*;

public class Main {

    // задание 1: класс Collections
    static void task1(int N) {
        System.out.println("=== Задание 1 ===");

        // a) массив из N чисел
        Integer[] arr = new Integer[N];
        for (int i = 0; i < N; i++) arr[i] = i % 5; // специально с повторами
        System.out.println("a) Массив: " + Arrays.toString(arr));

        // b) из массива -> список
        List<Integer> list = new ArrayList<>(Arrays.asList(arr));
        System.out.println("b) Список: " + list);

        // c) сортировка по возрастанию
        Collections.sort(list);
        System.out.println("c) По возрастанию: " + list);

        // d) сортировка по убыванию
        Collections.sort(list, Collections.reverseOrder());
        System.out.println("d) По убыванию: " + list);

        // e) перемешать
        Collections.shuffle(list);
        System.out.println("e) Перемешан: " + list);

        // f) циклический сдвиг на 1
        Collections.rotate(list, 1);
        System.out.println("f) Сдвиг на 1: " + list);

        // g) оставить только уникальные (встречаются 1 раз)
        List<Integer> onlyUnique = new ArrayList<>();
        Map<Integer, Integer> cnt = new HashMap<>();
        for (Integer x : list) cnt.put(x, cnt.getOrDefault(x, 0) + 1);
        for (Integer x : list) if (cnt.get(x) == 1) onlyUnique.add(x);
        System.out.println("g) Только уникальные: " + onlyUnique);

        // h) оставить только дубликаты (без повторов)
        List<Integer> onlyDup = new ArrayList<>();
        for (Map.Entry<Integer,Integer> e : cnt.entrySet())
            if (e.getValue() > 1) onlyDup.add(e.getKey());
        System.out.println("h) Только дубликаты: " + onlyDup);

        // i) список -> массив
        Integer[] back = list.toArray(new Integer[0]);
        System.out.println("i) Обратно в массив: " + Arrays.toString(back));
    }

    // задание 4: частоты слов (HashMap)
    static Map<String,Integer> wordFreq(String text) {
        System.out.println("\n=== Задание 4 ===");
        String[] words = text.split("[^A-Za-z]+"); // регистр различаем
        Map<String,Integer> map = new HashMap<>();
        for (String w : words) {
            if (w.isEmpty()) continue;
            map.put(w, map.getOrDefault(w, 0) + 1);
        }
        // простой вывод
        for (String k : map.keySet())
            System.out.println(k + " -> " + map.get(k));
        return map;
    }

    // задание 5: инверсия Map<K,V> -> Map<V, Collection<K>) 
    static <K,V> Map<V, Collection<K>> invert(Map<K,V> src) {
        Map<V, Collection<K>> res = new HashMap<>();
        for (Map.Entry<K,V> e : src.entrySet()) {
            V v = e.getValue();
            K k = e.getKey();
            if (!res.containsKey(v)) res.put(v, new ArrayList<>());
            res.get(v).add(k);
        }
        return res;
    }

    public static void main(String[] args) {
        // 1
        task1(12);

        // 2
        System.out.println("\n=== Задание 2 (простые числа) ===");
        PrimesGenerator gen = new PrimesGenerator(15);
        System.out.print("Вперёд: ");
        for (int p : gen) System.out.print(p + " ");
        System.out.println();
        System.out.print("Назад:  ");
        List<Integer> tmp = gen.toList();
        ListIterator<Integer> it = tmp.listIterator(tmp.size());
        while (it.hasPrevious()) System.out.print(it.previous() + " ");
        System.out.println();

        // 3
        System.out.println("\n=== Задание 3 (множества и компараторы) ===");
        Human h1 = new Human("Ivan", "Petrov", 20);
        Human h2 = new Human("Petr", "Ivanov", 25);
        Human h3 = new Human("Ivan", "Petrov", 20); // дубликат
        Human h4 = new Human("Olga", "Sidorova", 30);

        // a) HashSet
        Set<Human> s = new HashSet<>();
        s.add(h1); s.add(h2); s.add(h3); s.add(h4);
        System.out.println("HashSet:       " + s);

        // b) LinkedHashSet на основе s
        Set<Human> lhs = new LinkedHashSet<>(s);
        System.out.println("LinkedHashSet: " + lhs);

        // c) TreeSet на основе s (использует Comparable из Human)
        Set<Human> ts1 = new TreeSet<>(s);
        System.out.println("TreeSet (Comparable): " + ts1);

        // d) Пустой TreeSet с компаратором по фамилии
        Set<Human> ts2 = new TreeSet<>(new HumanComparatorByLName());
        ts2.addAll(s);
        System.out.println("TreeSet (по фамилии): " + ts2);

        // e) Пустой TreeSet с анонимным компаратором по возрасту
        Set<Human> ts3 = new TreeSet<>(new Comparator<Human>() {
            @Override public int compare(Human a, Human b) {
                return Integer.compare(a.getAge(), b.getAge());
            }
        });
        ts3.addAll(s);
        System.out.println("TreeSet (по возрасту): " + ts3);

        // 4 + 5
        Map<String,Integer> freq = wordFreq("To be, or not to be. To Be!");
        System.out.println("=== Задание 5 (инверсия Map) ===");
        Map<Integer, Collection<String>> inv = invert(freq);
        for (Integer k : inv.keySet())
            System.out.println(k + " -> " + inv.get(k));
    }
}
