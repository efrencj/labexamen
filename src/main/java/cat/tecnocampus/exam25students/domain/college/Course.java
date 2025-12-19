package cat.tecnocampus.exam25students.domain.college;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("position ASC")
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public void addLesson(Lesson lesson, int position) {
        if (position < 1 || position > lessons.size() + 1) {
            throw new IllegalArgumentException("Lesson position out of bounds");
        }

        for (int i = position - 1; i < lessons.size(); i++) {
            lessons.get(i).setPosition(lessons.get(i).getPosition() + 1);
        }

        lesson.setPosition(position);
        lesson.setCourse(this);
        lessons.add(position - 1, lesson);
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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}