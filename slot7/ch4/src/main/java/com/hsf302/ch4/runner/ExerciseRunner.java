package com.hsf302.ch4.runner;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
@Order(2)
@Profile("ex1")
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    // Runner CHỈ phụ thuộc vào Service (interface), KHÔNG inject Repository
    private final DepartmentService departmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        partB();
        partC();
        partD();
        bonus();      // chạy trên dữ liệu gốc → trước Part E
        partE();
    }

    private void partB() {
        todo6();
         todo7();
    }
    private void partC() {
        todo8();
        todo9();
        todo10();
        todo11();
         }
    private void partD() {
        todo12();
        todo13();
        todo14();
        todo15();
        todo16();
        todo17();
        todo18();
        todo19();
         }
    private void partE() {
        todo20();
        todo21();
        todo22();
        todo23();
        }
    private void bonus() {
        todo24();
    }

    // ===== helpers =====
    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    // ===== TODO 6 =====
    private void todo6() {
        title("TODO 6: count / findById / existsById");
        System.out.println("Departments: " + departmentService.count());
        System.out.println("Students   : " + studentService.count());

        studentService.findById(1L).ifPresentOrElse(
                s -> System.out.println("findById(1)  -> " + s),
                () -> System.out.println("findById(1)  -> Not found"));

        System.out.println("findById(99) -> " + studentService.findById(99L)
                .map(Object::toString)
                .orElse("Not found"));

        System.out.println("existsById(4) department -> " + departmentService.existsById(4L));
    }
    private void todo7() {
        title("TODO 7: Sort & Pageable");

        // (a) GPA giảm dần
        printList("All students order by GPA desc", studentService.findAllOrderByGpaDesc());

        // (b) Trang THỨ 2 → index 1 (Spring Data đánh số trang từ 0)
        Page<Student> page = studentService.findPage(1, 3, "fullName");
        printList("Page index " + page.getNumber() + " (size " + page.getSize() + ")", page.getContent());
        System.out.println("totalElements=" + page.getTotalElements()
                + ", totalPages=" + page.getTotalPages()
                + ", hasNext=" + page.hasNext()
                + ", hasPrevious=" + page.hasPrevious());
    }

    // ===== TODO 8 =====
    private void todo8() {
        title("TODO 8: findBy / existsBy / countBy");
        for (String code : List.of("AI002", "XX999")) {
            System.out.println("findByStudentCode(" + code + ") -> " +
                    studentService.findByStudentCode(code).map(Object::toString).orElse("Not found"));
        }
        System.out.println("isEmailExisted(binh.tt@fpt.edu.vn) -> "
                + studentService.isEmailExisted("binh.tt@fpt.edu.vn"));
        System.out.println("countActive -> " + studentService.countActive());
    }
    // ===== TODO 9 =====
    private void todo9() {
        title("TODO 9: Containing / EndingWith / IsNull");
        printList("fullName contains 'nguyen'", studentService.searchByName("nguyen"));
        printList("email domain 'gmail.com'", studentService.findByEmailDomain("gmail.com"));
        printList("email is null", studentService.findWithoutEmail());
    }
    // ===== TODO 10 =====
    private void todo10() {
        title("TODO 10: Between / And / True / After");
        printList("GPA in [3.0, 3.6] desc", studentService.findByGpaRange(3.0, 3.6));
        printList("MALE & active", studentService.findActiveByGender(Gender.MALE));
        printList("dob after 2005-01-01", studentService.findBornAfter(LocalDate.of(2005, 1, 1)));
    }
    // ===== TODO 11 =====
    private void todo11() {
        title("TODO 11: Nested property / Top / IsEmpty");
        printList("Students of SE (order by name)", studentService.findByDepartment("SE"));
        System.out.println("count students of AI -> " + studentService.countByDepartment("AI"));
        printList("Top 3 GPA", studentService.findTop3ByGpa());
        printList("Departments without students", departmentService.findDepartmentsWithoutStudents());
    }

    // ===== TODO 12 =====
    private void todo12() {
        title("TODO 12: JPQL + named parameter");
        printList("SE, GPA >= 3.0", studentService.findGoodStudents("SE", 3.0));
    }

    // ===== TODO 13 =====
    private void todo13() {
        title("TODO 13: JPQL LIKE");
        printList("keyword 'hoa'", studentService.searchByKeyword("hoa"));
        printList("keyword 'gmail'", studentService.searchByKeyword("gmail"));
    }

    // ===== TODO 14 =====
    private void todo14() {
        title("TODO 14: Statistics by department (DTO)");
        printList("code | name | total | avgGpa", departmentService.getStatistics());
    }

    // ===== TODO 15 =====
    private void todo15() {
        title("TODO 15: Subquery - GPA above average");
        printList("GPA > AVG", studentService.findAboveAverageGpa());
    }

    // ===== TODO 16 =====
    private void todo16() {
        title("TODO 16: LazyInitializationException & JOIN FETCH");

        Department ai = departmentService.findByCode("AI").orElseThrow();
        try {
            System.out.println("AI has " + ai.getStudents().size() + " students");
        } catch (LazyInitializationException e) {
            System.out.println("(a) Caught: " + e.getClass().getSimpleName());
            System.out.println("    " + e.getMessage());
        }

        Department aiFull = departmentService.getWithStudents("AI");
        System.out.println("(b) " + aiFull);
        aiFull.getStudents().forEach(s -> System.out.println("     " + s));
    }

    // ===== TODO 17 =====
    private void todo17() {
        title("TODO 17: Native query - TOP N");
        printList("Top 2 GPA of SE", studentService.findTopNInDepartment("SE", 2));
    }

    // ===== TODO 18 =====
    private void todo18() {
        title("TODO 18: Interface projection");
        List<StudentSummary> list = studentService.getActiveSummaries();
        list.forEach(p -> System.out.printf("   %s | %-15s | %.1f | %s%n",
                p.getStudentCode(), p.getFullName(), p.getGpa(), p.getDepartmentName()));
        System.out.println("   -> " + list.size() + " record(s)");
    }

    // ===== TODO 19 =====
    private void todo19() {
        title("TODO 19: @Query + Pageable");
        for (int i = 0; i < 2; i++) {
            Page<Student> page = studentService.findActiveByDepartment("SE", i, 2);
            printList("SE active - page " + page.getNumber(), page.getContent());
            System.out.println("   totalElements=" + page.getTotalElements()
                    + ", totalPages=" + page.getTotalPages());
        }
    }

    // ===== TODO 20 =====
    private void todo20() {
        title("TODO 20: Create new student");
        Student newSt = new Student();
        newSt.setStudentCode("SE005");
        newSt.setFullName("Hoang Ngoc Ha");
        newSt.setEmail("ha.hn@fpt.edu.vn");
        newSt.setGender(Gender.FEMALE);
        newSt.setDob(LocalDate.of(2005, 12, 25));
        newSt.setGpa(3.7);
        newSt.setActive(true);

        Student saved = studentService.create(newSt, "SE");
        System.out.println("Created successfully -> " + saved);
    }

    // ===== TODO 21 =====
    private void todo21() {
        title("TODO 21: Update student");
        // Lấy sinh viên ID = 1 (SE001 - Nguyen Van An) để cập nhật GPA và Email
        Student info = new Student();
        info.setFullName("Nguyen Van An (Updated)");
        info.setEmail("an.nv.updated@fpt.edu.vn");
        info.setGender(Gender.MALE);
        info.setDob(LocalDate.of(2005, 3, 15));
        info.setGpa(3.9);
        info.setActive(true);

        Student updated = studentService.update(1L, info);
        System.out.println("Updated successfully -> " + updated);
    }

    // ===== TODO 22 =====
    private void todo22() {
        title("TODO 22: Change student department");
        // Chuyển sinh viên ID = 2 (Tran Thi Binh) từ khoa SE sang khoa AI
        Student changed = studentService.changeDepartment(2L, "AI");
        System.out.println("Changed department successfully -> " + changed);
    }

    // ===== TODO 23 =====
    private void todo23() {
        title("TODO 23: Delete student & check stats");
        // Xóa sinh viên ID = 3 (Le Van Cuong)
        studentService.delete(3L);
        System.out.println("Deleted student ID = 3 successfully");

        // Kiểm tra lại thống kê khoa sau khi xóa
        printList("Updated department statistics", departmentService.getStatistics());
    }

    // ===== TODO 24 (Bonus) =====
    private void todo24() {
        title("TODO 24: Dynamic Search with Specification");
        // Lọc sinh viên: thuộc khoa SE, GPA >= 3.0, đang active
        printList("Dynamic filter (SE, GPA>=3.0, active=true)",
                studentService.searchDynamic(null, "SE", 3.0, true));

        // Lọc sinh viên có chứa từ khóa 'an' hoặc 'binh'
        printList("Dynamic filter (Keyword 'an')",
                studentService.searchDynamic("an", null, null, null));
    }
}