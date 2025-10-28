package lab7;

import java.util.*;

public class PrimesGenerator implements Iterable<Integer> {
    private final int count;
    public PrimesGenerator(int count) { this.count = Math.max(count, 0); }

    private static boolean isPrime(int x) {
        if (x < 2) return false;
        for (int d = 2; d * d <= x; d++) if (x % d == 0) return false;
        return true;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int made = 0, cur = 1;
            @Override public boolean hasNext() { return made < count; }
            @Override public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                cur++;
                while (!isPrime(cur)) cur++;
                made++;
                return cur;
            }
        };
    }

    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        for (int p : this) list.add(p);
        return list;
    }
}
