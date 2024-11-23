import java.util.*;

public class Student {
    private String name;
    private String surname;
    private String group;
    private List<Grade> grades;

    public Student(String name, String surname, String group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grades = new ArrayList<>();
    }

    public Student(String name, String surname, String group, List<Grade> grades) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.grades = grades;
    }

    @Override
    public String toString() {
        StringBuilder gradesString = new StringBuilder("[\n");
        for (Grade grade : grades) {
            gradesString.append(grade).append("\n");
        }

        gradesString.append("]");

        return PersonalData() + gradesString;
    }

    protected String PersonalData() {
        return name + " " + surname + " " + group + " ";
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroup() {
        return group;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }
}