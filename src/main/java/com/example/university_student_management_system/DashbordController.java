package com.example.university_student_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DashbordController implements Initializable {

    @FXML
    private Label lbl_WelcomeName;

    @FXML
    private Label tf_TotalEnrolled;

    @FXML
    private Label tf_TotalGraduated;

    @FXML
    private Label tf_TotalInactive;

    @FXML
    private Label tf_TotalStudents;
    PreparedStatement pst;
    ResultSet rs;

    HelloApplication main = new HelloApplication();

    public void account(){
        lbl_WelcomeName.setText(DisplayUserName.username);
    }


    @FXML
    void UserAnalyze(ActionEvent event) throws IOException {
        try {
            main.changeScene("Analyze.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void UserRecords(ActionEvent event) throws IOException {
        main.changeScene("Records.fxml");

    }

    @FXML
    void UserStudent(ActionEvent event) throws IOException {
        try {

            main.changeScene("Student.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void showTotalStudents(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current != '' ");
            rs = pst.executeQuery();

            while (rs.next()){
                String totalStudent = rs.getString("count(studentID)");
                tf_TotalStudents.setText(totalStudent);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void showTotalInactiveStudents(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current = 'Inactive' ");
            rs = pst.executeQuery();

            while (rs.next()){
                String totalInactiveStudent = rs.getString("count(studentID)");
                tf_TotalInactive.setText(totalInactiveStudent);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void showTotalEnrolledStudents(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current = 'Enrolled' ");
            rs = pst.executeQuery();

            while (rs.next()){
                String totalEnrolledStudent = rs.getString("count(studentID)");
                tf_TotalEnrolled.setText(totalEnrolledStudent);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void showTotalGraduatedStudents(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current = 'Graduated' ");
            rs = pst.executeQuery();

            while (rs.next()){
                String totalGraduatedStudent = rs.getString("count(studentID)");
                tf_TotalGraduated.setText(totalGraduatedStudent);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void logout() throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Loggin.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        account();
        showTotalStudents();
        showTotalInactiveStudents();
        showTotalEnrolledStudents();
        showTotalGraduatedStudents();
    }
}
