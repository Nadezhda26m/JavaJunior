package org.example.hibern.repository;

import org.example.hibern.model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Comparator;
import java.util.List;

public class CoursesRepositoryImpl implements CoursesRepository, AutoCloseable {
    private SessionFactory sessionFactory;

    public CoursesRepositoryImpl() {
        init();
    }

    private void init() {
        this.sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                // .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
        /*
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        this.sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
         */
    }

    private boolean isValidCourse(Course course) {
        if (course == null) return false;
        if (course.getDurationMinutes() < 0)
            course.setDurationMinutes(0);
        return !course.getTitle().isEmpty()
                && course.getDurationMinutes() >= 0;
    }

    @Override
    public void insert(Course course) {
        if (isValidCourse(course)) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(course);
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public Course readById(Integer id) {
        if (id > 0) {
            try (Session session = sessionFactory.openSession()) {
                return session.get(Course.class, id);
                // return session.find(Course.class, id);
            }
        }
        return null;
    }

    @Override
    public List<Course> readAllData() {
        try (Session session = sessionFactory.openSession()) {
            List<Course> courses = session.createNativeQuery(
                            "SELECT * FROM schoolDB.courses", Course.class)
                    .getResultList();
            courses.sort(Comparator.comparingInt(Course::getId));
            return courses;

        }
    }

    @Override
    public List<Course> filterByTitle(String containsTitle) {
        try (Session session = sessionFactory.openSession()) {
            String query = "SELECT * FROM schoolDB.courses WHERE title LIKE :courseTitle";
            List<Course> courses = session.createNativeQuery(query, Course.class)
                    .setParameter("courseTitle", "%" + containsTitle + '%')
                    .getResultList();
            courses.sort(Comparator.comparingInt(Course::getId));
            return courses;
        }
    }

    @Override
    public void update(Course course) {
        if (isValidCourse(course)) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.update(course);
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public void delete(Course course) {
        if (isValidCourse(course)) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.delete(course);
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (id > 0) {
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.createNativeQuery("DELETE FROM schoolDB.courses WHERE id = ?")
                        .setParameter(1, id)
                        .executeUpdate();

                // String query = "DELETE FROM schoolDB.courses WHERE id=" + id;
                // session.createNativeQuery(query, Course.class).executeUpdate();
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public void close() {
        sessionFactory.close();
        System.out.println("sessionFactory close");
    }
}
