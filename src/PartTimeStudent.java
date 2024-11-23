import java.util.List;

public class PartTimeStudent extends Student {
    private String workplace;

    public PartTimeStudent(String name, String surname, String group, String workplace) {
        super(name, surname, group);
        this.workplace = workplace;
    }

    public PartTimeStudent(String name, String surname, String group, String workplace, List<Grade> grades) {
        super(name, surname, group, grades);
        this.workplace = workplace;
    }

    @Override
    protected String PersonalData() {
        return super.PersonalData() + workplace + " ";
    }

    public String getWorkplace() {
        return workplace;
    }
}