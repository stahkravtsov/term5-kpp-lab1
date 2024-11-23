import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student x, Student y) {
        if (x == null && y == null) return 0;
        if (x == null) return -1;

        int nameComparison = x.getName().compareTo(y.getName());
        if (nameComparison != 0) return nameComparison;

        int surnameComparison;
        if (x.getSurname() == null && y.getSurname() == null) {
            surnameComparison = 0;
        } else if (x.getSurname() == null) {
            surnameComparison = 1;
        } else if (y.getSurname() == null) {
            surnameComparison = -1;
        } else {
            surnameComparison = y.getSurname().compareTo(x.getSurname());
        }
        return surnameComparison;
    }
}
