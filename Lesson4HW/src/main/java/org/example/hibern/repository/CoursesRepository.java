package org.example.hibern.repository;

import org.example.hibern.model.Course;

import java.util.List;

public interface CoursesRepository extends Repository<Course, Integer>{

    List<Course> filterByTitle(String containsTitle);

}
