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

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(precision = 10, scale = 2)
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
}