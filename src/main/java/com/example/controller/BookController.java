package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.service.BookService;
import com.example.bookmanagement.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String index(Model model) {
        List<Book> books = bookService.index();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("genres", genreService.findAllGenres()); // Add this line
        return "books/new-book";
    }

    @PostMapping("/new")
    public String newBook(Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> optionalBook = bookService.findBookById(id);
        if (optionalBook.isPresent()) {
            // 値が存在する場合の処理
            List<Genre> genres = genreService.findAllGenres();  // ジャンルのリストを取得
            Book book = optionalBook.get();
            model.addAttribute("book", book);
            model.addAttribute("genres", genres);
            return "books/edit";
        } else {
            // 値が存在しない場合の処理
            // エラー画面へ飛ばしたり、エラーの記録をしたり
        }
        return "redirect:/books";
    }

    @PostMapping("/edit")
    public String editBook(Book book) {
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(
        @RequestParam(name = "author", required = false) String author,
        Model model) {
        List<Book> books = bookService.findByAuthor(author);
        model.addAttribute("books", books);
        return "books/search";
    }
}
