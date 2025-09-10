public class ReverseString {
    public static void main(String[] args) {
        String str = "make install";  // исходная строка
        String reversed = "";         // сюда будем собирать перевернутую строку

        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }

        System.out.println(reversed); // вывод результата
    }
}