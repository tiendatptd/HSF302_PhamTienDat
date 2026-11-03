package com.hsf302.ui;

import com.hsf302.entity.Student;
import com.hsf302.service.StudentService; // Import thêm Service
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
    @FXML private TextField txtSearch;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtEmail;

    // Thay thế Repository bằng Service
    private final StudentService service = new StudentService();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    // Biến lưu trữ sinh viên đang được click chọn trên bảng
    private Student selectedStudent;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirst.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLast.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Bắt sự kiện khi click vào một dòng trong bảng để hiển thị dữ liệu lên form
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedStudent = newSelection;
                txtFirstName.setText(newSelection.getFirstName());
                txtLastName.setText(newSelection.getLastName());
                txtEmail.setText(newSelection.getEmail());
            }
        });

        loadData();
    }

    private void loadData() {
        // Dùng Service thay cho Repository
        List<Student> students = service.getAllStudents();
        studentList.setAll(students);
        studentTable.setItems(studentList);
    }

    @FXML
    public void addStudent(ActionEvent event) {
        if (!validateInput()) return;

        Student student = Student.builder()
                .firstName(txtFirstName.getText().trim())
                .lastName(txtLastName.getText().trim())
                .email(txtEmail.getText().trim())
                .build();

        try {
            // Dùng Service thay cho Repository
            if (!service.saveStudent(student)) {
                showAlert("Lỗi", "Email này đã được sử dụng. Vui lòng nhập email khác.");
                return;
            }
            loadData();
            clearForm();
        } catch (Exception e) {
            showAlert("Lỗi Database", "Không thể thêm sinh viên.");
        }
    }

    @FXML
    public void updateStudent(ActionEvent event) {
        if (selectedStudent == null) {
            showAlert("Thông báo", "Vui lòng chọn một sinh viên trong bảng để cập nhật!");
            return;
        }
        if (!validateInput()) return;

        selectedStudent.setFirstName(txtFirstName.getText().trim());
        selectedStudent.setLastName(txtLastName.getText().trim());
        selectedStudent.setEmail(txtEmail.getText().trim());

        try {
            // Dùng Service thay cho Repository
            service.updateStudent(selectedStudent);
            loadData();
            clearForm();
        } catch (Exception e) {
            showAlert("Lỗi Database", "Email đã tồn tại Không thể cập nhật sinh viên.");
        }
    }

    @FXML
    public void deleteStudent(ActionEvent event) {
        if (selectedStudent == null) {
            showAlert("Thông báo", "Vui lòng chọn một sinh viên trong bảng để xóa!");
            return;
        }

        try {
            // Dùng Service thay cho Repository
            service.deleteStudent(selectedStudent.getId());
            loadData();
            clearForm();
        } catch (Exception e) {
            showAlert("Lỗi Database", "Không thể xóa sinh viên.");
        }
    }

    private boolean validateInput() {
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        String email = txtEmail.getText().trim();
        if (!email.matches("^[A-Za-z0-9](?:[A-Za-z0-9.+-]*[A-Za-z0-9])?@gmail\\.com$") || email.contains("..")) {
            showAlert("Lỗi", "Địa chỉ email không hợp lệ. Vui lòng nhập đúng định dạng Gmail (ví dụ: ten@gmail.com)!");
            return false;
        }

        return true;
    }

    private void clearForm() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        selectedStudent = null;
        studentTable.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void searchStudent(ActionEvent event) {
        String keyword = txtSearch.getText();
        List<Student> result = service.searchStudents(keyword);
        studentList.setAll(result);
    }

    @FXML
    public void resetSearch(ActionEvent event) {
        txtSearch.clear();
        loadData();
    }
}