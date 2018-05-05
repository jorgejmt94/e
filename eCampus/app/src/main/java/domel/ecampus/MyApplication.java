package domel.ecampus;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;


import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domel.ecampus.Activity.MainMenuActivity;
import domel.ecampus.Model.Exam;
import domel.ecampus.Model.StatusInstance;
import domel.ecampus.Model.Student;
import domel.ecampus.Model.Subject;
import domel.ecampus.Model.User;


public class MyApplication extends Application{


    private static final String APP_STATUS_FILENAME = "persisted_data.json";
    private static final String APP_STATUS_DEFAULT_FILENAME = "default_data.json";

    private static ArrayList<Subject> subjects;

    private static ArrayList<Student> students;

    private static ArrayList<Exam> exams;

    private static boolean remembered;


    public MyApplication() {
        students = new ArrayList<>();
        subjects = new ArrayList<>();
        exams = new ArrayList<>();
    }

    public MyApplication(ArrayList<Subject> subjects, ArrayList<Student> students, ArrayList<Exam> exams) {
        MyApplication.subjects = subjects;
        MyApplication.students = students;
        MyApplication.exams = exams;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        remembered = false;
    }

    public void persist(){
        //here we define persisting logic of MyApplication information.

        if(Tools.isExternalStorageWritable()){

            new PersistanceTask(new StatusInstance(subjects, students, exams, remembered))
                    .execute((Void)null);

        }
    }

    public class PersistanceTask extends AsyncTask<Void, Void, Boolean> {

        private StatusInstance statusInstance;

        PersistanceTask(StatusInstance si) {
            this.statusInstance = si;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ObjectMapper obm = new ObjectMapper();
            obm.registerModule(new JodaModule());
            obm.enable(MapperFeature.USE_STATIC_TYPING);
            obm.setVisibilityChecker(obm.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));

            try {
                OutputStreamWriter osw = new OutputStreamWriter(
                        openFileOutput(APP_STATUS_FILENAME, 0),
                        "UTF-8"
                );
                File storageDir = getExternalFilesDir("huehue");
                if (storageDir != null) {
                    storageDir.mkdirs();
                }
                File file = File.createTempFile(
                        "huohuo",  /* prefix */
                        ".json",         /* suffix */
                        storageDir      /* directory */
                );


                obm.writeValue(osw, statusInstance);
                obm.writeValue(file, statusInstance);
                Log.d("persisting", obm.writeValueAsString(statusInstance));
                osw.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.d("write_result", success.toString());
        }

        @Override
        protected void onCancelled() {
        }
    }

    public void loadStatusFromDisk(){
        if(Tools.isExternalStorageReadable()){

            ObjectMapper obm = new ObjectMapper();
            obm.registerModule(new JodaModule());
            obm.enable(MapperFeature.USE_STATIC_TYPING);
            obm.setVisibilityChecker(obm.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));

            /*File dir = getFilesDir();
            File file = new File(dir, APP_STATUS_FILENAME);
            file.delete();
            return;*/

            if(Tools.fileExists(getApplicationContext(), APP_STATUS_FILENAME)){
                //load previous data
                Log.d("loading", "status_data");
                try{
                    InputStreamReader isr = new InputStreamReader(
                            openFileInput(APP_STATUS_FILENAME),
                            "UTF-8"
                    );

                    initializeData(obm.readValue(isr, StatusInstance.class));
                    isr.close();

                }catch (FileNotFoundException e){
                    Log.w("LOAD_DATA", "Status file not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                //load default data
                Log.d("loading", "default_data");
                try{
                    InputStreamReader isr = new InputStreamReader(
                            getAssets().open(APP_STATUS_DEFAULT_FILENAME),
                            "UTF-8"
                    );

                    initializeData(obm.readValue(isr, StatusInstance.class));
                    isr.close();

                } catch (IOException e) {
                    Log.w("LOAD DATA", "Default status file not found");
                }
            }

        }
    }

    public void updateIndexs(){
        int greater = 0;
        for(Student s: students){
            if(greater < s.getId()){
                greater = s.getId();
            }
        }
        Student.setAuto_inc_id(greater + 1);

        greater = 0;
        for(Subject s: subjects){
            if(greater < s.getId()){
                greater = s.getId();
            }
        }
        Subject.setAuto_inc_id(greater + 1);

        greater = 0;
        for(Exam s: exams){
            if(greater < s.getId()){
                greater = s.getId();
            }
        }
        Exam.setAuto_inc_id(greater + 1);
    }

    private   void initializeData(StatusInstance si) {
        MyApplication.students = si.getStudents();
        MyApplication.subjects = si.getSubjects();
        MyApplication.exams = si.getExams();
        MyApplication.remembered = si.isRemember();
    }


    public   void addStudent(Student student){
        students.add(student);
        persist();
    }

    public   void deleteStudent(Student student){
        for (Subject s : student.getSubjects()){
            s.unrollStudent(student);
        }
        students.remove(student);
        persist();
    }

    public   void addSubject(Subject subject){
        subjects.add(subject);
        persist();
    }

    public   void suspendExams(Subject subject){
        for (Exam e: subject.getExams()){
            exams.remove(e);
        }
        subject.suspendExams();
        persist();
    }

    public void unroll(Student student, Subject subject){
        student.unrollSubject(subject);
        subject.unrollStudent(student);
        persist();
    }

    public   void enroll(Student student, Subject subject){
        student.enrollSubject(subject);
        subject.enrollStudent(student);
        persist();
    }

    public   void deleteSubject(Subject subject){
        for (Student s: subject.getStudents()){
            s.unrollSubject(subject);
        }
        suspendExams(subject);
        subjects.remove(subject);
        persist();
    }

    public   void addExam(Exam exam, Subject subject){
        exams.add(exam);
        exam.setSubject(subject);
        subject.scheduleExam(exam);
        Collections.sort(exams);
        persist();
    }

    public   void deleteExam(Exam exam){
        exam.getSubject().getExams().remove(exam);
        exam.setSubject(null);
        exams.remove(exam);
        persist();
    }


    public   ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public   void setSubjects(ArrayList<Subject> subjects) {
        MyApplication.subjects = subjects;
        persist();
    }

    public   ArrayList<Student> getStudents() {
        return students;
    }

    public   void setStudents(ArrayList<Student> students) {
        MyApplication.students = students;
        persist();
    }

    public   ArrayList<Exam> getExams() {
        return exams;
    }



    public   void setExams(ArrayList<Exam> exams) {
        MyApplication.exams = exams;
        persist();
    }

    public   boolean isRemembered() {
        return remembered;
    }

    public   void setRemembered(boolean remembered) {
        MyApplication.remembered = remembered;
        persist();
    }

    public   Subject getSubjectById(int id) {

        for (Subject s : subjects){
            if(s.getId() == id) return s;
        }

        return null;
    }

    public   Student getStudentById(int id) {
        for (Student s : students){
            if(s.getId() == id) return s;
        }

        return null;
    }


    public   Exam getExamById(int id) {

        for (Exam s : exams){
            if(s.getId() == id) return s;
        }

        return null;
    }

    public   void addExam(Exam exam){
        exams.add(exam);
        Collections.sort(exams);
        persist();
    }

}
