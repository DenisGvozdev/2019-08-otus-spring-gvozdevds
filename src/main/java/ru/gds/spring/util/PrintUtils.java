package ru.gds.spring.util;

import org.apache.log4j.Logger;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;

import java.lang.reflect.Field;
import java.util.*;

public class PrintUtils {

    private static final Logger logger = Logger.getLogger(PrintUtils.class);

    public static String printObject(StringBuilder sb, Object obj) {
        if (sb == null)
            sb = new StringBuilder();

        String cls = obj.getClass().getName();
        int indx = cls.lastIndexOf(".");
        String className = (indx > 0) ? cls.substring(indx + 1, cls.length()) : "";
        sb.append(className).append(" = {");

        for (Field field : getFields(obj.getClass())) {
            field.setAccessible(true);
            String name = field.getName();
            Object value;
            try {
                value = field.get(obj);
                if (value instanceof Set) {
                    sb.append("\n").append(name).append(" = [");
                    Set<Object> set = (Set<Object>) value;
                    for (Object o : set) {
                        printObject(sb, o);
                    }
                    sb.append("]\n");

                } else if (value instanceof List) {
                    sb.append(name).append(" = ").append("\n[");
                    List<Object> list = (List<Object>) value;
                    for (Object o : list) {
                        printObject(sb, o);
                    }
                    sb.append("]\n");

                } else if (value instanceof String || value instanceof Number
                        || value instanceof Date || value instanceof Boolean || value instanceof byte[]) {
                    sb.append(name).append(" = ").append(value).append(";");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return sb.append("}").toString();
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
        Set<Genre> genreSet = new HashSet<>();
        genreSet.add(genre1);

        Author author1 = new Author(
                "Фамилия",
                "Имя",
                "Отчество",
                new Date());
        Set<Author> authorSet = new HashSet<>();
        authorSet.add(author1);

        Status status = new Status("Статус 1");

        Book book = new Book(
                "книга",
                new Date(),
                "Описание",
                null,
                genreSet,
                authorSet,
                status);
        logger.debug(printObject(null, book));
    }
}
