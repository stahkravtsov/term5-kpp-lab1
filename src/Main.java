import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();

        List<Student> students = new ArrayList<>();
       /* List<Grade> grades = new ArrayList<>();

        grades.add(new Grade("KPP", 85));
        grades.add(new Grade("KPZ", 87));
        grades.add(new Grade("APZ", 67));
        grades.add(new Grade("QPZ", 77));

        students.add(new PartTimeStudent("Max", "Verstappen", "PZ-33", "SoftServe", grades));

        List<Grade> grades1 = new ArrayList<>();

        grades1.add(new Grade("KPP", 100));
        grades1.add(new Grade("KPZ", 34));
        grades1.add(new Grade("APZ", 65));
        grades1.add(new Grade("QPZ", 98));
        students.add(new PartTimeStudent("Lando", "Norris", "PZ-37", "N-iX", grades1));

        studentRepository.setStudents(students);
        studentRepository.writeStudentsToFile();*/

/*
        students.add(new Student("John", "Doe"));
        students.add(new Student("Jane", "Doe"));
        students.add(new Student("John", "Smith"));
        students.add(new Student("Jane", null));*/
/*
        for (Student student : students) {
            System.out.println(student.getName() + " " + student.getSurname());
        }
*/
        studentRepository.readStudentsFromFile();

        for (Student student : studentRepository.getStudents()) {
            System.out.println(student);
        }

    }

}