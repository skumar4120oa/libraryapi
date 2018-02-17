package com.sathish.library.api.rest;

import com.sathish.library.domain.Book;
import com.sathish.library.exception.DataFormatException;
import com.sathish.library.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/library/books")
public class BookController extends AbstractEventPublisher {

    @Autowired
    private BookService bookService;

    
    //As a user,I should be able to add a book
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody Book book,
                                 HttpServletRequest request, HttpServletResponse response) {
        Book createdBook = this.bookService.createBook(book);
        response.setHeader("Book", request.getRequestURL().append("/").append(createdBook.getBookId()).toString());
    }
    
    //As a user, I should be able to see a list of the books in the library
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Page<Book> getAllBook(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
    		return this.bookService.getAllBooks(page, size);
    }

    //As a user, I should be able to search for a book by id
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Book getBook(@PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Book book = this.bookService.getBook(id);
        checkResourceFound(book);
        return book;
    }

    //I should be able to update that a member has checked out a book on the date
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBook(@PathVariable("id") Long id, @RequestBody Book book,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.bookService.getBook(id));
        if (id != book.getBookId()) throw new DataFormatException("ID doesn't match!");
        this.bookService.updateBook(book);
    }

    //I should be able to remove books from the system.
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.bookService.getBook(id));
        this.bookService.deleteBook(id);
    }
    
    //TODO
    /* I should be able to mark that a member has checked out a book
     * As a user, I should be able to enter and access information about a book’s title
	 *and summary
	 *As a user, I should be able to filter books based on whether or not it is checked
	 *out or overdue
     *I should be able to see when a book becomes overdue. Books are overdue after
     *3 weeks of being checked out*/
}
