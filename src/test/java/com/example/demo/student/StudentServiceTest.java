package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest=new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        //given
        Student student = new Student("Tapiwa", "tapiwa@gmail.ca", Gender.MALE);
        //when
        underTest.addStudent(student);
        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        assertThat(studentArgumentCaptor.getValue()).isEqualTo(student);
    }

    @Test
    void willThrowErrorWhenEmailTaken() {
        //given
        Student student = new Student("Tapiwa", "tapiwa@gmail.ca", Gender.MALE);
        given(studentRepository.selectExistsEmail(student.getEmail())).willReturn(true);
        //when
        //then
        assertThatThrownBy(()-> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("This email belongs to an existing Student " + student.getName());

        verify(studentRepository, never()).save(any());
    }

    @Test
    void deleteStudent() {
        //given
        given(studentRepository.existsById(any())).willReturn(true);
        //when
        underTest.deleteStudent(any());

        //then
        verify(studentRepository).deleteById(any());
    }

    @Test
    void throwErrorWhenStudentDoesNotExist() {
        //given
        Long studentId=1L;
        given(studentRepository.existsById(studentId)).willReturn(false);
        //then
        assertThatThrownBy(()-> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exist Student");
        verify(studentRepository, never()).deleteById(any());
    }
}