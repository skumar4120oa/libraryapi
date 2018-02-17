package com.sathish.library.dao.jpa;

import com.sathish.library.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source
 */
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Page findAll(Pageable pageable);
}
