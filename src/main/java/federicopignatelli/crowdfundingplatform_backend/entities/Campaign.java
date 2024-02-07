package federicopignatelli.crowdfundingplatform_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "campaigns")
public class Campaign {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String subtitle;
    private String category;
    private String description;
    private LocalDate startDate = LocalDate.now();
    private String campaignCover;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User user_id;
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Contribution> contributionList;
}
