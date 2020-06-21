package com.app.services;

import com.app.exceptions.BookNotFoundException;
import com.app.models.Book;
import com.app.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {
    BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Book> getBooks(String author, String title, Integer pageNumber, Integer pageSize){
        return repository.findByAuthorIgnoreCaseContainingAndTitleIgnoreCaseContaining(author, title, PageRequest.of(pageNumber, pageSize));

    }

    @Override
    public Book addBook(Book book) {
        Book foundBook = repository.findByIsbn(book.getIsbn());
        if (foundBook != null) {
            book.setQuantity(foundBook.getQuantity() + book.getQuantity());
        }
        return repository.save(book);
    }

    @Override
    public Book getBook(Long isbn) {
        Book book = repository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public Book buyBook(Book book) {
        if (book.getQuantity() > 1) {
            book.setQuantity(book.getQuantity() - 1);
        }
        Book updatedBook = repository.save(book);
        updatedBook.setQuantity(1);
        return updatedBook;
    }
}
