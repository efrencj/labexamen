package cat.tecnocampus.exam25students.application.DTO;

public class CreateLessonRequest {
    private String title;
    private Integer position;

    public CreateLessonRequest() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
