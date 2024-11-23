import java.util.TreeSet;

public class StudentTreeSet extends TreeSet<Student> {
    public StudentTreeSet() {
        super(new StudentComparator());
    }
}
