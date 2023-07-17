package com.example.university_student_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseConnector {

    public static Connection connectDb(){

        PreparedStatement pst;

        try{

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect =
                    DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("select * from users where Username = ? and Password = ?");


            return connect;

        }catch(Exception e){e.printStackTrace();}

        return null;
    }


}
