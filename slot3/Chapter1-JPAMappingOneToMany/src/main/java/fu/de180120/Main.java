package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.dao.EmployeeDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import fu.de180120.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        // 1) Tạo Department + 3 Employee, add qua helper method (TODO 2.4)
        Department it = new Department("Marketing", "Ha Noi");

        Employee e1 = new Employee("aa.nguyen@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("bb.tran@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("cc.le@company.com", "Le Van C", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        // 2) Chỉ persist (department) - cascade = ALL tự lo phần Employee (TODO 2.7)
        departmentDAO.save(it);
        System.out.println("Da luu Department, id = " + it.getId());

        // 3) Tìm lại kèm employees bằng JOIN FETCH (TODO 2.6) - không bị
        //    LazyInitializationException dù EntityManager của lần tìm này đã đóng,
        //    vì employees đã được load ngay trong cùng 1 query.
        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        System.out.println("Phong ban: " + found.getName());
        for (Employee e : found.getEmployees()) {
            System.out.println(" - " + e);
        }

        // 4) Kiềm tra vi phạm Unique Email Constraint
        System.out.println("\n=== TEST UNIQUE EMAIL CONSTRAINT ===");
        try {
            EmployeeDAO employeeDAO = new EmployeeDAO();
            Employee duplicateEmp = new Employee("aa.nguyen@company.com", "Duplicate User", Gender.MALE,
                    new BigDecimal("10000000"), LocalDate.now());
            duplicateEmp.setDepartment(found);
            employeeDAO.save(duplicateEmp);
        } catch (Exception ex) {
            System.out.println("Thành công bắt lỗi trùng Email: " + ex.getMessage());
        }

        JPAUtil.close();
    }
}