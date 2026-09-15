package fu.de180120;

import fu.de180120.dao.DepartmentDAO;
import fu.de180120.pojo.Department;
import fu.de180120.pojo.Employee;
import fu.de180120.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        try {
            Long deptId = 1L; // Thay id tương ứng có trong DB của bạn

            System.out.println("=== TEST JPQL JOIN FETCH ===");
            Department dept = departmentDAO.findByIdWithEmployees(deptId);

            if (dept != null) {
                System.out.println("Tên phòng ban: " + dept.getName());

                // EntityManager đã đóng trong DAO, nhưng lệnh bên dưới không bị LazyInitializationException
                System.out.println("Số lượng nhân viên: " + dept.getEmployees().size());
                for (Employee e : dept.getEmployees()) {
                    System.out.println(" - Nhân viên: " + e.getFullName() + " | Email: " + e.getEmail());
                }
            } else {
                System.out.println("Không tìm thấy Department với ID: " + deptId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JPAUtil.close();
        }
    }
}