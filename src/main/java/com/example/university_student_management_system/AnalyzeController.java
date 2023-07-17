package com.example.university_student_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AnalyzeController implements Initializable {


    @FXML
    private Button btnLineChartShow;

    @FXML
    private Label lbl_WelcomeName;

    @FXML
    private LineChart<String, Integer> linechart;

    PreparedStatement pst;
    PreparedStatement pst1;
    ResultSet rs;
    ResultSet rs1;

    HelloApplication o = new HelloApplication();

    public void account(){
        lbl_WelcomeName.setText(DisplayUserName.username);
    }

    @FXML
    void home(ActionEvent event) {
        try {
            o.changeScene("Dashbord.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void records(ActionEvent event) {
        try {
            o.changeScene("Records.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void student(ActionEvent event) {
        try {
            o.changeScene("Student.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void gotoBarchart(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Barchartview.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Total students vs Graduated");
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void gotoAriachart(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Ariachartview.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Total Student vs Inactive");
            stage.show();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void showChart(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE gender = 'male' ");

            XYChart.Series<String,Integer> chart = new XYChart.Series<>();

            rs = pst.executeQuery();

            while (rs.next()){
                Integer count1 = Integer.parseInt(rs.getString("count(studentID)"));

                chart.getData().add(new XYChart.Data<>("male", count1));
            }


            linechart.getData().add(chart);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void showchart2(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE gender = 'female' ");

            XYChart.Series<String,Integer> chart = new XYChart.Series<>();

            rs = pst.executeQuery();

            while (rs.next()){
                Integer count1 = Integer.parseInt(rs.getString("count(studentID)"));

                chart.getData().add(new XYChart.Data<>("female", count1));
            }


            linechart.getData().add(chart);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account();
        showChart();
        showchart2();

    }
}
