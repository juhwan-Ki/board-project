package com.kevin.boardproject.repository;

import com.kevin.boardproject.config.JpaConfig;
import com.kevin.boardproject.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    // 생성자 주입
    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    
    @DisplayName("select test")
    @Test
    public void givenTestData_whenSelecting_thenWorksFine() throws Exception {
       // given
       
       // when
        List<Article> articles = articleRepository.findAll();

       // then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert test")
    @Test
    public void givenTestData_whenInserting_thenWorksFine() throws Exception {
        // given
        long previousCount = articleRepository.count();

        // when
        Article saveArticle =  articleRepository.save(Article.of("new article", "new content", "#spring"));

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update test")
    @Test
    public void givenTestData_whenUpdating_thenWorksFine() throws Exception {
        // given
        Article updateArticle = articleRepository.findById(1L).orElseThrow();
        String updateHashing = "#springboot";
        updateArticle.setHashtag(updateHashing);

        // when
        Article saveArticle =  articleRepository.save(updateArticle);

        // then
        assertThat(saveArticle).hasFieldOrPropertyWithValue("hashtag",updateHashing);
    }

    @DisplayName("delete test")
    @Test
    public void givenTestData_whenDeleting_thenWorksFine() throws Exception {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long deletedCommentSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }

}