package library.demo.repository;


import library.demo.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);

    List<Loan> findByReturnDateIsNull();

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}
