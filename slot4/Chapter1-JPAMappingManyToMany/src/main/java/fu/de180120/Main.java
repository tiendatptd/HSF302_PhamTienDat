package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        Department d1 = new Department("IT", "Building A");
        d1.addEmployee(new Employee("a@company.com", "Nguyen A", Gender.MALE, new BigDecimal("1000"), LocalDate.now()));
        departmentDAO.save(d1);

        Department d2 = new Department("HR", "Building B");
        d2.addEmployee(new Employee("b@company.com", "Tran B", Gender.FEMALE, new BigDecimal("1200"), LocalDate.now()));
        departmentDAO.save(d2);

        System.out.println("\n=== BẮT ĐẦU FIX N+1 QUERY BẰNG JOIN FETCH ===");

        List<Department> departments = departmentDAO.findAllWithEmployees();

        for (Department dept : departments) {
            System.out.println("Phòng ban: " + dept.getName()
                    + " | Số nhân viên: " + dept.getEmployees().size());
        }

        JPAUtil.close();
    }
}