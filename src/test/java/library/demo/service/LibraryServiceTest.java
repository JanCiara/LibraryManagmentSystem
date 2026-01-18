package library.demo.service;

import library.demo.model.Book;
import library.demo.model.Loan;
import library.demo.model.User;
import library.demo.repository.BookRepository;
import library.demo.repository.LoanRepository;
import library.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LibraryService libraryService;

    @Test
    void borrowBook_ShouldSucceed_WhenBookIsAvailable() {
        Long userId = 1L;
        Long bookId = 10L;
        User user = new User();
        user.setId(userId);
        Book book = new Book();
        book.setId(bookId);

        when(loanRepository.existsByBookIdAndReturnDateIsNull(bookId)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        libraryService.borrowBook(userId, bookId);

        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void borrowBook_ShouldThrowException_WhenBookIsAlreadyBorrowed() {
        Long userId = 1L;
        Long bookId = 10L;

        when(loanRepository.existsByBookIdAndReturnDateIsNull(bookId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            libraryService.borrowBook(userId, bookId);
        });

        verify(loanRepository, never()).save(any());
    }


    @Test
    void returnBook_ShouldSetReturnDate() {
        Long loanId = 123L;
        Loan loan = new Loan();
        assertNull(loan.getReturnDate());

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        libraryService.returnBook(loanId);

        assertNotNull(loan.getReturnDate());
        verify(loanRepository).save(loan);
    }
}