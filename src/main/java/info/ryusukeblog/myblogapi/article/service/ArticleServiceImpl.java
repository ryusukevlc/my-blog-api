package info.ryusukeblog.myblogapi.article.service;

import info.ryusukeblog.myblogapi.article.dto.ArticleDto;
import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<ArticleDto> getArticlesForPagination(int limit, int offset, List<String> fields) {
        return this.articleRepository.selectForPagination(limit, offset, fields);
    }

    @Override
    public int getArticleCount() {
        return this.articleRepository.getArticleCount();
    }

    @Override
    public Article getArticleDetail(int id) {
        return this.articleRepository.selectForArticleDetail(id);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        return this.articleRepository.update(article);
    }


    @Override
    public void delete(int id) {
        this.articleRepository.delete(id);
    }


}
