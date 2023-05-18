package joelsonfatima.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "attestation_conformity")
public class AttestationConformity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "description")
    private String description;
    @OneToOne(mappedBy = "attestation", optional = false)
    private TechnicalVisit technicalVisit;
}
