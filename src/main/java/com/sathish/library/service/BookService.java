package com.sathish.library.service;

import com.sathish.library.domain.Book;
import com.sathish.library.dao.jpa.BookRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    CounterService counterService;

    public BookService() {
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(long id) {
        return bookRepository.findOne(id);
    }

    public void updateBook(Book book) {
    	bookRepository.save(book);
    }

    public void deleteBook(Long id) {
    	bookRepository.delete(id);
    }

    public Page<Book> getAllBooks(Integer page, Integer size) {
		Page pageOfBooks  = bookRepository.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("sathish.bookService.getAll.largePayload");
        }
        return pageOfBooks;
    }
}
