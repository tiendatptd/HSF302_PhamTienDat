package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        // 1. Tạo 2 Phòng ban mẫu có chứa Employee
        Department d1 = new Department("IT", "Building A");
        d1.addEmployee(new Employee("a@company.com", "Nguyen A", Gender.MALE, new BigDecimal("1000"), LocalDate.now()));
        departmentDAO.save(d1);

        Department d2 = new Department("HR", "Building B");
        d2.addEmployee(new Employee("b@company.com", "Tran B", Gender.FEMALE, new BigDecimal("1200"), LocalDate.now()));
        departmentDAO.save(d2);

        System.out.println("\n=== TÁI HIỆN N+1 QUERY PROBLEM ===");

        EntityManager em = JPAUtil.getEMF().createEntityManager();
        try {
            // Câu SELECT thứ 1: Lấy tất cả N Departments (N = 2)
            List<Department> departments = em.createQuery("SELECT d FROM Department d", Department.class)
                    .getResultList();

            // Vòng lặp bắn thêm N câu SELECT riêng để lấy Employees cho từng Department
            for (Department dept : departments) {
                System.out.println("Phòng ban: " + dept.getName()
                        + " | Số nhân viên: " + dept.getEmployees().size());
            }

        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}