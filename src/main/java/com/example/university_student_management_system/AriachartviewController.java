package com.example.university_student_management_system;

import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AriachartviewController {

    @FXML
    private AreaChart<String, Integer> areachart;

    @FXML
    private Button btnAreaChartShow;
    PreparedStatement pst;
    ResultSet rs;

    public void showAreachart(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current != '' ");

            XYChart.Series<String,Integer> chart = new XYChart.Series<>();

            rs = pst.executeQuery();

            while (rs.next()){
                Integer count1 = Integer.parseInt(rs.getString("count(studentID)"));

                chart.getData().add(new XYChart.Data<>("Total Students", count1));
            }


            areachart.getData().add(chart);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void showAriachart2(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("SELECT count(studentID) FROM student WHERE current = 'Inactive' ");

            XYChart.Series<String,Integer> chart = new XYChart.Series<>();

            rs = pst.executeQuery();

            while (rs.next()){
                Integer count1 = Integer.parseInt(rs.getString("count(studentID)"));

                chart.getData().add(new XYChart.Data<>("Total Inactive", count1));
            }


            areachart.getData().add(chart);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}
