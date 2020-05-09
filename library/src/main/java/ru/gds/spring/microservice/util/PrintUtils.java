package ru.gds.spring.microservice.util;

import org.apache.log4j.Logger;
import ru.gds.spring.microservice.domain.Author;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Genre;
import ru.gds.spring.microservice.domain.Status;

import java.lang.reflect.Field;
import java.util.*;

public class PrintUtils {

    private static final Logger logger = Logger.getLogger(PrintUtils.class);

    public static String printObject(StringBuilder sb, Object obj) {
        if (sb == null)
            sb = new StringBuilder();

        sb.append(getClassName(obj)).append(" = {");

        for (Field field : getFields(obj.getClass())) {
            field.setAccessible(true);
            String name = field.getName();
            Object value;
            try {
                value = field.get(obj);
                if (value instanceof Collection) {
                    readCollection(value, name, sb);

                } else if (isPrimitive(value)) {
                    sb.append(name).append(" = ").append(value).append(";");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.append("}").toString();
    }

    private static boolean isPrimitive(Object value) {
        return (value instanceof String || value instanceof Number
                || value instanceof Date || value instanceof Boolean || value instanceof byte[]);
    }

    private static void readCollection(Object value, String name, StringBuilder sb) {
        sb.append(name).append(" = ").append("\n[");
        Collection<Object> collection = (Collection<Object>) value;
        collection.forEach(o -> {
            printObject(sb, o);
        });
        sb.append("]\n");
    }

    private static String getClassName(Object obj) {
        String cls = obj.getClass().getName();
        int indx = cls.lastIndexOf(".");
        return (indx > 0) ? cls.substring(indx + 1, cls.length()) : "";
    }

    private static Collection<Field> getFields(Class<?> type) {
        TreeSet<Field> fields = new TreeSet<Field>(
                new Comparator<Field>() {
                    @Override
                    public int compare(Field o1, Field o2) {
                        int res = o1.getName().compareTo(o2.getName());
                        if (0 != res) {
                            return res;
                        }
                        res = o1.getDeclaringClass().getSimpleName().compareTo(o2.getDeclaringClass().getSimpleName());
                        if (0 != res) {
                            return res;
                        }
                        res = o1.getDeclaringClass().getName().compareTo(o2.getDeclaringClass().getName());
                        return res;
                    }
                });
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }

    public static void main(String[] args) {
        Genre genre1 = new Genre("Жанр 1");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre1);

        Author author1 = new Author(
                "Фамилия",
                "Имя",
                "Отчество",
                new Date());
        List<Author> authorList = new ArrayList<>();
        authorList.add(author1);

        Status status = new Status("Статус 1");

        Book book = new Book(
                "книга",
                new Date(),
                "Описание",
                null,
                genreList,
                authorList,
                status);
        logger.debug(printObject(null, book));
    }
}
