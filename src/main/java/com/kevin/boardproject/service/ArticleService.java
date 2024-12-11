package com.kevin.boardproject.service;

import com.kevin.boardproject.domain.type.SearchType;
import com.kevin.boardproject.dto.ArticleDto;
import com.kevin.boardproject.dto.ArticleUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ArticleService {

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(Long articleId, ArticleUpdateDto dto) {
    }

    public void deleteArticle(Long articleId) {

    }
}
