package cat.tecnocampus.exam25students.domain.exceptions;

public class LessonPositionOutOfBoundsException extends RuntimeException {
    public LessonPositionOutOfBoundsException(String message) {
        super(message);
    }
}
