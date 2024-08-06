package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.Store;
import com.example.bookmanagement.service.StoreService;
import com.example.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.NoSuchElementException; // 修正: 追加

@RequestMapping("/stores")
@Controller
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listStores(Model model) {
        List<Store> stores = storeService.findAllStores();
        System.out.println("Found stores: " + stores); // ログに出力
        model.addAttribute("stores", stores);
        return "stores/list";
    }

    @GetMapping("/add")
    public String addStoreForm(Model model) {
        model.addAttribute("store", new Store());
        // Booksデータの全件取得
        model.addAttribute("books", bookService.index());
        return "stores/add-store";
    }

    @PostMapping("/add")
    public String addStore(Store store, @RequestParam("books") List<Long> books) {
        // booksリストを使ってBookオブジェクトのセットを作成
        Set<Book> bookSet = new HashSet<>();
        for (Long bookId : books) {
            // 各bookIdに対応するBookオブジェクトを作成し、セットに追加
            bookSet.add(new Book(bookId));
        }
        // 書店とその書籍情報を保存するサービスメソッドを呼び出し
        storeService.saveStoreWithBooks(store, bookSet);
        // 書店一覧ページにリダイレクト
        return "redirect:/stores";
    }

    // 書籍の更新フォームを表示
    @GetMapping("/edit/{id}")
    public String showEditStoreForm(@PathVariable("id") Long id, Model model) {
        Store store = storeService.findStoreById(id)
            .orElseThrow(() -> new NoSuchElementException("Store not found")); // 修正: NoSuchElementException
        model.addAttribute("store", store);
        model.addAttribute("allBooks", bookService.index());
        return "stores/edit";
    }

    // 書店の更新処理を担当するメソッド
    @PostMapping("/edit")
    public String updateStore(Store store, @RequestParam("books") List<Long> books) {
        // 書店に関連付ける本のIDリストを、Bookオブジェクトのセットに変換
        Set<Book> bookSet = books.stream()
            // 各本のIDを使って、新しいBookオブジェクトを作成
            .map(bookId -> new Book(bookId))
            // それらのBookオブジェクトをSetに収集
            .collect(Collectors.toSet());

        // 更新対象の書店とその関連本のセットを保存するサービスメソッドを呼び出し
        storeService.updateStoreWithBooks(store, bookSet);

        // 書店一覧ページにリダイレクト
        return "redirect:/stores";
    }

    // 書籍の削除
    @GetMapping("/delete/{id}")
    public String deleteStore(@PathVariable("id") Long id) {
        storeService.deleteStore(id);
        return "redirect:/stores";
    }
}
