package fu.de180120.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="fullName", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String fullName;

    @Column(name = "email", columnDefinition = "NVARCHAR(50)",nullable = false ,unique = true)
    private String email;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate hireDate;

    private boolean active;

    @Transient
    private int yearsOfService;

    public int getYearsOfService() {
        if (this.hireDate != null) {
            return Period.between(this.hireDate, LocalDate.now()).getYears();
        }
        return 0;
    }

    public Employee(String fullName, String email, BigDecimal salary, Gender gender, LocalDate hireDate, boolean active) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.gender = gender;
        this.hireDate = hireDate;
        this.active = active;
    }
}