package domel.ecampus.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

import domel.ecampus.R;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Subject {

    private static int auto_inc_id = 0;

    private int id;
    private String name;
    private String image;
    private  String description;
    private String degree;

    //@JsonManagedReference
    private ArrayList<Student> students;
    //@JsonManagedReference
    private ArrayList<SubjectTheme> themes;
    //@JsonManagedReference
    private ArrayList<Exam> exams;



    public Subject() {
        this.id = auto_inc_id++;
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.themes = new ArrayList<>();
    }

    public Subject(String name, String image, String description, String degree){
        this.id = auto_inc_id++;
        this.name = name;
        this.image = image;
        this.description = description;
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.themes = new ArrayList<>();
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<SubjectTheme> getThemes() {
        return themes;
    }

    public void setThemes(ArrayList<SubjectTheme> themes) {
        this.themes = themes;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }

    public void suspendExams(){
        this.exams.clear();
    }

    public void unrollStudent(Student student) {
        this.students.remove(student);
    }

    public void enrollStudent(Student student) {
        this.students.add(student);
    }

    public void scheduleExam(Exam exam){
        exam.setSubject(this);
        this.exams.add(exam);
    }

    public void suspendExam(Exam exam){
        this.exams.remove(exam);
    }

    /*
    public static ArrayList<Subject> getTestCollection(){
        ArrayList<Subject> s = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Subject sub = new Subject("test subject", R.mipmap.la_salle_logo, "this is some dummy text this is some dummy text this is some dummy text this is some dummy text this is some dummy text this is some dummy text this is some dummy text this is some dummy text this is some dummy text ");
            sub.getThemes().add(new SubjectTheme("Dummy Theme"));
            s.add(sub);
        }
        return s;
    }*/

    public void addThemes(Collection<SubjectTheme> themes){
        this.themes.addAll(themes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getAuto_inc_id() {
        return auto_inc_id;
    }

    public static void setAuto_inc_id(int auto_inc_id) {
        Subject.auto_inc_id = auto_inc_id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
