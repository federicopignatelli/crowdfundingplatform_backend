package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record NewCampaignUPDATEDTO(

        String title,
        String subtitle,
        String category,
        String description,
        String campaignCover
) {
}
