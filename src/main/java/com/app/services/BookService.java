package com.app.services;

import com.app.exceptions.BookNotFoundException;
import com.app.models.Book;
import com.app.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<Book> getBooks(String author, String title, Integer pageNumber, Integer pageSize){
        return bookRepository.findByAuthorIgnoreCaseContainingAndTitleIgnoreCaseContaining(author, title, PageRequest.of(pageNumber, pageSize));

    }

    @Override
    public Book addBook(Book book) {
        Book foundBook = bookRepository.findByIsbn(book.getIsbn());
        if (foundBook != null) {
            book.setQuantity(foundBook.getQuantity() + book.getQuantity());
        }
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return book;
    }

    @Override
    public Book buyBook(Long isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }
        if (book.getQuantity() > 1) {
            book.setQuantity(book.getQuantity() - 1);
        }
        Book updatedBook = bookRepository.save(book);
        updatedBook.setQuantity(1);
        return updatedBook;
    }
}
