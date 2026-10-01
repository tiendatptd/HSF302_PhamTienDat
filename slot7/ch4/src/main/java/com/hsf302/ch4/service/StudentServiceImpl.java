package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Department;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.DepartmentRepository;
import com.hsf302.ch4.repository.StudentRepository;
import com.hsf302.ch4.specification.StudentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)          // mặc định: mọi method chỉ ĐỌC
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phải >= 0 và size phải > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    // ===== Part C =====
    @Override
    public Optional<Student> findByStudentCode(String studentCode) {
        return studentRepository.findByStudentCode(studentCode);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    @Override
    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();                              // từ khoá rỗng → không tìm
        }
        return studentRepository.findByFullNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        String suffix = domain.startsWith("@") ? domain : "@" + domain;   // Tự thêm @ nếu thiếu
        return studentRepository.findByEmailEndingWith(suffix);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }
    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min GPA phải <= max GPA");
        }
        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender gender) {
        return studentRepository.findByGenderAndActiveTrue(gender);
    }

    @Override
    public List<Student> findBornAfter(LocalDate date) {
        return studentRepository.findByDobAfter(date);
    }
    @Override
    public List<Student> findByDepartment(String deptCode) {
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode);
    }

    @Override
    public long countByDepartment(String deptCode) {
        return studentRepository.countByDepartment_Code(deptCode);
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    @Override
    public List<Student> findGoodStudents(String deptCode, double minGpa) {
        return studentRepository.findGoodStudentsInDepartment(deptCode, minGpa);
    }

    @Override
    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    @Override
    public List<Student> findTopNInDepartment(String deptCode, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n phải > 0");
        }
        return studentRepository.findTopNByDepartmentNative(deptCode, n);
    }

    @Override
    public List<StudentSummary> getActiveSummaries() {
        return studentRepository.findActiveSummaries();
    }

    @Override
    public Page<Student> findActiveByDepartment(String deptCode, int pageIndex, int size) {
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by("gpa").descending());
        return studentRepository.findActiveByDepartment(deptCode, pageable);
    }

    @Override
    @Transactional
    public Student create(Student student, String deptCode) {
        if (studentRepository.existsByStudentCode(student.getStudentCode())) {
            throw new IllegalArgumentException("Mã sinh viên đã tồn tại: " + student.getStudentCode());
        }
        if (student.getEmail() != null && !student.getEmail().isBlank()
                && studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại: " + student.getEmail());
        }
        Department dept = departmentRepository.findByCode(deptCode)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa: " + deptCode));

        dept.addStudent(student);   // Gán quan hệ 2 chiều
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student update(Long id, Student updatedInfo) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với ID: " + id));

        // Kiểm tra email mới nếu có thay đổi và khác rỗng
        if (updatedInfo.getEmail() != null && !updatedInfo.getEmail().isBlank()
                && !updatedInfo.getEmail().equals(existing.getEmail())) {
            if (studentRepository.existsByEmail(updatedInfo.getEmail())) {
                throw new IllegalArgumentException("Email đã tồn tại: " + updatedInfo.getEmail());
            }
            existing.setEmail(updatedInfo.getEmail());
        }

        existing.setFullName(updatedInfo.getFullName());
        existing.setGender(updatedInfo.getGender());
        existing.setDob(updatedInfo.getDob());
        existing.setGpa(updatedInfo.getGpa());
        existing.setActive(updatedInfo.isActive());

        // Không cần gọi studentRepository.save() tường minh vì entity đang ở trạng thái managed (dirty checking)
        return existing;
    }

    @Override
    @Transactional
    public Student changeDepartment(Long studentId, String newDeptCode) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với ID: " + studentId));

        Department newDept = departmentRepository.findByCode(newDeptCode)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy khoa mới: " + newDeptCode));

        // Nếu sinh viên đã thuộc khoa mới này rồi thì không cần làm gì thêm
        if (student.getDepartment() != null && student.getDepartment().getCode().equals(newDeptCode)) {
            return student;
        }

        // Xóa sinh viên khỏi danh sách khoa cũ (nếu có)
        if (student.getDepartment() != null) {
            student.getDepartment().getStudents().remove(student);
        }

        // Thêm sinh viên vào khoa mới thông qua hàm tiện ích 2 chiều
        newDept.addStudent(student);

        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sinh viên với ID: " + id));

        // Gỡ bỏ quan hệ 2 chiều với Department trước khi xóa
        if (student.getDepartment() != null) {
            student.getDepartment().getStudents().remove(student);
            student.setDepartment(null);
        }

        studentRepository.delete(student);
    }

    @Override
    public List<Student> searchDynamic(String keyword, String deptCode, Double minGpa, Boolean active) {
        Specification<Student> spec = Specification.where(StudentSpecifications.hasKeyword(keyword))
                .and(StudentSpecifications.hasDepartmentCode(deptCode))
                .and(StudentSpecifications.gpaGreaterThanOrEqual(minGpa))
                .and(StudentSpecifications.isActive(active));

        return studentRepository.findAll(spec);
    }
}