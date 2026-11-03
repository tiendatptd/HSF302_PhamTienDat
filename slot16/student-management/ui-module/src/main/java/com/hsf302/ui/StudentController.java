package com.hsf302.ui;

import com.hsf302.entity.Student;
import com.hsf302.repository.StudentRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StudentController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Long> colId;
    @FXML private TableColumn<Student, String> colFirst;
    @FXML private TableColumn<Student, String> colLast;
    @FXML private TableColumn<Student, String> colEmail;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;

    private final StudentRepository repository = new StudentRepository();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Gắn các cột trong bảng với các thuộc tính của lớp Student
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirst.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLast.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Tải dữ liệu lần đầu
        loadData();
    }

    private void loadData() {
        List<Student> students = repository.findAll();
        studentList.setAll(students);
        studentTable.setItems(studentList);
    }

    @FXML
    public void addStudent(ActionEvent event) {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        Student student = Student.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        try {
            repository.save(student);
            loadData(); // Tải lại bảng sau khi lưu thành công
            txtFirstName.clear();
            txtLastName.clear();
            txtEmail.clear();
        } catch (Exception e) {
            showAlert("Lỗi Database", "Không thể lưu sinh viên (có thể trùng email).");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
