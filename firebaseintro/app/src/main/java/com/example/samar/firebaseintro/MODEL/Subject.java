package com.example.samar.firebaseintro.MODEL;

public class Subject {

    private  String SubjectName;
    private  String SubjectCode;



    public  Subject()
    {

    }


    public Subject(String subjectName, String subjectCode) {
        SubjectName = subjectName;
        SubjectCode = subjectCode;
    }

    public String getSubjectCode() {
        return SubjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        SubjectCode = subjectCode;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
}
