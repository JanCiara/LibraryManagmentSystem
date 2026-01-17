package library.demo.controller;

import jakarta.validation.Valid;
import library.demo.dto.BookDTO;
import library.demo.dto.LoanDTO;
import library.demo.dto.UserDTO;
import library.demo.dto.UserResponseDTO;
import library.demo.model.Book;
import library.demo.model.Loan;
import library.demo.model.User;
import library.demo.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // endpoints

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/users")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = libraryService.getAllUsers();

        return users.stream().map(user -> {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());

            if (user.getLoans() != null) {
                List<Long> bookIds = user.getLoans().stream()
                        .map(loan -> loan.getBook().getId())
                        .toList();
                dto.setBorrowedBookIds(bookIds);
            }

            return dto;
        }).toList();
    }

    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody BookDTO bookDto) {
        Book bookEntity = new Book();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setIsbn(bookDto.getIsbn());
        return libraryService.addBook(bookEntity);
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody UserDTO userDto) {
        User userEntity = new User();
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        return libraryService.addUser(userEntity);
    }

    @PostMapping("/loans")
    public Loan borrowBook(@Valid @RequestBody LoanDTO loanDto) {
        return libraryService.borrowBook(loanDto.getUserId(), loanDto.getBookId());
    }

    @PutMapping("/loans/{loanId}/return")
    public void returnBook(@PathVariable Long loanId) {
        libraryService.returnBook(loanId);
    }

}
