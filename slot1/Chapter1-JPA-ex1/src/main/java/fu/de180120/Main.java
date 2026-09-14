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

        // TODO 0.5:
//        Employee e1 = new Employee();
//        e1.setFullName("Tran Van C");
//        e1.setEmail("c.tran@fpt.edu.vn");
//        e1.setSalary(new BigDecimal("20000000"));
//        e1.setGender(Gender.MALE);
//        e1.setHireDate(LocalDate.now());
//        e1.setActive(true);
//        dao.save(e1);

        // 2. Test findByEmail - TON TAI
        Employee found = dao.findByEmail("c.tran@fpt.edu.vn");
        System.out.println("Tim theo email (co ket qua): " + found);

        // 3. Test findByEmail - KHONG TON TAI
        Employee notFoundEmail = dao.findByEmail("notfound@fpt.edu.vn");
        System.out.println("Tim theo email (khong co ket qua): " + notFoundEmail);

        // 4. Test findBySalaryGreaterThanAndActive
        List<Employee> highSalaryEmps = dao.findBySalaryGreaterThanAndActive(new BigDecimal("10000000"));
        System.out.println("Danh sach nhan vien luong > 10M (" + highSalaryEmps.size() + " nhan vien):");
        highSalaryEmps.forEach(System.out::println);

        //TODO 0.6: UPDATE
        System.out.println("\n=== KIEM THU TODO 0.6 (UPDATE: merge) ===");
        Employee empToUpdate = dao.findById(1L);
        if (empToUpdate != null) {
            System.out.println("Truoc khi update: " + empToUpdate);

            // Tang luong them 5,000,000
            BigDecimal oldSalary = empToUpdate.getSalary();
            BigDecimal newSalary = oldSalary.add(new BigDecimal("10000000.00"));
            empToUpdate.setSalary(newSalary);

            // Goi dao.update (merge object Detached)
            dao.update(empToUpdate);

            // Fetch lai tu DB de kiem tra gia tri da thuc su duoc luu hay chua
            Employee updatedEmp = dao.findById(1L);
            System.out.println("Sau khi update (Salary moi: " + updatedEmp.getSalary() + "): " + updatedEmp);
        } else {
            System.out.println("Khong tim thay nhan vien ID = 1 de update.");
        }

        // TODO 0.7: DELETE
        System.out.println("\n=== KIEM THU TODO 0.7 (DELETE: remove) ===");
        Long deleteId = 3L; // Chọn ID của nhân viên Tran Van C để xóa

        Employee empToDelete = dao.findById(deleteId);
        if (empToDelete != null) {
            System.out.println("Tim thay nhan vien can xoa: " + empToDelete);

            // Goi hàm delete
            boolean isDeleted = dao.delete(deleteId);
            System.out.println("Ket qua xoa ID " + deleteId + ": " + isDeleted);

            // Fetch lai tu DB de kiem tra: findById phai tra ve null
            Employee checkDeleted = dao.findById(deleteId);
            System.out.println("Kiem tra findById(" + deleteId + ") sau khi xoa: " + checkDeleted);
        } else {
            System.out.println("Khong tim thay nhan vien ID = " + deleteId + " de xoa.");
        }
    }
}