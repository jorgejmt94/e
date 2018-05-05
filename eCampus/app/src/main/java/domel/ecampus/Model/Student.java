package domel.ecampus.Model;


import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.joda.time.DateTime;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import domel.ecampus.R;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Student{

    private static int auto_inc_id = 0;

    private int id;
    private String name;
    private int image;
    private DateTime birthdate;
    private String specialty;
    private String gender;
    private String path;

//    @JsonBackReference
    private ArrayList<Subject> subjects;


    public Student(String name, int image, DateTime birthdate, String specialty, String gender) {
        this.id = auto_inc_id++;
        this.name = name;
        this.image = image;
        this.birthdate = birthdate;
        this.specialty = specialty;
        this.gender = gender;
        this.subjects = new ArrayList<>();
        this.path = null;
    }

    public String getGender() {return gender;}

    public void setGender(String gender) {this.gender = gender;}


    public Student() {
        this.id = auto_inc_id++;
        this.path = null;
        this.subjects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public String getAgeString() {
        return Years.yearsBetween(this.birthdate, new DateTime()).getYears() + " años";
    }

    public String getSpecialty() {return " " + specialty;}

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void unrollSubject(Subject subject) {
        this.subjects.remove(subject);
    }

    public void enrollSubject(Subject subject) {
        this.subjects.add(subject);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public String getBithdateString(){

        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        String str = timeFormat.print(birthdate);
        return str;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static int getAuto_inc_id() {
        return auto_inc_id;
    }

    public static void setAuto_inc_id(int auto_inc_id) {
        Student.auto_inc_id = auto_inc_id;
    }

    /*
    public static ArrayList<Student> getTestCollection(){
        ArrayList<Student> s = new ArrayList<>();

        for (int i = 0; i < 9; i++) {

            Student st = new Student("test student",R.mipmap.la_salle_logo,new DateTime(1991,11,30,0,0), "Magisterio", "Hombre");
            st.getSubjects().add(new Subject("test subject", R.mipmap.la_salle_logo, "this is some dummy text this is some dummy text this is some dummy text "));
            s.add(st);
        }
        return s;
    }*/
}
