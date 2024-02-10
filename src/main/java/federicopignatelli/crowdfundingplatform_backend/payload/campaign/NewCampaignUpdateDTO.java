package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

public record NewCampaignUpdateDTO(

        String title,
        String subtitle,
        String category,
        String description,
        String campaignCover
) {
}
