package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        //given
        String email = "rob@rob.com";
        Student student = new Student("Rob", email, Gender.MALE);
        underTest.save(student);

        //when
        boolean result = underTest.selectExistsEmail(email);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExist() {
        //given
        String email = "rob@rob.com";

        //when
        boolean result = underTest.selectExistsEmail(email);

        //then
        assertThat(result).isFalse();
    }
}