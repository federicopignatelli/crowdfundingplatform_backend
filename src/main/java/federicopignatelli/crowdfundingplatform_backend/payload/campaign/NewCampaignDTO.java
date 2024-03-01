package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;



public record NewCampaignDTO(
        @NotEmpty(message = "titolo obbligatorio")
        String title,
        @NotEmpty(message = "sottotitolo obbligatorio")
        String subtitle,
        @NotEmpty(message = "categoria obbligatoria")
        String category,
        String description,
        @NotNull(message = "target found obbligatorio")
        Integer fundsTarget
) {
}
