package com.example.library;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    @NotBlank(message = "Title is required!")
    private String title;

    @NotBlank(message = "Author is required!")
    private String author;
    private String isbn;
    private boolean isAvailable = true;
}
