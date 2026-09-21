package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.dao.EmployeeDAO;
import fu.de180120.dao.ProjectDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.pojo.Project;
import fu.de180120.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        // =====================================================
        // 1. TẠO DEPARTMENT
        // =====================================================

        Department department = new Department(
                "IT",
                "Da Nang"
        );

        // =====================================================
        // 2. TẠO 3 EMPLOYEE
        // =====================================================

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

        // =====================================================
        // 3. GÁN EMPLOYEE VÀO DEPARTMENT
        // =====================================================

        department.addEmployee(e1);
        department.addEmployee(e2);
        department.addEmployee(e3);

        // =====================================================
        // 4. LƯU DEPARTMENT
        //
        // Department có cascade = ALL nên 3 Employee
        // cũng được lưu tự động.
        // =====================================================

        departmentDAO.save(department);

        // =====================================================
        // 5. TẠO 2 PROJECT
        // =====================================================

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

        // =====================================================
        // 6. LƯU PROJECT
        // =====================================================

        projectDAO.save(projectA);
        projectDAO.save(projectB);

        // =====================================================
        // 7. PHÂN CÔNG NHÂN VIÊN VÀO PROJECT
        //
        // NV1 -> Project A + B
        // NV2 -> Project B
        // NV3 -> Project A
        // =====================================================

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

        // =====================================================
        // 8. ĐỌC LẠI EMPLOYEE + PROJECT
        // =====================================================

        Employee employee1 =
                employeeDAO.findByIdWithProjects(e1.getId());

        Employee employee2 =
                employeeDAO.findByIdWithProjects(e2.getId());

        Employee employee3 =
                employeeDAO.findByIdWithProjects(e3.getId());

        // =====================================================
        // 9. IN DANH SÁCH PROJECT CỦA TỪNG EMPLOYEE
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   DANH SÁCH PROJECT CỦA NHÂN VIÊN");
        System.out.println("========================================");

        printEmployeeProjects(employee1);
        printEmployeeProjects(employee2);
        printEmployeeProjects(employee3);

        // =====================================================
        // 10. TODO 5.8
        // THỐNG KÊ ACTIVE EMPLOYEE / PROJECT
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   TODO 5.8 - THỐNG KÊ PROJECT");
        System.out.println("========================================");

        List<Object[]> stats =
                projectDAO.getActiveEmployeeStatsByProject();

        for (Object[] row : stats) {

            String projectCode = (String) row[0];
            String projectName = (String) row[1];
            Long employeeCount = (Long) row[2];
            BigDecimal totalSalary = (BigDecimal) row[3];

            System.out.println();
            System.out.println("Project Code: " + projectCode);
            System.out.println("Project Name: " + projectName);
            System.out.println("Active Employees: " + employeeCount);
            System.out.println("Total Salary: " + totalSalary);
        }

        // =====================================================
        // 11. TODO 5.10
        // TÌM ACTIVE EMPLOYEE THAM GIA > 1 PROJECT
        //
        // Phải chạy TODO 5.10 TRƯỚC TODO 5.9
        // vì lúc này NV1 vẫn đang có Project A + B.
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   TODO 5.10 - ACTIVE EMPLOYEE > 1 PROJECT");
        System.out.println("========================================");

        List<Employee> employeesWithMoreThanOneProject =
                employeeDAO.findActiveEmployeesWithMoreThanOneProject();

        if (employeesWithMoreThanOneProject.isEmpty()) {

            System.out.println(
                    "Không có active employee nào tham gia hơn 1 project."
            );

        } else {

            for (Employee employee : employeesWithMoreThanOneProject) {

                System.out.println();
                System.out.println("Employee: " + employee.getFullName());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("Active: " + employee.isActive());
                System.out.println(
                        "Number of Projects: "
                                + employee.getProjects().size()
                );

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

        // =====================================================
        // 12. TODO 5.9
        // KIỂM TRA SỐ DÒNG employee_project TRƯỚC KHI GỠ
        // =====================================================

        long beforeCount = countEmployeeProject();

        System.out.println();
        System.out.println("========================================");
        System.out.println("   TODO 5.9 - TRƯỚC KHI UNASSIGN");
        System.out.println("========================================");

        System.out.println(
                "Số dòng employee_project trước khi gỡ: "
                        + beforeCount
        );

        // =====================================================
        // 13. GỠ NV1 KHỎI PROJECT A
        //
        // NV1 trước:
        //   -> Project A
        //   -> Project B
        //
        // Sau:
        //   -> Project B
        // =====================================================

        employeeDAO.unassignEmployeeFromProject(
                e1.getId(),
                projectA.getId()
        );

        // =====================================================
        // 14. KIỂM TRA SỐ DÒNG employee_project SAU KHI GỠ
        // =====================================================

        long afterCount = countEmployeeProject();

        System.out.println();
        System.out.println("========================================");
        System.out.println("   TODO 5.9 - SAU KHI UNASSIGN");
        System.out.println("========================================");

        System.out.println(
                "Số dòng employee_project sau khi gỡ: "
                        + afterCount
        );

        System.out.println(
                "Số dòng đã mất: "
                        + (beforeCount - afterCount)
        );

        // =====================================================
        // 15. ĐỌC LẠI NV1 SAU KHI UNASSIGN
        // =====================================================

        Employee employeeAfterUnassign =
                employeeDAO.findByIdWithProjects(e1.getId());

        // =====================================================
        // 16. KIỂM TRA EMPLOYEE VÀ PROJECT GỐC
        // =====================================================

        Project projectAFromDB =
                projectDAO.findById(projectA.getId());

        Project projectBFromDB =
                projectDAO.findById(projectB.getId());

        System.out.println();
        System.out.println("========================================");
        System.out.println("   KIỂM TRA ENTITY GỐC");
        System.out.println("========================================");

        System.out.println(
                "Employee NV1 còn tồn tại: "
                        + (employeeAfterUnassign != null)
        );

        System.out.println(
                "Project A còn tồn tại: "
                        + (projectAFromDB != null)
        );

        System.out.println(
                "Project B còn tồn tại: "
                        + (projectBFromDB != null)
        );

        // =====================================================
        // 17. IN PROJECT CỦA NV1 SAU KHI UNASSIGN
        // =====================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("   PROJECT CỦA NV1 SAU KHI UNASSIGN");
        System.out.println("========================================");

        printEmployeeProjects(employeeAfterUnassign);

        // =====================================================
        // 18. ĐÓNG JPA
        // =====================================================

        JPAUtil.close();
    }

    // =========================================================
    // IN THÔNG TIN EMPLOYEE + PROJECT
    // =========================================================

    private static void printEmployeeProjects(Employee employee) {

        System.out.println();
        System.out.println("Employee: " + employee.getFullName());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Salary: " + employee.getSalary());
        System.out.println("Hire Date: " + employee.getHireDate());
        System.out.println("Gender: " + employee.getGender());
        System.out.println("Active: " + employee.isActive());

        System.out.println("Projects:");

        if (employee.getProjects().isEmpty()) {

            System.out.println("  - Không có project");

        } else {

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

    // =========================================================
    // ĐẾM SỐ DÒNG TRONG employee_project
    // =========================================================

    private static long countEmployeeProject() {

        EntityManager em =
                JPAUtil.getEMF().createEntityManager();

        try {

            Number count = (Number) em.createNativeQuery(
                    "SELECT COUNT(*) FROM employee_project"
            ).getSingleResult();

            return count.longValue();

        } finally {

            em.close();
        }
    }
}