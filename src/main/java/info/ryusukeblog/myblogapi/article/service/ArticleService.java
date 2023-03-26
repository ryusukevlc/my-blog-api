package info.ryusukeblog.myblogapi.article.service;

import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getArticlesForPagination(int limit, int offset) {
        return this.articleRepository.selectForPagination(limit, offset);
    }

    public Article getArticleDetail(int id) {
        return this.articleRepository.selectForArticleDetail(id);
    }

    public void save(Article article) {
//        this.saveArticle();
        this.saveTags();
    }

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void saveTags() {

    }


}
