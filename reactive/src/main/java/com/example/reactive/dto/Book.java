package com.example.reactive.dto;

import lombok.Data;

@Data
public class Book {
    private long id;
    private String name;

    public Book(String name) {
        this.name = name;
    }
}
