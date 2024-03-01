package federicopignatelli.crowdfundingplatform_backend.payload.campaign;

import federicopignatelli.crowdfundingplatform_backend.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record NewCampaignDTO(
        @NotEmpty(message = "titolo obbligatorio")
        String title,
        @NotEmpty(message = "sottotitolo obbligatorio")
        String subtitle,
        @NotEmpty(message = "categoria obbligatoria")
        String category,
        String description,
        @NotNull(message = "foto obbligatoria")
        Integer fundsTarget
) {
}
