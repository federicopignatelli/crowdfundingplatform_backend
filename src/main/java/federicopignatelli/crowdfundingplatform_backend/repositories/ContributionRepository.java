package federicopignatelli.crowdfundingplatform_backend.repositories;

import federicopignatelli.crowdfundingplatform_backend.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContributionRepository extends JpaRepository<Contribution, UUID>{
}

