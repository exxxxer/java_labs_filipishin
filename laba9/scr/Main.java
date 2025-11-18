import java.lang.annotation.*;
import java.lang.reflect.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.*;

//аннотация
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Repeat {
    int value();
}

//классы с методами
class TestClass {

    public void pubMethod1(int a){
        System.out.println("Публичный метод 1: a=" + a);
    }

    public void pubMethod2(String s){
        System.out.println("Публичный метод 2: строка=" + s);
    }

    @Repeat(2)
    protected void protMethod1(int x){
        System.out.println("Защищённый метод 1 (x=" + x + ")");
    }

    @Repeat(3)
    protected void protMethod2(String msg){
        System.out.println("Защищённый метод 2: сообщение='" + msg + "'");
    }

    @Repeat(4)
    private void privMethod1(){
        System.out.println("Приватный метод 1");
    }

    private void privMethod2(){
        System.out.println("Приватный метод 2 (не аннотирован, не вызывается)");
    }
}

//рекурсии
class AnnotationInvoker {
    public static void runAnnotated(Object obj){
        Class<?> cls = obj.getClass();

        for(Method m : cls.getDeclaredMethods()){
            if(m.isAnnotationPresent(Repeat.class)){
                int times = m.getAnnotation(Repeat.class).value();
                m.setAccessible(true);
                try{
                    for(int i=0; i<times; i++){
                        if(m.getParameterCount() == 0)
                            m.invoke(obj);
                        else if(m.getParameterTypes()[0] == int.class)
                            m.invoke(obj, 10);
                        else if(m.getParameterTypes()[0] == String.class)
                            m.invoke(obj, "Пример строки");
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}

//java.nio 
class FileTasks {

    public static void runNio(String surname, String name) throws IOException {
        Path root = Paths.get(surname);
        System.out.println("Создание директории: " + root);
        Files.createDirectories(root);

        Path mainFile = root.resolve(name + ".txt");
        System.out.println("Создание файла: " + mainFile);
        Files.writeString(mainFile, "Файл создан автоматически.");

        Path dir1 = root.resolve("dir1");
        Path dir2 = dir1.resolve("dir2");
        Path dir3 = dir2.resolve("dir3");
        System.out.println("Создание вложенных директорий dir1/dir2/dir3...");
        Files.createDirectories(dir3);

        System.out.println("Копирование файла в dir3...");
        Files.copy(mainFile, dir3.resolve(mainFile.getFileName()), StandardCopyOption.REPLACE_EXISTING);

        Path file1 = dir1.resolve("file1.txt");
        System.out.println("Создание file1 в dir1...");
        Files.writeString(file1, "file1 внутри dir1");

        Path file2 = dir2.resolve("file2.txt");
        System.out.println("Создание file2 в dir2...");
        Files.writeString(file2, "file2 внутри dir2");

        System.out.println("\n=== Рекурсивный обход директории " + surname + " ===");
        try(Stream<Path> stream = Files.walk(root)){
            stream.forEach(p -> {
                if(Files.isDirectory(p))
                    System.out.println("D " + p.getFileName());
                else
                    System.out.println("F " + p.getFileName());
            });
        }

        System.out.println("\nУдаление dir1 со всем содержимым...");
        try(Stream<Path> stream = Files.walk(dir1)){
            stream.sorted((a,b)->b.getNameCount()-a.getNameCount())
                    .forEach(path -> {
                        try{ Files.delete(path);}catch(Exception ignored){}
                    });
        }
        System.out.println("Готово. dir1 удалён.");
    }
}

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("===== Часть 1: Аннотации и рефлексия =====");
        TestClass t = new TestClass();
        AnnotationInvoker.runAnnotated(t);

        System.out.println("\n===== Часть 2: Работа с файловой системой (NIO) =====");
        FileTasks.runNio("Filipishin", "Egor");
    }
}
