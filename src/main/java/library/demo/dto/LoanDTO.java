package library.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;
    @NotNull
    private LocalDate loanDate;
    private LocalDate returnDate;
}
