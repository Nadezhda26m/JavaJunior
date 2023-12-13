package task3;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// служебные аннотации
@Retention(RetentionPolicy.RUNTIME) // аннотация будет доступны в Runtime для работы
@Target(ElementType.TYPE) // аннотация @Table применима только к конкретному <типу>
public @interface Table {
    // если такая аннотация есть, то с классом можно работать как с таблицей
    // с указанным именем

    String name();

}
