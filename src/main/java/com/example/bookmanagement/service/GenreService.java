package com.example.bookmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookmanagement.model.Genre; // パッケージ名の修正
import com.example.bookmanagement.repository.GenreRepository; // パッケージ名の修正
import org.springframework.beans.factory.annotation.Autowired; // 追加
import org.springframework.stereotype.Service; // 追加
import java.util.List; // 追加


@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }
}