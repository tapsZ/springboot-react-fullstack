package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        //check if email is taken
        Boolean emailExists = studentRepository.selectExistsEmail(student.getEmail());
        if(emailExists) {
            throw new BadRequestException("This email belongs to an existing Student " + student.getName());
        }
            studentRepository.save(student);
    }

    public void deleteStudent(Long studentId){
        boolean studentExists = studentRepository.existsById(studentId);
        if(!studentExists) {
            throw new StudentNotFoundException("Student with id " + studentId + " does not exist Student");
        }
        studentRepository.deleteById(studentId);
    }
}
