package com.example.university_student_management_system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RecordsController implements Initializable {


    @FXML
    private Button btnrClear;

    @FXML
    private Button btnrUpdate;

    @FXML
    private ComboBox<?> comboboxCurrent;
    private String current[] = {"Enrolled", "Graduated", "Inactive"};

    @FXML
    private Label lbl_WelcomeName;

    @FXML
    private ImageView rimageview;

    @FXML
    private TableView<Table1Data> table1;

    @FXML
    private TextField tfrid;

    @FXML
    private TableColumn<Table1Data, String> trCurrent;

    @FXML
    private TableColumn<Table1Data, String> trImage;

    @FXML
    private TableColumn<Table1Data, String> trdegree;

    @FXML
    private TableColumn<Table1Data, String> trgender;

    @FXML
    private TableColumn<Table1Data, String> trid;

    @FXML
    private TableColumn<Table1Data, String> trlastname;

    @FXML
    private TableColumn<Table1Data, String> trsurname;
    PreparedStatement pst;
    ResultSet rs;

    HelloApplication l = new HelloApplication();

    public void account(){
        lbl_WelcomeName.setText(DisplayUserName.username);
    }

    @FXML
    void Analyze(ActionEvent event) {
        try {
            l.changeScene("Analyze.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void Home(ActionEvent event) {
        try {
            l.changeScene("Dashbord.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @FXML
    void Student(ActionEvent event) {
        try {
            l.changeScene("Student.fxml");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void combobox(){

        List<String> list = new ArrayList<>();
        for (String contain : current){
            list.add(contain);
        }
        ObservableList addData = FXCollections.observableArrayList(list);
        comboboxCurrent.setItems(addData);

    }

    public ObservableList<Table1Data> listRecordData() {

        ObservableList<Table1Data> dataList = FXCollections.observableArrayList();

        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");
            pst = connect.prepareStatement("select * from student");

            rs = pst.executeQuery();

            Table1Data data1;

            while (rs.next()){
                //Table1Data data;
                data1 = new Table1Data(rs.getString("studentID"),
                        rs.getString("surname"),
                        rs.getString("lastname"),
                        rs.getString("gender"),
                        rs.getString("degree"),
                        rs.getString("image"),
                        rs.getString("current"));

                dataList.addAll(data1);
            }

        }
        catch (Exception e){
            System.out.println(e);
        }

        return dataList;
    }

    public void showRecordData(){
        ObservableList<Table1Data> show = listRecordData();
        try {
            trid.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("id"));
            trsurname.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("firstname"));
            trlastname.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("lastname"));
            trgender.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("gender"));
            trdegree.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("degree"));
            trImage.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("image"));
            trCurrent.setCellValueFactory(new PropertyValueFactory<Table1Data,String>("current"));

            table1.setItems(show);
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void selectRecordData(){
        try {
            int num = table1.getSelectionModel().getSelectedIndex();
            Table1Data data2 = table1.getSelectionModel().getSelectedItem();

            if (num-1 < -1)
                return;
            tfrid.setText(data2.getId());
            comboboxCurrent.getSelectionModel().clearSelection();
            String file_path = data2.getImage();
            Image image = new Image(file_path, 110, 110, false, true);
            rimageview.setImage(image);

        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public void updateRecordData(){
        try {
            Statement st;
            Class.forName("com.mysql.cj.jdbc.Driver");

            java.sql.Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/usms", "root", "");

            if (tfrid.getText().isEmpty() | comboboxCurrent.getSelectionModel().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields");
                alert.showAndWait();

            }else {
                String sql = "UPDATE student SET `current` = '" + comboboxCurrent.getSelectionModel().getSelectedItem()
                        + "' WHERE `studentID` = '" + tfrid.getText() + "'";

                st = connect.createStatement();
                st.executeUpdate(sql);

                showRecordData();
                clearRecordData();

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

    public void clearRecordData(){
        tfrid.setText("");
        comboboxCurrent.getSelectionModel().clearSelection();
        rimageview.setImage(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        account();
        combobox();
        showRecordData();

    }
}
