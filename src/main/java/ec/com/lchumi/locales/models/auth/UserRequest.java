package ec.com.lchumi.locales.models.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
