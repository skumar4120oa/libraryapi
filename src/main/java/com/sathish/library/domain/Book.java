package com.sathish.library.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Book")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {


    private long book_id;

    private String name;

    private String description;

    private String checkout_date;
    
    Member user = new Member();

    public Book() {
    }

    public Book(String name, String description, Member user, String checkout_date) {
        this.name = name;
        this.description = description;
        this.user = user;
        this.checkout_date = checkout_date;
    }

    @Id
    @GeneratedValue()
    public long getBookId() {
        return this.book_id;
    }

    public void setBookId(long bookId) {
        this.book_id = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckoutdate() {
        return checkout_date;
    }

    public void setCheckoutdate(String checkout_date) {
        this.checkout_date = checkout_date;
    }
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="memberid")
    @JsonBackReference
    public Member getUser() {
        return this.user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Book {" +
                "id=" + book_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user='" + user + '\'' +
                ", checkout_date=" + checkout_date +
                '}';
    }
}
