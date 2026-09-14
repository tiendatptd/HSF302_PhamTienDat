package fu.de180120;

import fu.de180120.dao.EmployeeDAO;
import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Gender;
import jakarta.persistence.PersistenceException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
// DEMO TODO 0.8 & TODO 0.10: CRUD FLOW & ENTITY LIFECYCLE
// =====================================================================
        System.out.println("=========================================");
        System.out.println("      DEMO LUONG CRUD DAY DU             ");
        System.out.println("=========================================");

        String testEmail = "d.le@fpt.edu.vn";

// Dọn dẹp dữ liệu cũ nếu email trùng từ trước
        Employee oldEmp = dao.findByEmail(testEmail);
        if (oldEmp != null) {
            dao.delete(oldEmp.getId());
        }

// STEP 1: CREATE (Tạo mới nhân viên)
        System.out.println("\n--- 1. CREATE ---");

// [LIFECYCLE]: NEW / TRANSIENT (Mới tạo bằng new, ID = null, chưa có trong DB/EM)
        Employee newEmp = new Employee();
        newEmp.setFullName("Le Van D");
        newEmp.setEmail(testEmail);
        newEmp.setSalary(new BigDecimal("12000000.00"));
        newEmp.setGender(Gender.FEMALE);
        newEmp.setHireDate(LocalDate.now());
        newEmp.setActive(true);

        System.out.println("Truoc khi save - ID: " + newEmp.getId());

// [LIFECYCLE IN DAO]: em.persist() chuyển thành MANAGED trong Tx.
// [LIFECYCLE OUT DAO]: em.close() xong -> object chuyển sang DETACHED.
        dao.save(newEmp);
        Long createdId = newEmp.getId();
        System.out.println("Sau khi save   - Generated ID: " + createdId);

// STEP 2: READ (Đọc dữ liệu vừa tạo theo ID)
        System.out.println("\n--- 2. READ (Sau khi CREATE) ---");

// [LIFECYCLE]: findById() lấy từ DB ra (Managed trong Tx), hàm kết thúc em.close() -> DETACHED
        Employee readEmp1 = dao.findById(createdId);
        System.out.println("Thong tin Nhan vien vua tao: " + readEmp1);

// STEP 3: UPDATE (Cập nhật thông tin nhân viên)
        System.out.println("\n--- 3. UPDATE ---");

// [LIFECYCLE]: Thay đổi giá trị trên object DETACHED
        readEmp1.setSalary(new BigDecimal("18000000.00"));
        readEmp1.setFullName("Le Van D (Updated)");

// [LIFECYCLE IN DAO]: em.merge() tạo bản sao MANAGED để sync DB.
// [LIFECYCLE OUT DAO]: em.close() xong -> object tiếp tục DETACHED.
        dao.update(readEmp1);
        System.out.println("Da goi dao.update() voi Salary va FullName moi.");


// STEP 4: READ AGAIN (Kiểm tra dữ liệu sau Update)
        System.out.println("\n--- 4. READ (Sau khi UPDATE) ---");

// [LIFECYCLE]: Re-fetch từ DB -> DETACHED (xác nhận data đã sync)
        Employee readEmp2 = dao.findById(createdId);
        System.out.println("Thong tin Nhan vien sau Update: " + readEmp2);


// STEP 5: DELETE (Xóa nhân viên theo ID)
        System.out.println("\n--- 5. DELETE ---");

// [LIFECYCLE IN DAO]: em.find() (MANAGED) -> em.remove() chuyển thành REMOVED -> commit() xóa hẳn khỏi DB.
        boolean isDeleted = dao.delete(createdId);
        System.out.println("Ket qua xoa ID " + createdId + ": " + isDeleted);


// STEP 6: READ AGAIN (Xác nhận dữ liệu đã được xóa hoàn toàn)
        System.out.println("\n--- 6. READ (Sau khi DELETE) ---");

// [LIFECYCLE]: Truy vấn ID đã xóa -> trả về null
        Employee readEmp3 = dao.findById(createdId);
        if (readEmp3 == null) {
            System.out.println("Xac nhan: Khong tim thay Nhan vien voi ID = " + createdId + " (Gia tri tra ve null)");
        } else {
            System.out.println("Loi: Nhan vien van con ton tai trong CSDL!");
        }

        System.out.println("\n=========================================");
        System.out.println("      HOAN THANH DEMO LUONG CRUD         ");
        System.out.println("=========================================");

        // todo 0.9
        String duplicateEmail = "duplicate.test@fpt.edu.vn";

        // 0. Dọn dẹp dữ liệu cũ nếu email thử nghiệm đã tồn tại từ trước
        Employee existing = dao.findByEmail(duplicateEmail);
        if (existing != null) {
            dao.delete(existing.getId());
        }

        // 1. Tạo và save Employee thứ nhất (Kỳ vọng: THÀNH CÔNG)
        Employee emp1 = new Employee();
        emp1.setFullName("Nguyen Van Copy 1");
        emp1.setEmail(duplicateEmail);
        emp1.setSalary(new BigDecimal("10000000.00"));
        emp1.setGender(Gender.MALE);
        emp1.setHireDate(LocalDate.now());
        emp1.setActive(true);

        dao.save(emp1);
        System.out.println("1. Luu Nhan vien 1 thanh cong voi email: " + duplicateEmail);

        // 2. Cố ý tạo Employee thứ hai trùng email và gọi save()
        Employee emp2 = new Employee();
        emp2.setFullName("Nguyen Van Copy 2");
        emp2.setEmail(duplicateEmail); // Trùng email với emp1
        emp2.setSalary(new BigDecimal("12000000.00"));
        emp2.setGender(Gender.FEMALE);
        emp2.setHireDate(LocalDate.now());
        emp2.setActive(true);

        System.out.println("\n2. Co y luu Nhan vien 2 voi EMAIL TRUNG...");

        try {
            dao.save(emp2);
            System.out.println("-> THAT BAI: Database khong ngan chan duoc email trung!");
        } catch (PersistenceException ex) {
            System.out.println("-> PASSED: Da bat duoc PersistenceException / ConstraintViolationException theo dung ky vong!");
            System.out.println("   Chi tiet loi tu Database: " + ex.getCause().getMessage());
        } catch (Exception ex) {
            System.out.println("-> PASSED: Da bat duoc Exception: " + ex.getMessage());
        } finally {
            // 3. Dọn dẹp bản ghi thử nghiệm sau khi test xong
            if (emp1.getId() != null) {
                dao.delete(emp1.getId());
                System.out.println("\n3. Da don dep du lieu test (Xoa ID " + emp1.getId() + ")");
            }
        }
    }
}