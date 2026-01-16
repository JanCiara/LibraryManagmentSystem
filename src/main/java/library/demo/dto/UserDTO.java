package library.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email wymagany")
    @Email(message = "Email niepoprawny")
    private String email;

    @NotBlank(message = "Haslo wymagane")
    @Size(min = 6, message = "Haslo musi miec min. 6 znakow")
    private String password;

    public UserDTO() {}

}
