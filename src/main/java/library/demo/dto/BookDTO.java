package library.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookDTO {
    public BookDTO() {}

    @NotBlank(message = "Tytul wymagany")
    private String title;

    private String author;
    private String isbn;

}
