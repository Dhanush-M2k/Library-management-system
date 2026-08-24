package net.javaguides.studentmanagement.service;

import net.javaguides.studentmanagement.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student saveStudent(Student student);

    Student getStudentById(long id);

    void deleteStudentById(long id);
}
