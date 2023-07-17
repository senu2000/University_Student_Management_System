package com.example.university_student_management_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupController {

    @FXML
    private Button btn_Register;

    @FXML
    private Hyperlink hpr_Signup;

    @FXML
    private PasswordField pf_Password2;

    @FXML
    private TextField tf_Email;

    @FXML
    private TextField tf_Usename2;

    @FXML
    void User_Register(ActionEvent event) {
        PreparedStatement pst;
        ResultSet rs;

        String uname = tf_Usename2.getText();
        String pass = pf_Password2.getText();
        String mail = tf_Email.getText();

        try {

            //validationEmail();
            if (validationEmail()){

            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("INSERT INTO users (Email,Username,Password) VALUES (?,?,?)");

            pst.setString(1,mail);
            pst.setString(2,uname);
            pst.setString(3,pass);

            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Success Message");
            alert.setHeaderText(null);
            alert.setContentText("Signup success");
            alert.showAndWait();

            HelloApplication m = new HelloApplication();
            m.changeScene("Loggin.fxml");}

        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void User_Signup(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("Loggin.fxml");

    }

    public boolean validationEmail(){

        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");

        Matcher match = pattern.matcher(tf_Email.getText());

        if(match.find() && match.group().equals(tf_Email.getText())){

            return true;

        }else{

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();

            return false;

        }

    }

}
