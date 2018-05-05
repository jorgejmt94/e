package domel.ecampus.Model;

/**
 * Created by Guillermo on 8/5/16.
 */
public class SubjectTheme {

    private static int auto_inc_id = 0;
    private int id;
    private String name;


    public SubjectTheme() {
        this.id = auto_inc_id++;
    }

    public SubjectTheme(String name) {
        this.id = auto_inc_id++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getAuto_inc_id() {
        return auto_inc_id;
    }

    public static void setAuto_inc_id(int auto_inc_id) {
        SubjectTheme.auto_inc_id = auto_inc_id;
    }
}
