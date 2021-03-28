package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path="api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents (){
        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@Valid @RequestBody Student student){
        if(studentService.isEmailAlreadyUsed(student.getEmail())) {
            throw new BadRequestException("This email belongs to an existing Student");
        }else {
            studentService.addStudent(student);
        }
    }

    @DeleteMapping(path="{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        if(!studentService.doesStudentExist(studentId)) {
            throw new StudentNotFoundException("Student with id " + studentId + " does not exist Student");
        }else {
            studentService.deleteStudent(studentId);
        }
    }
}
