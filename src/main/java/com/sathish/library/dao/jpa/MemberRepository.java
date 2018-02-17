package com.sathish.library.dao.jpa;

import com.sathish.library.domain.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations against the data source
 */
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
    Page findAll(Pageable pageable);
}
