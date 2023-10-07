package info.ryusukeblog.article.service;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import info.ryusukeblog.article.repository.ArticleRepository;
import info.ryusukeblog.article.dto.ArticleDto;
import info.ryusukeblog.article.model.Article;
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
    public ArticleDto getArticleDetail(int id, List<String> fields, boolean isMarkdown) {
        ArticleDto articleDto = this.articleRepository.selectForArticleDetail(id, fields);
        if (!isMarkdown) {
            Parser parser = Parser.builder().build();
            Document document = parser.parse(articleDto.getContent());
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            articleDto.setContent(renderer.render(document));
        }
        return articleDto;
    }

    @Override
    public ArticleDto save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public ArticleDto update(Article article) {
        return this.articleRepository.update(article);
    }


    @Override
    public void delete(int id) {
        this.articleRepository.delete(id);
    }


}
