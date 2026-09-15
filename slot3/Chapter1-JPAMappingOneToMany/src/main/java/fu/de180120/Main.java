package fu.de180120;

import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("test@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());

        dept.addEmployee(emp);

        System.out.println(dept.getEmployees().contains(emp)); // Output: true
        System.out.println(emp.getDepartment() == dept);       // Output: true
    }
}