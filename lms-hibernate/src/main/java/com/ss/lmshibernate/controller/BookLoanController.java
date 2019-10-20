package com.ss.lmshibernate.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lmshibernate.entity.Author;
import com.ss.lmshibernate.entity.Book;
import com.ss.lmshibernate.entity.BookLoan;
import com.ss.lmshibernate.entity.Publisher;
import com.ss.lmshibernate.exception.ResourceNotFoundException;
import com.ss.lmshibernate.repository.AuthorRepository;
import com.ss.lmshibernate.repository.BookLoanRepository;
import com.ss.lmshibernate.repository.BookRepository;
import com.ss.lmshibernate.repository.BorrowerRepository;
import com.ss.lmshibernate.repository.LibraryBranchRepository;
import com.ss.lmshibernate.repository.PublisherRepository;

@RestController
@RequestMapping(value = "/lms/admin*")
public class BookLoanController {

	@Autowired
	BookLoanRepository bookLoanRepository;
	@Autowired
	BookRepository bookRepository;

	@Autowired
	BorrowerRepository borrowerRepository;

	@Autowired
	LibraryBranchRepository libraryBranchRepository;

	@PutMapping("/duedate/{bookId}/{branchId}/{cardNo}")
	public void updateBookLoan(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "cardNo") Long cardNo,
			@PathVariable(value = "branchId") Long branchId, @Valid @RequestBody BookLoan bookLoanRequest) {

		if (!bookRepository.existsById(bookId) && !borrowerRepository.existsById(cardNo)
				&& !libraryBranchRepository.existsById(branchId)) {
			throw new ResourceNotFoundException("CardNo  or branchId or bookId  not found");
		}
		BookLoan bookLoan = new BookLoan();
		bookLoan.setBookId(bookId);
		bookLoan.setBranchId(branchId);
		bookLoan.setCardNo(cardNo);
		bookLoan.setDateOut(bookLoanRequest.getDateOut());
		bookLoan.setDueDate(bookLoanRequest.getDueDate());
		bookLoanRepository.save(bookLoan);

//	        return bookLoanRepository.findByIds(bookId, branchId, cardNo).map(bookLoan -> {
//	        	bookLoan.setDueDate(bookLoanRequest.getDueDate());
//	            return bookLoanRepository.save(bookLoan);
//	        }).orElseThrow(() -> new ResourceNotFoundException("BookId or branchId or cardNo not found"));
	}

}
