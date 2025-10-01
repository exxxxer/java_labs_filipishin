import java.util.ArrayList;

class Button {
    private int count;

    public Button() {
        count = 0;
    }

    public void click() {
        count++;
        System.out.println("Button was clicked " + count + " times");
    }
}


class Balance {
    private int left;
    private int right;

    public Balance() {
        left = 0;
        right = 0;
    }

    public void addLeft(int weight) {
        left += weight;
    }

    public void addRight(int weight) {
        right += weight;
    }

    public void result() {
        if (left == right) {
            System.out.println("=");
        } else if (left > right) {
            System.out.println("L");
        } else {
            System.out.println("R");
        }
    }
}


class Bell {
    private boolean ding;

    public Bell() {
        ding = true;
    }

    public void sound() {
        if (ding) {
            System.out.println("ding");
        } else {
            System.out.println("dong");
        }
        ding = !ding;
    }
}

class OddEvenSeparator {
    private ArrayList<Integer> evens;
    private ArrayList<Integer> odds;

    public OddEvenSeparator() {
        evens = new ArrayList<>();
        odds = new ArrayList<>();
    }

    public void addNumber(int num) {
        if (num % 2 == 0) {
            evens.add(num);
        } else {
            odds.add(num);
        }
    }

    public void even() {
        System.out.println(evens);
    }

    public void odd() {
        System.out.println(odds);
    }
}


class Table {
    private int rows;
    private int cols;
    private int[][] data;

    public Table(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new int[rows][cols];
    }

    public int getValue(int row, int col) {
        return data[row][col];
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : data) {
            for (int val : row) {
                sb.append(val).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public double average() {
        int sum = 0;
        int count = rows * cols;
        for (int[] row : data) {
            for (int val : row) {
                sum += val;
            }
        }
        return (count > 0) ? (double) sum / count : 0.0;
    }
}


public class Main {
    public static void main(String[] args) {
        System.out.println("=== Задание 1: Button ===");
        Button btn = new Button();
        btn.click();
        btn.click();
        btn.click();

        System.out.println("\n=== Задание 2: Balance ===");
        Balance bal = new Balance();
        bal.addLeft(5);
        bal.addRight(3);
        bal.result();
        bal.addRight(2);
        bal.result();

        System.out.println("\n=== Задание 3: Bell ===");
        Bell bell = new Bell();
        bell.sound();
        bell.sound();
        bell.sound();

        System.out.println("\n=== Задание 4: OddEvenSeparator ===");
        OddEvenSeparator sep = new OddEvenSeparator();
        for (int num : new int[]{1, 2, 3, 4, 5, 6}) {
            sep.addNumber(num);
        }
        sep.even();
        sep.odd();

        System.out.println("\n=== Задание 5: Table ===");
        Table table = new Table(3, 3);
        table.setValue(0, 0, 5);
        table.setValue(1, 1, 10);
        table.setValue(2, 2, 15);
        System.out.println("Таблица:");
        System.out.println(table.toString());
        System.out.println("Среднее значение: " + table.average());
    }
}