package com.hellokoding.jpa;

import com.hellokoding.jpa.model.Book;
import com.hellokoding.jpa.model.Publisher;
import com.hellokoding.jpa.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.HashSet;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        // save a couple of books
        Publisher publisherA = new Publisher("Publisher A");
        Publisher publisherB = new Publisher("Publisher B");
        Publisher publisherC = new Publisher("Publisher C");

        bookRepository.save(new HashSet<Book>(){{
            add(new Book("Book A", new HashSet<Publisher>(){{
                add(publisherA);
                add(publisherB);
            }}));

            add(new Book("Book B", new HashSet<Publisher>(){{
                add(publisherA);
                add(publisherC);
            }}));
        }});

        // fetch all books
        for(Book book : bookRepository.findAll()) {
            logger.info(book.toString());
        }
    }
}
