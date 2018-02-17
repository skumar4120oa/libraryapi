package com.sathish.library.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sathish.library.Application;
import com.sathish.library.api.rest.BookController;
import com.sathish.library.domain.Book;
import com.sathish.library.domain.Member;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class BookControllerTest {

    @InjectMocks
    BookController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/library/books")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        Book r1 = mockBook("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mvc.perform(post("/library/books")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //DELETE
        mvc.perform(delete("/library/books/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/library/books/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


    }

    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
    	if (locationUrl == null) {
    		return 1l;
    	} else {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);    		
    	}

    }


    private Book mockBook(String prefix) {
        Book r = new Book();
        Set<Book> s = new HashSet<Book>();
        Member m = new Member();
        m.setName("SV");
        m.setInformation("Desc");
        m.setBooks(s);
        r.setDescription(prefix + "_description");
        r.setName(prefix + "_name");
        r.setCheckoutdate("12/01/2017");
        r.setUser(m);
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }


}
