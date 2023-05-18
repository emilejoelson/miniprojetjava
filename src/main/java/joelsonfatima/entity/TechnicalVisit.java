package joelsonfatima.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "technical_visit")
public class TechnicalVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;
    @OneToOne
    @JoinColumn(name = "attestation_id")
    private AttestationConformity attestation;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle_technical_visit;
    public static final int ATT_MIN_AGE_BY_YEARS = 5;
}
