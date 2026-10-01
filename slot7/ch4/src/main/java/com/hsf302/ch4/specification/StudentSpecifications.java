package com.hsf302.ch4.specification;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {

    public static Specification<Student> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return cb.conjunction();
            String likePattern = "%" + keyword.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("fullName")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern)
            );
        };
    }

    public static Specification<Student> hasDepartmentCode(String deptCode) {
        return (root, query, cb) -> {
            if (deptCode == null || deptCode.isBlank()) return cb.conjunction();
            return cb.equal(root.get("department").get("code"), deptCode.trim());
        };
    }

    public static Specification<Student> gpaGreaterThanOrEqual(Double minGpa) {
        return (root, query, cb) -> {
            if (minGpa == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("gpa"), minGpa);
        };
    }

    public static Specification<Student> isActive(Boolean active) {
        return (root, query, cb) -> {
            if (active == null) return cb.conjunction();
            return cb.equal(root.get("active"), active);
        };
    }
}