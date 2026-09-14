package fu.de180120;

import fu.de180120.dao.EmployeeDAO;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        Employee emp = new Employee();
        emp.setFullName("Nguyen Van A");
        emp.setEmail("a.nguyen@fpt.edu.vn");
        emp.setSalary(new BigDecimal("15000000.00"));
        emp.setGender(Gender.MALE);
        emp.setHireDate(LocalDate.of(2023, 5, 15));
        emp.setActive(true);
        System.out.println("Truoc khi save - ID: " + emp.getId());
        dao.save(emp);
        System.out.println("Sau khi save - ID: " + emp.getId());
        System.out.println("Thong tin nhan vien: " + emp);
    }
}