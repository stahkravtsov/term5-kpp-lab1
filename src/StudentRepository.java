import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class StudentRepository {
    private List<Student> students;
    StudentTreeSet studentsSorted = new StudentTreeSet();

    private static final String STUDENTS_FILE = "students.txt";

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return studentsSorted.stream().toList();
    }

    public void writeStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                boolean isPart = student instanceof PartTimeStudent;

                writer.write((student instanceof PartTimeStudent) + ",");

                writer.write(student.getName() + "," + student.getSurname() + "," + student.getGroup());

                if (isPart) {
                    writer.write(((PartTimeStudent) student).getWorkplace() + ",");
                }

                for (Grade grade : student.getGrades()) {
                    writer.write("," + grade.getSubject() + "," + grade.getValue());
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Помилка запису до файлу: " + e.getMessage());
        }
    }

    public List<Student> readStudentsFromFile() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Student student;

                int nexId = 4;

                if (Boolean.parseBoolean(parts[0])) {
                    student = new PartTimeStudent(parts[1], parts[2], parts[3], parts[4]);
                    nexId++;
                } else {
                    student = new Student(parts[1], parts[2], parts[3]);
                }

                for (int i = nexId; i < parts.length - 1; i += 2) {
                    student.addGrade(new Grade(parts[i], Double.parseDouble(parts[i + 1])));
                }
                students.add(student);
                studentsSorted.add(student);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        this.students = students;
        return students;
    }

    public List<Student> sortStudentsByAverageGrade() {
        return students.stream()
                .sorted((student1, student2) -> {
                    double avgGrade1 = calculateAverageGrade(student1);
                    double avgGrade2 = calculateAverageGrade(student2);
                    return Double.compare(avgGrade2, avgGrade1);
                })
                .collect(Collectors.toList());
    }

    public Set<String> getUniqueSubjects() {
        return students.stream()
                .flatMap(student -> student.getGrades().stream())
                .map(Grade::getSubject)
                .collect(Collectors.toSet());
    }

    public Optional<Student> getBestStudentForSubject(String subject) {
        return students.stream()
                .filter(student -> student.getGrades().stream()
                        .anyMatch(grade -> grade.getSubject().equals(subject)))
                .max(Comparator.comparingDouble(student -> calculateAverageGradeForSubject(student, subject)));
    }

    public List<Student> getPartTimeStudentsStream() {
        return students.stream()
                .filter(student -> student instanceof PartTimeStudent)
                .collect(Collectors.toList());
    }

    public List<Student> getPartTimeStudentsNoStream() {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (student instanceof PartTimeStudent) {
                result.add(student);
            }
        }
        return result;
    }

    public List<Student> getOtherStudentsStream() {
        return students.stream()
                .filter(student -> !(student instanceof PartTimeStudent))
                .collect(Collectors.toList());
    }

    public List<Student> getOtherStudentsNoStream() {
        List<Student> result = new ArrayList<>();
        for (Student student : students) {
            if (!(student instanceof PartTimeStudent)) {
                result.add(student);
            }
        }
        return result;
    }

    public Map<String, List<Student>> groupStudentsByGroupStream() {
        return students.stream().collect(Collectors.groupingBy(Student::getGroup));
    }

    public Map<String, List<Student>> groupStudentsByGroupNoStream() {
        Map<String, List<Student>> result = new HashMap<>();
        for (Student student : students) {
            String group = student.getGroup();
            if (!result.containsKey(group)) {
                result.put(group, new ArrayList<>());
            }
            result.get(group).add(student);
        }
        return result;
    }

    public Map<String, Map<Double, List<String>>> getGradesBySubjectStream() {
        return students.stream()
                .flatMap(student -> student.getGrades().stream()
                        .map(grade -> new AbstractMap.SimpleEntry<>(student, grade)))
                .collect(Collectors.groupingBy(entry -> entry.getValue().getSubject(),
                        Collectors.groupingBy(entry -> entry.getValue().getValue(),
                                Collectors.mapping(entry -> entry.getKey().getSurname(), Collectors.toList()))));
    }

    public Map<String, Map<Double, List<String>>> getGradesBySubjectNoStream() {
        Map<String, Map<Double, List<String>>> result = new HashMap<>();
        for (Student student : students) {
            for (Grade grade : student.getGrades()) {
                String subject = grade.getSubject();
                double value = grade.getValue();
                if (!result.containsKey(subject)) {
                    result.put(subject, new HashMap<>());
                }
                if (!result.get(subject).containsKey(value)) {
                    result.get(subject).put(value, new ArrayList<>());
                }
                result.get(subject).get(value).add(student.getSurname());
            }
        }
        return result;
    }

    private double calculateAverageGrade(Student student) {
        return student.getGrades().stream()
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);
    }

    private double calculateAverageGradeForSubject(Student student, String subject) {
        return student.getGrades().stream()
                .filter(grade -> grade.getSubject().equals(subject))
                .mapToDouble(Grade::getValue)
                .average()
                .orElse(0.0);
    }
}