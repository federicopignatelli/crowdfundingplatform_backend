package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

import java.util.UUID;

public record NewCampaignUpdateResponseDTO(UUID campaignId, String title, String subtitle, String Category, String description) {
}
