package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.dao.EmployeeDAO;
import fu.de180120.dao.ProjectDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.pojo.Project;
import fu.de180120.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        // =========================
        // 1. TẠO DEPARTMENT
        // =========================

        Department department = new Department(
                "IT",
                "Da Nang"
        );

        // =========================
        // 2. TẠO 3 EMPLOYEE
        // =========================

        Employee e1 = new Employee(
                "nv1@company.com",
                "Nguyen Van A",
                Gender.MALE,
                new BigDecimal("1500"),
                LocalDate.of(2024, 1, 15)
        );

        Employee e2 = new Employee(
                "nv2@company.com",
                "Tran Thi B",
                Gender.FEMALE,
                new BigDecimal("1800"),
                LocalDate.of(2023, 6, 10)
        );

        Employee e3 = new Employee(
                "nv3@company.com",
                "Le Van C",
                Gender.OTHER,
                new BigDecimal("2000"),
                LocalDate.of(2022, 9, 20)
        );

        e1.setActive(true);
        e2.setActive(true);
        e3.setActive(true);

        // =========================
        // 3. GÁN EMPLOYEE VÀO DEPARTMENT
        // =========================

        department.addEmployee(e1);
        department.addEmployee(e2);
        department.addEmployee(e3);

        // =========================
        // 4. LƯU DEPARTMENT
        // =========================

        departmentDAO.save(department);

        // =========================
        // 5. LƯU 3 EMPLOYEE
        // =========================

        employeeDAO.save(e1);
        employeeDAO.save(e2);
        employeeDAO.save(e3);

        // =========================
        // 6. TẠO 2 PROJECT
        // =========================

        Project projectA = new Project(
                "PRJ-A",
                "Online Job Portal",
                new BigDecimal("50000"),
                LocalDate.of(2024, 1, 1),
                null
        );

        Project projectB = new Project(
                "PRJ-B",
                "AI Recruitment System",
                new BigDecimal("80000"),
                LocalDate.of(2024, 2, 1),
                null
        );

        // =========================
        // 7. LƯU 2 PROJECT
        // =========================

        projectDAO.save(projectA);
        projectDAO.save(projectB);

        // =========================
        // 8. PHÂN CÔNG CHÉO
        //
        // NV1 -> Project A + B
        // NV2 -> Project B
        // NV3 -> Project A
        // =========================

        employeeDAO.assignEmployeeToProject(
                e1.getId(),
                projectA.getId()
        );

        employeeDAO.assignEmployeeToProject(
                e1.getId(),
                projectB.getId()
        );

        employeeDAO.assignEmployeeToProject(
                e2.getId(),
                projectB.getId()
        );

        employeeDAO.assignEmployeeToProject(
                e3.getId(),
                projectA.getId()
        );

        // =========================
        // 9. ĐỌC LẠI EMPLOYEE
        // =========================

        Employee employee1 =
                employeeDAO.findByIdWithProjects(e1.getId());

        Employee employee2 =
                employeeDAO.findByIdWithProjects(e2.getId());

        Employee employee3 =
                employeeDAO.findByIdWithProjects(e3.getId());

        // =========================
        // 10. IN DANH SÁCH PROJECT
        // =========================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   DANH SÁCH PROJECT CỦA NHÂN VIÊN");
        System.out.println("========================================");

        printEmployeeProjects(employee1);
        printEmployeeProjects(employee2);
        printEmployeeProjects(employee3);

        // =========================
        // 11. THỐNG KÊ ACTIVE EMPLOYEE / PROJECT
        // =========================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   THỐNG KÊ ACTIVE EMPLOYEE / PROJECT");
        System.out.println("========================================");

        List<Object[]> stats =
                projectDAO.getActiveEmployeeStatsByProject();

        for (Object[] row : stats) {

            String projectCode = (String) row[0];
            String projectName = (String) row[1];
            Long employeeCount = (Long) row[2];
            BigDecimal totalSalary = (BigDecimal) row[3];

            System.out.println();
            System.out.println("Project: " + projectCode);
            System.out.println("Name: " + projectName);
            System.out.println("Active employees: " + employeeCount);
            System.out.println("Total salary: " + totalSalary);
        }

        // =========================
        // 12. ĐÓNG JPA
        // =========================

        JPAUtil.close();
    }

    private static void printEmployeeProjects(Employee employee) {

        System.out.println();
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Hire Date: " + employee.getHireDate());
        System.out.println("Gender: " + employee.getGender());
        System.out.println("Active: " + employee.isActive());

        System.out.println("Projects:");

        for (Project project : employee.getProjects()) {

            System.out.println(
                    "  - "
                            + project.getProjectCode()
                            + " | "
                            + project.getProjectName()
            );
        }
    }
}