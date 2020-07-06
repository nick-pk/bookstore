package com.app.services;

import com.app.models.Book;
import org.springframework.data.domain.Page;

public interface IBookService {
    public Page<Book> getBooks(String author, String title, Integer pageNumber, Integer pageSize);
    public Book addBook(Book book);
    public Book getBook(Long isbn);
    public Book buyBook(Long isbn);
}
