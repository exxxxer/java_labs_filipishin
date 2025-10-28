package lab7;

import java.util.Objects;

public class Human implements Comparable<Human> {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Human(String firstName, String lastName, int age) {
        this.firstName = firstName; this.lastName = lastName; this.age = age;
    }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public int getAge()          { return age;       }

    // Comparable: Сначала Фамилия, потом Имя, потом Возраст
    @Override
    public int compareTo(Human o) {
        int r = this.lastName.compareToIgnoreCase(o.lastName);
        if (r != 0) return r;
        r = this.firstName.compareToIgnoreCase(o.firstName);
        if (r != 0) return r;
        return Integer.compare(this.age, o.age);
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Human h)) return false;
        return age == h.age &&
               firstName.equalsIgnoreCase(h.firstName) &&
               lastName.equalsIgnoreCase(h.lastName);
    }
    @Override public int hashCode() {
        return Objects.hash(firstName.toLowerCase(), lastName.toLowerCase(), age);
    }
    @Override public String toString() {
        return lastName + " " + firstName + " (" + age + ")";
    }
}
