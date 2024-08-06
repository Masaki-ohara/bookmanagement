// package com.example.bookmanagement.service;


// import java.util.List;
// import java.util.Optional;

// import com.example.bookmanagement.repository.StoreRepository;

// import org.springframework.stereotype.Service;
// import com.example.bookmanagement.model.Store;
// import com.example.bookmanagement.repository.StoreRepository;
// import org.springframework.beans.factory.annotation.Autowired;

// @Service
// public class StoreService {
//     @Autowired
//     private StoreRepository storeRepository;

//     // 書店情報の全件取得
//     public List<Store> findAllStores() {
//         return storeRepository.findAll();
//     }

//     // 書店情報のID検索
//     public Optional<Store> findStoreById(Long storeId) {
//         return storeRepository.findById(storeId);
//     }
// }
package com.example.bookmanagement.service;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.Store;
import com.example.bookmanagement.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import jakarta.persistence.EntityNotFoundException;


@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    // 書店情報の全件取得
    public List<Store> findAllStores() {
        return storeRepository.findAll();
    }

    // 書店情報のID検索
    public Optional<Store> findStoreById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    public void saveStoreWithBooks(Store store, Set<Book> books) {
        store.setBooks(books);
        storeRepository.save(store); // 書店と書籍の関連が保存されます
    }

    // 書店と書籍の更新処理
    @Transactional
    public void updateStoreWithBooks(Store updatedStore, Set<Book> updatedBooks) {
        // 既存の書店を取得
        Store existingStore = storeRepository.findById(updatedStore.getId())
            .orElseThrow(() -> new NoSuchElementException("Store not found"));

        // 書店のデータ(名前)を更新
        existingStore.setName(updatedStore.getName());

        // 書籍のセットを更新
        existingStore.setBooks(updatedBooks);

        // 書店を保存
        storeRepository.save(existingStore);
    }

    @Transactional
    public void deleteStore(Long storeId) {
        Store store = findStoreById(storeId)
            .orElseThrow(() -> new EntityNotFoundException("store not found"));
        store.getBooks().clear(); // 書店と書籍の関連をクリア
        storeRepository.delete(store); // 書店を削除
    }
}
