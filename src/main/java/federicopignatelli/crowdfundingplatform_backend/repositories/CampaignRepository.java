package federicopignatelli.crowdfundingplatform_backend.repositories;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}
