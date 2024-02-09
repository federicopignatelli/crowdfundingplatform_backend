package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

import federicopignatelli.crowdfundingplatform_backend.entities.User;

public record NewCampaignResponseDTO(String title, User user_id) {
}
