package lab7;

import java.util.Comparator;

public class HumanComparatorByLName implements Comparator<Human> {
    @Override
    public int compare(Human a, Human b) {
        return a.getLastName().compareToIgnoreCase(b.getLastName());
    }
}
