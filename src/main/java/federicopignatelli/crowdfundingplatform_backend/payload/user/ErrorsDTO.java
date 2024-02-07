package federicopignatelli.crowdfundingplatform_backend.payload.user;

import java.time.LocalDateTime;

public record ErrorsDTO(String message, LocalDateTime timestamp) {
}
