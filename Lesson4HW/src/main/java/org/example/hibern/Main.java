package org.example.hibern;

/*
Создайте базу данных (например, SchoolDB).
В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
Настройте Hibernate для работы с вашей базой данных.
Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

import org.example.hibern.model.Course;
import org.example.hibern.repository.CoursesRepositoryImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (CoursesRepositoryImpl db = new CoursesRepositoryImpl()) {

            // Добавление данных
            db.insert(new Course("Docker", 8 * 60 + 20));
            db.insert(new Course("SQL", 12 * 60));
            db.insert(new Course("Java Junior", 3 * 60 + 30));
            db.insert(new Course("Spring", 125));
            db.insert(new Course("Hibernate", 2 * 60 + 45));
            db.insert(new Course("Java", 200));
            System.out.println("insert ok\n");

            // Чтение данных
            List<Course> courses = db.readAllData();
            for (Course course : courses) {
                System.out.println(course);
            }
            System.out.println("read all ok\n");

            // Поиск по названию
            courses = db.filterByTitle("javA");
            for (Course course : courses) {
                System.out.println(course);
            }
            System.out.println("filter \'javA\' ok\n");

            // Чтение объекта из базы данных
            Course findCourse = db.readById(2);
            System.out.println("findCourse (2): " + findCourse + '\n');

            // Обновление объекта
            if (findCourse != null) {
                findCourse.setTitle("MySQL");
                // findCourse.setDurationMinutes(findCourse.getDurationMinutes() / 2);
                findCourse.setDurationMinutes(findCourse.getDurationMinutes() - 800); // dur -> 0
            }
            db.update(findCourse);
            System.out.println("update ok\n");

            findCourse = db.readById(2);
            System.out.println("changeCourse: " + findCourse + '\n');

            // удаление
            db.deleteById(4);
            System.out.println("delete 4 ok\n");

            db.delete(findCourse);
            System.out.println("delete 2 ok\n");

            // чтение
            courses = db.readAllData();
            for (Course course : courses) {
                System.out.println(course);
            }
            System.out.println("read all ok\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("finish");
    }
}

/*
Hibernate: create table schoolDB.courses (
    id integer not null auto_increment,
    duration INT UNSIGNED not null,
    title varchar(200) not null, primary key (id)) engine=MyISAM
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
Hibernate: insert into schoolDB.courses (duration, title) values (?, ?)
insert ok

Hibernate: SELECT * FROM schoolDB.courses
Course{id=1, title='Docker', duration=500 min}
Course{id=2, title='SQL', duration=720 min}
Course{id=3, title='Java Junior', duration=210 min}
Course{id=4, title='Spring', duration=125 min}
Course{id=5, title='Hibernate', duration=165 min}
Course{id=6, title='Java', duration=200 min}
read all ok

Hibernate: SELECT * FROM schoolDB.courses WHERE title LIKE ?
Course{id=3, title='Java Junior', duration=210 min}
Course{id=6, title='Java', duration=200 min}
filter 'javA' ok

Hibernate: select course0_.id as id1_0_0_, course0_.duration as duration2_0_0_,
course0_.title as title3_0_0_ from schoolDB.courses course0_ where course0_.id=?
findCourse (2): Course{id=2, title='SQL', duration=720 min}

Hibernate: update schoolDB.courses set duration=?, title=? where id=?
update ok

Hibernate: select course0_.id as id1_0_0_, course0_.duration as duration2_0_0_,
course0_.title as title3_0_0_ from schoolDB.courses course0_ where course0_.id=?
changeCourse: Course{id=2, title='MySQL', duration=0 min}

Hibernate: DELETE FROM schoolDB.courses WHERE id = ?
delete 4 ok

Hibernate: delete from schoolDB.courses where id=?
delete 2 ok

Hibernate: SELECT * FROM schoolDB.courses
Course{id=1, title='Docker', duration=500 min}
Course{id=3, title='Java Junior', duration=210 min}
Course{id=5, title='Hibernate', duration=165 min}
Course{id=6, title='Java', duration=200 min}
read all ok

sessionFactory close
finish
 */
