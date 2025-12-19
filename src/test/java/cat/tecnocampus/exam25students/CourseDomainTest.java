package cat.tecnocampus.exam25students;

import cat.tecnocampus.exam25students.domain.college.Course;
import cat.tecnocampus.exam25students.domain.college.Lesson;
import cat.tecnocampus.exam25students.domain.exceptions.LessonPositionOutOfBoundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseDomainTest {

    @Test
    void addLessonShiftsPositions() {
        Course course = new Course("Programming");
        course.addLesson(new Lesson("First Lesson"), 1);
        course.addLesson(new Lesson("Second Lesson"), 2);

        Lesson newLesson = new Lesson("New Lesson");
        course.addLesson(newLesson, 2);

        assertEquals(1, course.getLessons().get(0).getPosition());
        assertEquals(2, course.getLessons().get(1).getPosition());
        assertEquals(3, course.getLessons().get(2).getPosition());
        assertEquals("New Lesson", course.getLessons().get(1).getTitle());
    }

    @Test
    void invalidPositionThrowsException() {
        Course course = new Course("Programming");
        assertThrows(LessonPositionOutOfBoundsException.class, () -> course.addLesson(new Lesson("First Lesson"), 2));
    }
}
