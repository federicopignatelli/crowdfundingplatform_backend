package federicopignatelli.crowdfundingplatform_backend.payload.user;

import java.util.Date;
import java.util.List;

public record ErrorsResponseWithListDTO(String message,
                                        Date timestamp,
                                        List<String> errorsList) {
}
