package federicopignatelli.crowdfundingplatform_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "contributions")
public class Contribution {
    @Id
    @GeneratedValue
    private UUID contributionId;
    private int amount;
    private LocalDate emissionDate = LocalDate.now();
    private LocalTime emissionTime;
    @ManyToOne
    @JoinColumn(name = "contributor")
    private User userId;
    @ManyToOne
    @JoinColumn(name = "campaign")
    private Campaign campaign;
}
