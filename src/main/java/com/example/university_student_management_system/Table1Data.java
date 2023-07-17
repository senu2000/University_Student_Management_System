package com.example.university_student_management_system;

public class Table1Data {

    private String id;
    private String firstname;
    private String lastname;
    private String gender;
    private String degree;
    private String image;
    private String current;

    public Table1Data(String id, String firstname, String lastname, String gender, String degree, String image ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.degree = degree;
        this.image = image;
    }

    public Table1Data(String id, String firstname, String lastname, String gender, String degree, String image, String current ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.degree = degree;
        this.image = image;
        this.current = current;
    }


    public String getId(){
        return id;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public String getGender(){
        return gender;
    }

    public String getDegree(){
        return degree;
    }

    public String getImage(){
        return image;
    }

    public String getCurrent(){
        return current;
    }

}
