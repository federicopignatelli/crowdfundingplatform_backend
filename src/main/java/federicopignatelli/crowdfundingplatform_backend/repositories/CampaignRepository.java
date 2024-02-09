package federicopignatelli.crowdfundingplatform_backend.repositories;

import federicopignatelli.crowdfundingplatform_backend.entities.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
    @Query("SELECT c FROM Campaign c WHERE c.userId = :userId")
    List<Campaign> findByUserId(UUID userId);
}
