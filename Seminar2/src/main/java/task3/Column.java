package task3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // аннотация @Column применима только к <полям> класса
public @interface Column {
    String name(); // в столбец с каким названием помещать данные текущего поля
    boolean primaryKey() default false;
}
