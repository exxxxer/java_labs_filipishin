public class SeriesSum {
    public static void main(String[] args) {
        int N = 1_000_000; // количество членов для аппроксимации
        double sum = 0.0;

        for (int n = 2; n <= N; n++) {
            sum += 1.0 / (n * n + n - 2);
        }

        System.out.println("Приближённая сумма ряда = " + sum);
    }
}