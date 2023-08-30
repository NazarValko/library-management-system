package com.nazarvalko.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "book")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "isbn")
    private long ISBN;

    @Column(name = "available")
    private boolean available;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Review> reviews;


    public Book(int id, String title, Author author, Category category, Date publishedDate, long ISBN, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publishedDate = publishedDate;
        this.ISBN = ISBN;
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && ISBN == book.ISBN && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(category, book.category) && Objects.equals(publishedDate, book.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, category, publishedDate, ISBN);
    }
}
