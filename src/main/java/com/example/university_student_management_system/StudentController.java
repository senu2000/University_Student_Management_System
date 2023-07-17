package com.example.university_student_management_system;


import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    private Image image;

    @FXML
    private Button btnAddImage;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private ImageView imageview;


    @FXML
    private TextField tfdegree;

    @FXML
    private TextField tfgender;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tflastname;

    @FXML
    private TextField tfsurname;

    @FXML
    private Label lbl_WelcomeName;

    @FXML
    private Label file_path;

    @FXML
    private TableView<Table1Data> table1;

    @FXML
    private TableColumn<Table1Data,String> tdegree;

    @FXML
    private TableColumn<Table1Data,String> tgender;

    @FXML
    private TableColumn<Table1Data,String> tid;

    @FXML
    private TableColumn<Table1Data,String> tlastname;

    @FXML
    private TableColumn<Table1Data,String> tsurname;

    @FXML
    private TableColumn<Table1Data, String> tImage;

    private PreparedStatement pst;
    private ResultSet rs;

    HelloApplication n = new HelloApplication();

    public void account(){
        lbl_WelcomeName.setText(DisplayUserName.username);
    }

    @FXML
    void goAnalyze(ActionEvent event) {
        try {
            n.changeScene("Analyze.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void goHome(ActionEvent event) {
        try {
            n.changeScene("Dashbord.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void goRecords(ActionEvent event) {
        try {
            n.changeScene("Records.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void addStudent(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("INSERT INTO student (studentID,surname,lastname,gender,degree,image,current) VALUES (?,?,?,?,?,?,?)");

            if (tfid.getText().isEmpty() | tfsurname.getText().isEmpty() | tflastname.getText().isEmpty()
                | tfgender.getText().isEmpty() | tfdegree.getText().isEmpty() | imageview.getImage() == null){

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");
                alert.showAndWait();

            }else {

                //new line
                String new_filepath = file_path.getText().replace("\\","\\\\");

                pst.setString(1,tfid.getText());
                pst.setString(2,tfsurname.getText());
                pst.setString(3,tflastname.getText());
                pst.setString(4,tfgender.getText());
                pst.setString(5,tfdegree.getText());
                pst.setString(6,new_filepath);
                pst.setString(7,"");

                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Student added successfully");
                alert.showAndWait();

                showdata();
                clear();

            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void addImage(){
        FileChooser open = new FileChooser();

        Stage stage = new Stage();
        File file = open.showOpenDialog(stage);

        if (file != null){
            System.out.println("file path : " + file.getAbsolutePath());
            file_path.setText(file.getAbsolutePath());
            image = new Image(file.toURI().toString(), 250, 110, false, true);
            imageview.setImage(image);
        }else {
            System.out.println("Image doesn't exist.");
        }

    }

    public void clear(){
        tfid.setText("");
        tfsurname.setText("");
        tflastname.setText("");
        tfgender.setText("");
        tfdegree.setText("");
        imageview.setImage(null);
    }

    //check this one
    public void update(){
        try {
            Statement st;
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");

            if (tfid.getText().isEmpty() | tfsurname.getText().isEmpty() | tflastname.getText().isEmpty()
                    | tfgender.getText().isEmpty() | tfdegree.getText().isEmpty() | imageview.getImage() == null){

                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");
                alert.showAndWait();

            }else {

                //new line
                String new_filepath = file_path.getText().replace("\\","\\\\");

                String sql = "UPDATE `student` SET `surname`='" + tfsurname.getText()
                        + "',`lastname` = '" + tflastname.getText()
                        + "',`gender` = '" + tfgender.getText()
                        + "',`degree` = '" + tfdegree.getText()
                        + "',`image` = '" + new_filepath
                        + "' WHERE `studentID` = '" + tfid.getText() + "' ";

                st = connect.createStatement();
                st.executeUpdate(sql);

                showdata();
                clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Update successfully");
                alert.showAndWait();

            }

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void delete(){

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("DELETE FROM student WHERE studentID = '" + tfid.getText() + "'");

            if (tfid.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select StudentID");
                alert.showAndWait();
            }else {
                pst.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Success Message");
                alert.setHeaderText(null);
                alert.setContentText("Student deleted successfully");
                alert.showAndWait();

                showdata();
                clear();
            }


        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void selectData(){
        try {
            int num = table1.getSelectionModel().getSelectedIndex();
            Table1Data data2 = table1.getSelectionModel().getSelectedItem();

            if (num-1 < -1)
                return;
            tfid.setText(data2.getId());
            tfsurname.setText(data2.getFirstname());
            tflastname.setText(data2.getLastname());
            tfgender.setText(data2.getGender());
            tfdegree.setText(data2.getDegree());
            file_path.setText(data2.getImage());
            image = new Image(data2.getImage(), 110, 110, false, true);
            imageview.setImage(image);

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public ObservableList<Table1Data> listData() {

        ObservableList<Table1Data> dataList = FXCollections.observableArrayList();

        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("select * from student");

            rs = pst.executeQuery();

            Table1Data data;

            while (rs.next()){
                //Table1Data data;
                data = new Table1Data(rs.getString("studentID"),
                        rs.getString("surname"),
                        rs.getString("lastname"),
                        rs.getString("gender"),
                        rs.getString("degree"),
                        rs.getString("image"));

                dataList.addAll(data);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        return dataList;
    }

    public void showdata(){
        ObservableList<Table1Data> show = listData();
        try {
            tid.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("id"));
            tsurname.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("firstname"));
            tlastname.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("lastname"));
            tgender.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("gender"));
            tdegree.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("degree"));
            tImage.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("image"));

            table1.setItems(show);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void Print(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("select * from student");

            rs = pst.executeQuery();

            File file = new File("print");
            FileWriter fileWriter = new FileWriter("print");
            fileWriter.write("StudentID\t\tFullname\t\t\tGender\t\tDegree\t\tImage Path\n\n");

            int x = 1;
            while (rs.next()){
                fileWriter.write(rs.getString("studentID") + "\t\t");
                fileWriter.write(rs.getString("surname"));
                fileWriter.write(rs.getString("lastname") + "\t\t");
                fileWriter.write(rs.getString("gender") + "\t\t");
                fileWriter.write(rs.getString("degree") + "\t\t");
                fileWriter.write(rs.getString("image") + "\t\t");
                fileWriter.write("\n");
                x++;
            }

            fileWriter.close();
            System.out.println("work");
            System.out.println(file.getAbsolutePath());


        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            account();
            showdata();

        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
