package fu.de180120;

import fu.de180120.dao.EmployeeDAO;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
//
//        Employee emp = new Employee();
//        emp.setFullName("Nguyen Van A");
//        emp.setEmail("a.nguyen@fpt.edu.vn");
//        emp.setSalary(new BigDecimal("15000000.00"));
//        emp.setGender(Gender.MALE);
//        emp.setHireDate(LocalDate.of(2023, 5, 15));
//        emp.setActive(true);
//        System.out.println("Truoc khi save - ID: " + emp.getId());
//        dao.save(emp);
//        System.out.println("Sau khi save - ID: " + emp.getId());
//        System.out.println("Thong tin nhan vien: " + emp);

        System.out.println("=== KIEM THU findById ===");
        Employee foundEmp = dao.findById(1L);
        if (foundEmp != null) {
            System.out.println("Tim thấy nhan vien: " + foundEmp);
        } else {
            System.out.println("Khong tim thay nhan vien voi ID = 1");
        }

        // 2. Kiem thu findById voi ID KHONG ton tai (vi dụ 9999L)
        Employee notFound = dao.findById(9999L);
        System.out.println("Tim voi ID 9999: " + notFound); // Ket qua ky vong: null

        // 3. Kiem thu findAll
        System.out.println("\n=== KIEM THU findAll ===");
        List<Employee> employees = dao.findAll();
        System.out.println("Tong so nhan vien trong DB: " + employees.size());
        for (Employee e : employees) {
            System.out.println(" - " + e);
        }
    }
}