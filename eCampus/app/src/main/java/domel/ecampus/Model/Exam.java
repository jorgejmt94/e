package domel.ecampus.Model;


import android.content.Context;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

import domel.ecampus.R;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Exam implements Comparable<Exam>  {

    private static int auto_inc_id = 0;

    private int id;
    private DateTime date;
    private String assigned_class;

    //@JsonBackReference
    private Subject subject;
    private String speciality;
    private String hour;


    public Exam() {
    }

    public Exam(DateTime date, String hour, String assigned_class, Subject subject) {
        this.id = auto_inc_id++;
        this.date = date;
        this.assigned_class = assigned_class;
        this.subject = subject;
    }
    public Exam(DateTime date, String hour, String assigned_class, Subject subject, String speciality) {
        this.id = auto_inc_id++;
        this.date = date;
        this.assigned_class = assigned_class;
        this.subject = subject;
        this.speciality = speciality;
        this.hour = hour;
    }

    public DateTime getDateTime(){
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getDate(){

        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
        String str = timeFormat.print(date);
        return str;
    }
    public String getSpecialty() {return " " + speciality;}

    public void setSpecialty(String specialty) {
        this.speciality = specialty;
    }

   /* public String getHour(){
        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("HH:mm");
        String str = timeFormat.print(date);
        return str;
    }*/




    public int getDay(){
        return date.getDayOfMonth();
    }

    public int getMonth(){
        return date.getMonthOfYear();
    }

    public int getYear(){
        return date.getYear();
    }

    public String getHour(){
        String _hour = "";
        String _minute = "";

        if(date.getHourOfDay() < 10) _hour = "0";
        if(date.getMinuteOfHour() < 10) _minute = "0";
        _hour = _hour.concat(Integer.toString(date.getHourOfDay()));
        _minute = _minute.concat(Integer.toString(date.getMinuteOfHour()));
        return _hour + ":" + _minute;
    }

    public void setHour(String hour){ this.hour = hour;}

    public int getId() {
        return id;
    }


    public String getAssigned_class() {
        return assigned_class ;
    }

    public void setAssigned_class(String assigned_class) {
        this.assigned_class = assigned_class;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getSubjectName() {
        return subject.getName();
    }

    public String getNumberSubjects(){
        if(subject.getStudents().size() != 1) {
            return subject.getStudents().size() + " Alumnos";
        }else{
            return subject.getStudents().size() + " Alumno";
        }
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int compareTo(Exam exam) {
       return this.date.compareTo(exam.getDateTime());
    }

    public static int getAuto_inc_id() {
        return auto_inc_id;
    }

    public static void setAuto_inc_id(int auto_inc_id) {
        Exam.auto_inc_id = auto_inc_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
