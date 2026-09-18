//package fu.de180120;
//
//import fu.de180120.dao.DepartmentDAO;
//import fu.de180120.pojo.Department;
//import fu.de180120.pojo.Employee;
//import fu.de180120.pojo.Gender;
//import fu.de180120.util.JPAUtil;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//public class Main {
//    public static void main(String[] args) {
//        DepartmentDAO departmentDAO = new DepartmentDAO();
//
//        Department d1 = new Department("IT", "Building A");
//        d1.addEmployee(new Employee("a@company.com", "Nguyen A", Gender.MALE, new BigDecimal("1000"), LocalDate.now()));
//        departmentDAO.save(d1);
//
//        Department d2 = new Department("HR", "Building B");
//        d2.addEmployee(new Employee("b@company.com", "Tran B", Gender.FEMALE, new BigDecimal("1200"), LocalDate.now()));
//        departmentDAO.save(d2);
//
//        System.out.println("\n=== BẮT ĐẦU FIX N+1 QUERY BẰNG JOIN FETCH ===");
//
//        List<Department> departments = departmentDAO.findAllWithEmployees();
//
//        for (Department dept : departments) {
//            System.out.println("Phòng ban: " + dept.getName()
//                    + " | Số nhân viên: " + dept.getEmployees().size());
//        }
//
//        JPAUtil.close();
//    }
//}

package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.pojo.Project;
import fu.de180120.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        // =========================
        // 1. TẠO PROJECT
        // =========================

        Project p1 = new Project(
                "PRJ001",
                "Online Job Portal",
                new BigDecimal("50000"),
                LocalDate.now(),
                null
        );

        Project p2 = new Project(
                "PRJ002",
                "AI Recruitment System",
                new BigDecimal("80000"),
                LocalDate.now(),
                null
        );

        // =========================
        // 2. TẠO EMPLOYEE
        // =========================

        Employee e1 = new Employee(
                "a@company.com",
                "Nguyen A",
                Gender.MALE,
                new BigDecimal("1000"),
                LocalDate.now()
        );

        Employee e2 = new Employee(
                "b@company.com",
                "Tran B",
                Gender.FEMALE,
                new BigDecimal("1200"),
                LocalDate.now()
        );

        // =========================
        // 3. TEST MANY-TO-MANY
        // Employee là owning side
        // =========================

        e1.getProjects().add(p1);
        e1.getProjects().add(p2);

        e2.getProjects().add(p1);

        // =========================
        // 4. TẠO DEPARTMENT
        // =========================

        Department d1 = new Department("IT", "Building A");

        d1.addEmployee(e1);
        d1.addEmployee(e2);

        // =========================
        // 5. SAVE
        // =========================

        departmentDAO.save(d1);

        // =========================
        // 6. KIỂM TRA EMPLOYEE
        // =========================

        System.out.println("\n=== TEST EMPLOYEE - PROJECT ===");

        System.out.println("Employee: " + e1.getFullName());
        System.out.println("Projects: " + e1.getProjects().size());

        for (Project project : e1.getProjects()) {
            System.out.println(
                    "  - " + project.getProjectCode()
                            + " | " + project.getProjectName()
            );
        }

        System.out.println();

        System.out.println("Employee: " + e2.getFullName());
        System.out.println("Projects: " + e2.getProjects().size());

        for (Project project : e2.getProjects()) {
            System.out.println(
                    "  - " + project.getProjectCode()
                            + " | " + project.getProjectName()
            );
        }

        // =========================
        // 7. KIỂM TRA DATABASE
        // =========================

        System.out.println("\n=== KIỂM TRA QUAN HỆ N-N ===");
        System.out.println("Employee A -> Project 1");
        System.out.println("Employee A -> Project 2");
        System.out.println("Employee B -> Project 1");

        JPAUtil.close();
    }
}