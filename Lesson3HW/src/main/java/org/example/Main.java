package org.example;

/*
Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
Обеспечьте поддержку сериализации для этого класса.
Создайте объект класса Student и инициализируйте его данными.
Сериализуйте этот объект в файл.
Десериализуйте объект обратно в программу из файла.
Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String FILE_BIN = "Lesson3HW/src/main/java/org/example/student.bin";
        String FILE_JSON = "Lesson3HW/src/main/java/org/example/student.json";
        String FILE_XML = "Lesson3HW/src/main/java/org/example/student.xml";

        Student student = new Student("Василий", 23, 4.7);

        serialBin(student, FILE_BIN);
        serialJson(student, FILE_JSON);
        serialXml(student, FILE_XML);

        System.out.println("<bin>");
        System.out.println(deSerialBin(Student.class, FILE_BIN));
        System.out.println("<json>");
        System.out.println(deSerialJson(Student.class, FILE_JSON));
        System.out.println("<xml>");
        System.out.println(deSerialXml(Student.class, FILE_XML));

        // <bin>
        // Имя: Василий
        // Возраст: 23
        // Средний балл (transient): 0.0
        // <json>
        // Имя: Василий
        // Возраст: 23
        // Средний балл (transient): 0.0
        // <xml>
        // Имя: Василий
        // Возраст: 23
        // Средний балл (transient): 0.0
    }

    public static void serialBin(Object object, String file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(object);
        }
    }

    public static <T> T deSerialBin(Class<T> classObject, String file)
            throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (T) objectInputStream.readObject();
        }
    }

    public static void serialJson(Object object, String file) throws IOException {
        new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValue(new File(file), object);
    }

    public static <T> T deSerialJson(Class<T> classObject, String file) throws IOException {
        // ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // или @JsonIgnoreProperties(ignoreUnknown = true) в классе
        return new ObjectMapper().readValue(new File(file), classObject);
    }

    public static void serialXml(Object object, String file) throws IOException {
        new XmlMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValue(new File(file), object);
    }

    public static <T> T deSerialXml(Class<T> classObject, String file) throws IOException {
        return new XmlMapper().readValue(new File(file), classObject);
    }
}