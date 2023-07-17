package com.example.university_student_management_system;

import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.net.Connection;
import com.almasb.fxgl.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.w3c.dom.UserDataHandler;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private Button btn_Login;

    @FXML
    private Hyperlink hpr_Signup;

    @FXML
    private PasswordField ps_Password;

    @FXML
    private TextField tf_Username;

    private Connection  con;
    private Statement statement;
    private PreparedStatement pst;
    private ResultSet rs;

    @FXML
    void UserLogin(ActionEvent event) {


        String uname = tf_Username.getText();
        String pass = ps_Password.getText();

        if (uname.equals("") && pass.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Enter username or password");
            alert.showAndWait();
        }else {

            try {


                Class.forName("com.mysql.cj.jdbc.Driver");

                java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
                pst = connect.prepareStatement("select * from users where Username = ? and Password = ?");

                pst.setString(1,uname);
                pst.setString(2,pass);

                rs = pst.executeQuery();

                if (rs.next()){

                    DisplayUserName.username = rs.getString("Username");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login success");
                    alert.showAndWait();

                    HelloApplication m = new HelloApplication();
                    m.changeScene("Dashbord.fxml");

                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password");
                    alert.showAndWait();

                    tf_Username.setText("");
                    ps_Password.setText("");
                    tf_Username.requestFocus();
                }


            }
            catch (Exception e){
                System.out.println(e);
            }


        }


    }

    @FXML
    void User_Signup(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Signup.fxml");

    }

}
