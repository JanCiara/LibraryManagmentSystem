package library.demo.service;

import library.demo.dto.BookDTO;
import library.demo.model.Book;
import library.demo.model.Loan;
import library.demo.model.User;
import library.demo.repository.BookRepository;
import library.demo.repository.LoanRepository;
import library.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public LibraryService(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Loan borrowBook(Long userId, Long bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika o ID: " + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono książki o ID: " + bookId));

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());

        return loanRepository.save(loan);
    }

}
