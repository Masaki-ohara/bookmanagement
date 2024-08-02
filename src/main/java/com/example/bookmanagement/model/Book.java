// package com.example.bookmanagement.model;

// import java.time.LocalDate;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
// import org.hibernate.annotations.SQLRestriction;

// // ↑↑↑↑
// // 同じSQLRestrictionでもimport元が違うものがあるので注意
// // import org.hibernate.annotations.DialectOverride.SQLRestriction;

// @Entity
// @SQLRestriction("is_deleted = false")
// @Table(name = "books")
// public class Book {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(nullable = false)
//     private String title;

//     @Column(nullable = false)
//     private String author;

//     @Column(nullable = false, unique = true)
//     private String isbn;

//     @Column(name = "is_deleted", nullable = false)
//     private Boolean isDeleted = false;

//     private LocalDate publishedDate;
//     // @ManyToOne

//     // 書籍側から見たら多対1(Many : One)でした。そのままの意味のアノテーションです。
    
//     // このアノテーションを定義する事で「書籍たちはそれぞれ1つのジャンルを持っていますよ」という意味を持たせることが出来ます。
    
//     // 反対にGenre側に設定する場合は@OneToManyを設定できますがこの話は後ほど行います。
//     // @ManyToOne
//     // @JoinColumn(name = "genre_id", nullable = false)
//     // private Genre genre;

//     // デフォルトコンストラクタ
//     public Book() {}

//     public Book(Long id, String title, String author, String isbn, LocalDate publishedDate, boolean isDeleted) {
//         this.id = id;
//         this.title = title;
//         this.author = author;
//         this.isbn = isbn;
//         this.publishedDate = publishedDate;
//         this.isDeleted = isDeleted;
//         // this.genre = genre;
//     }

//     // ゲッターとセッター
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public String getAuthor() {
//         return author;
//     }

//     public void setAuthor(String author) {
//         this.author = author;
//     }

//     public String getIsbn() {
//         return isbn;
//     }

//     public void setIsbn(String isbn) {
//         this.isbn = isbn;
//     }

//     public LocalDate getPublishedDate() {
//         return publishedDate;
//     }

//     public void setPublishedDate(LocalDate publishedDate) {
//         this.publishedDate = publishedDate;
//     }

//     public Boolean getIsDeleted() {
//         return isDeleted;
//     }

//     public void setDeleted(Boolean isDeleted) {
//         this.isDeleted = isDeleted;
//     }

    // Genreのゲッター
    // public Genre getGenre() {
    //     return genre;
    // }

    // // Genreのセッター
    // public void setGenre(Genre genre) {
    //     this.genre = genre;
    // }
// }

package com.example.bookmanagement.model;

import org.hibernate.annotations.SQLRestriction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@SQLRestriction("is_deleted = false")
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column
    private LocalDate publishedDate;

    @Column
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    public Book() {
    }

    public Book(Long id, String title, String author, String isbn,
                LocalDate publishedDate, boolean isDeleted, Genre genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.isDeleted = isDeleted;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
