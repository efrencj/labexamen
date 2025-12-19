package cat.tecnocampus.exam25students.application.DTO;

import java.util.List;

public class CourseDTO {
    private Long id;
    private String name;
    private List<LessonDTO> lessons;

    public CourseDTO() {}

    public CourseDTO(Long id, String name, List<LessonDTO> lessons) {
        this.id = id;
        this.name = name;
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }
}
