package federicopignatelli.crowdfundingplatform_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private UUID campaignId;
    private String title;
    private String subtitle;
    private String category;
    private String description;
    private LocalDate startDate = LocalDate.now();
    private String campaignCover;
    private Integer totalFunds;
    private Integer fundsTarget;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User userId;
    @JsonIgnore
    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private List<Contribution> contributionList;
}
