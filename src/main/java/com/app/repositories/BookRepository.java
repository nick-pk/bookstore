package com.app.repositories;

import com.app.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Book findByIsbn(Long isbn);
    Page<Book> findByAuthorIgnoreCaseContainingAndTitleIgnoreCaseContaining(String author, String title, Pageable pageable);
}
