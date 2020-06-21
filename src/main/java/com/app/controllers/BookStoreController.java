package com.app.controllers;

import com.app.exceptions.BookNotFoundException;
import com.app.models.Book;
import com.app.services.IBookService;
import com.app.services.IMediaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookStoreController {
    private IMediaService mediaService;
    private IBookService bookService;

    public BookStoreController(IMediaService mediaService, IBookService bookService) {
        this.mediaService = mediaService;
        this.bookService = bookService;
    }

    /**
     * filtering data using title and author as partial or full matched
     * @param author to search by author
     * @param title to search by title
     * @param pageNumber
     * @param pageSize
     * @return list of books
     */
    @GetMapping
    public Page<Book> getBooks(@RequestParam(value = "filter[author]", defaultValue = "", required = false) String author,
                               @RequestParam(value = "filter[title]", defaultValue = "", required = false) String title,
                               @RequestParam(value = "page[number]", defaultValue = "0", required = false) Integer pageNumber,
                               @RequestParam(value = "page[size]", defaultValue = "10", required = false) Integer pageSize) {
        return bookService.getBooks(author,title,pageNumber,pageSize);
    }

    /**
     * Saving a Book
     * @param book Book Object with mandatory fields isbn,title,quantity,author
     * @return saved book
     */
    @PostMapping
    public Book addBook(@Valid @RequestBody Book book) {
        return bookService.addBook(book);
    }

    /**
     * Search Book with given ISBN
     * @param isbn
     * @return a book with required isbn
     */
    @GetMapping("{isbn}")
    public Book getBook(@PathVariable Long isbn) {
        return bookService.getBook(isbn);
    }

    /**
     * Search a post for given isbn
     * @param isbn
     * @return posts with title containing given isbn
     * @throws JsonProcessingException
     */
    @GetMapping("{isbn}/posts")
    public List<String> fetchPosts(@PathVariable Long isbn) throws JsonProcessingException {
        Book book = bookService.getBook(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return mediaService.fetchPosts(isbn, book.getTitle());
    }

    /**
     * remove a single book
     * @param isbn
     * @return a single book which is purchased
     */
    @GetMapping("{isbn}/buy")
    public Book buyBook(@PathVariable Long isbn) {
        Book book = bookService.getBook(isbn);
        if (book == null) {
            throw new BookNotFoundException();
        }
        return bookService.buyBook(book);
    }
}
