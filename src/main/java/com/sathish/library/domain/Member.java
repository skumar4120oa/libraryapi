package com.sathish.library.domain;

import java.util.Set;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Member")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Member {

    private long memberid;

    private String name;

    private String information;

    private Set<Book> books;

    public Member() {
    }

    public Member(String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id
    @GeneratedValue()
    public long getMemberid() {
        return this.memberid;
    }

    // for tests ONLY
    public void setMemberid(long memberid) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Member {" +
                "memberid=" + memberid +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                '}';
    }
}
