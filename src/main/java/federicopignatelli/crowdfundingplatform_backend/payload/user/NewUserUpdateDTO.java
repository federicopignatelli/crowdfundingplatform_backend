package federicopignatelli.crowdfundingplatform_backend.payload.user;

public record NewUserUpdateDTO(
        String name,
        String surname,
        String email,
        String password,
        String Country,
        String city,
        String bio,
        String profilepic
) {
}
