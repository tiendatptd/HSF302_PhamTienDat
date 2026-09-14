package fu.de180120;

import fu.de180120.dao.EmployeeDAO;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("=========================================");
        System.out.println("      DEMO LUONG CRUD DAY DU             ");
        System.out.println("=========================================");

        String testEmail = "d.le@fpt.edu.vn";

        // Dọn dẹp dữ liệu cũ nếu email trùng từ trước (đảm bảo demo luôn chạy mượt)
        Employee oldEmp = dao.findByEmail(testEmail);
        if (oldEmp != null) {
            dao.delete(oldEmp.getId());
        }

        // ---------------------------------------------------------------------
        // STEP 1: CREATE (Tạo mới nhân viên)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 1. CREATE ---");
        Employee newEmp = new Employee();
        newEmp.setFullName("Le Van D");
        newEmp.setEmail(testEmail);
        newEmp.setSalary(new BigDecimal("12000000.00"));
        newEmp.setGender(Gender.FEMALE);
        newEmp.setHireDate(LocalDate.now());
        newEmp.setActive(true);

        System.out.println("Truoc khi save - ID: " + newEmp.getId());
        dao.save(newEmp);
        Long createdId = newEmp.getId();
        System.out.println("Sau khi save   - Generated ID: " + createdId);

        // ---------------------------------------------------------------------
        // STEP 2: READ (Đọc dữ liệu vừa tạo theo ID)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 2. READ (Sau khi CREATE) ---");
        Employee readEmp1 = dao.findById(createdId);
        System.out.println("Thong tin Nhan vien vua tao: " + readEmp1);

        // ---------------------------------------------------------------------
        // STEP 3: UPDATE (Cập nhật thông tin nhân viên)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 3. UPDATE ---");
        readEmp1.setSalary(new BigDecimal("18000000.00"));
        readEmp1.setFullName("Le Van D (Updated)");

        dao.update(readEmp1);
        System.out.println("Da goi dao.update() voi Salary va FullName moi.");

        // ---------------------------------------------------------------------
        // STEP 4: READ AGAIN (Kiểm tra dữ liệu sau Update)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 4. READ (Sau khi UPDATE) ---");
        Employee readEmp2 = dao.findById(createdId);
        System.out.println("Thong tin Nhan vien sau Update: " + readEmp2);

        // ---------------------------------------------------------------------
        // STEP 5: DELETE (Xóa nhân viên theo ID)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 5. DELETE ---");
        boolean isDeleted = dao.delete(createdId);
        System.out.println("Ket qua xoa ID " + createdId + ": " + isDeleted);

        // ---------------------------------------------------------------------
        // STEP 6: READ AGAIN (Xác nhận dữ liệu đã được xóa hoàn toàn)
        // ---------------------------------------------------------------------
        System.out.println("\n--- 6. READ (Sau khi DELETE) ---");
        Employee readEmp3 = dao.findById(createdId);
        if (readEmp3 == null) {
            System.out.println("Xac nhan: Khong tim thay Nhan vien voi ID = " + createdId + " (Gia tri tra ve null)");
        } else {
            System.out.println("Loi: Nhan vien van con ton tai trong CSDL!");
        }

        System.out.println("\n=========================================");
        System.out.println("      HOAN THANH DEMO LUONG CRUD         ");
        System.out.println("=========================================");
    }
}