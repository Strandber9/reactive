package com.example.reactive.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.reactive.dto.Book;

@RestController
@RequestMapping("/v1/books")
public class SpringMvcBranchOffice {
    private final Map<Long, Book> bookMap = Map.ofEntries(
            Map.entry(1L, new Book("book-1")),
            Map.entry(2L, new Book("book-2")),
            Map.entry(3L, new Book("book-3")),
            Map.entry(4L, new Book("book-4")),
            Map.entry(5L, new Book("book-5")));

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{book-id}")
    public ResponseEntity<Book> getBook(@PathVariable("book-id") long bookId) throws InterruptedException {
        Thread.sleep(2000);

        Book book = bookMap.get(bookId);

        return ResponseEntity.ok(book);
    }
}
