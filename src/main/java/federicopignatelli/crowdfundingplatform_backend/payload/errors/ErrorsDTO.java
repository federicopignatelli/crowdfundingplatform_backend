package federicopignatelli.crowdfundingplatform_backend.payload.errors;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
