package cat.tecnocampus.exam25students.application.DTO;

public class LessonDTO {
    private Integer order;
    private String title;

    public LessonDTO() {}

    public LessonDTO(Integer order, String title) {
        this.order = order;
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}