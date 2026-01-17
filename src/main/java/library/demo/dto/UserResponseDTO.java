package library.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    private List<Long> borrowedBookIds;
}