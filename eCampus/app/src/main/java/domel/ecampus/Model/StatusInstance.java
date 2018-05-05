package domel.ecampus.Model;

import java.util.ArrayList;


public class StatusInstance {

    private ArrayList<Subject> subjects;
    private boolean remember;
    private ArrayList<Student> students;
    private ArrayList<Exam> exams;


    public StatusInstance() {
    }

    public StatusInstance(ArrayList<Subject> subjects, ArrayList<Student> students, ArrayList<Exam> exams, boolean remember) {
        this.subjects = subjects;
        this.remember = remember;
        this.students = students;
        this.exams = exams;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }
}
