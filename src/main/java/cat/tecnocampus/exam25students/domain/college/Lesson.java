package cat.tecnocampus.exam25students.domain.college;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Lesson() {}

    public Lesson(String title) {
        this.title = title;
    }

    public void validateTitle() {
        if (title == null || title.length() < 5) {
            throw new IllegalArgumentException("The title must be at least 5 characters long");
        }

        if (!title.isEmpty() && Character.isLetter(title.charAt(0)) && !Character.isUpperCase(title.charAt(0))) {
            throw new IllegalArgumentException("The title must start with a capital letter");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}