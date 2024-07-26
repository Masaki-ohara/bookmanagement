package com.example.bookmanagement.service;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book updatedBook) {
        return bookRepository.save(updatedBook);
    }

    public void deleteBookById(Long id) {
        // 引数として受け取ったIDを使って、データベースから該当する本の情報を取得します。
        Optional<Book> optionalBook = bookRepository.findById(id);
    
        // 取得したOptionalオブジェクトが空でない（つまり、該当する本が見つかった）場合の処理です。
        if (optionalBook.isPresent()) {
            // Optionalから本のインスタンスを取り出します。
            Book book = optionalBook.get();
    
            // 本の削除フラグをtrueに設定します。
            // 修正: `isDeleted` メソッドが Boolean 型になっているため、Boolean 型のメソッドを使用します。
            book.setDeleted(true);
    
            // 更新された本の情報をデータベースに保存します。
            // ここで本の削除フラグがデータベースに反映されます。
            bookRepository.save(book);
        }
    }    
}