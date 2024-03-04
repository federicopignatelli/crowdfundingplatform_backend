package federicopignatelli.crowdfundingplatform_backend.payload.Contribution;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewContributionDTO(
        @NotEmpty(message = "Somma obbligatoria")
        Integer amount
) {
}
