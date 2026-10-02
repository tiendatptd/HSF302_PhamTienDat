package com.hsf302.ch4.specification;

import com.hsf302.ch4.pojo.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecs {

    // Lọc theo tên (chứa từ khóa, không phân biệt hoa thường)
    public static Specification<Course> hasNameContaining(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.isBlank()) return builder.conjunction(); // Bỏ qua nếu null
            return builder.like(builder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
        };
    }

    // Lọc theo tín chỉ tối thiểu (>= minCredits)
    public static Specification<Course> hasMinCredits(Integer minCredits) {
        return (root, query, builder) -> {
            if (minCredits == null) return builder.conjunction();
            return builder.greaterThanOrEqualTo(root.get("credits"), minCredits);
        };
    }

    // Lọc theo học kỳ (chính xác)
    public static Specification<Course> hasSemester(String semester) {
        return (root, query, builder) -> {
            if (semester == null || semester.isBlank()) return builder.conjunction();
            return builder.equal(root.get("semester"), semester);
        };
    }
}