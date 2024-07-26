package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String index(Model model) {
        List<Book> books = bookService.index();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new-book";
    }

    @PostMapping("/new")
    public String newBook(Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        // 指定されたidを持つ書籍をデータベースから検索する
        Book book = bookService.findBookById(id)
                // Optionalで返された書籍が存在しない場合は例外を投げる
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
    
        // モデルに書籍を追加する。Thymeleafテンプレートで使用できるようにする
        model.addAttribute("book", book);
    
        // 書籍の更新フォームのテンプレートを返す。Thymeleafではテンプレート名が返され、
        // これに基づいて対応するHTMLがレンダリングされる
        return "books/edit";
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
}
