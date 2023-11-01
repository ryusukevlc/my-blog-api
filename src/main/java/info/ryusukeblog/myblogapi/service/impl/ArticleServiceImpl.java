package info.ryusukeblog.myblogapi.service.impl;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import info.ryusukeblog.myblogapi.dto.ArticleDto;
import info.ryusukeblog.myblogapi.model.Article;
import info.ryusukeblog.myblogapi.repository.ArticleMapper;
import info.ryusukeblog.myblogapi.service.ArticleService;
import org.jsoup.Jsoup;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    MessageSource messageSource;

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";

    private static final int PART_OF_CONTENT_LENGTH = 150;
    private final ArticleMapper articleMapper;
    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, ModelMapper modelMapper, MessageSource messageSource) {
        this.articleMapper = articleMapper;
        this.modelMapper = modelMapper;
        this.messageSource = messageSource;
    }

    @Override
    public List<ArticleDto> getColumnsWithPagination(int limit, int offset, List<String> fields) {
        Map<String, Boolean> fieldMap = convertListToMap(fields);
        return this.modelMapper.map(this.articleMapper.findWithPagination(limit, offset, fieldMap), new TypeToken<List<ArticleDto>>() {
        }.getType());
    }

    @Override
    public long getCount() {
        return this.articleMapper.count();
    }

    @Override
    public ArticleDto getOne(int id, boolean isMarkdown) {
        ArticleDto articleDto = this.modelMapper.map(this.articleMapper.findById(id), ArticleDto.class);
        if (!isMarkdown) {
            this.convertToHtml(articleDto);
        }
        return articleDto;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        if (articleDto.getPartOfContent() == null || articleDto.getPartOfContent().isEmpty()) {
            this.setPartOfContent(articleDto);
        }
        return this.saveArticle(articleDto, INSERT);
    }

    @Override
    public ArticleDto saveAsDraft(ArticleDto articleDto) {
        articleDto.setWriting(true);
        return this.saveArticle(articleDto, INSERT);
    }

    @Override
    public ArticleDto update(ArticleDto articleDto) {
        if (articleDto.getPartOfContent() == null || articleDto.getPartOfContent().isEmpty()) {
            this.setPartOfContent(articleDto);
        }
        return this.saveArticle(articleDto, UPDATE);
    }

    @Override
    public boolean delete(int id) {
        if (this.articleMapper.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.messageSource.getMessage("ERROR.ARTICLE_NOT_FOUND_WITH_ID", new String[]{Integer.valueOf(id).toString()}, Locale.JAPAN));
        }
        boolean hasDeleted = this.articleMapper.delete(id);
        hasDeleted = this.articleMapper.deleteArticleTagRelationByArticleId(id);
        return hasDeleted;
    }

    private void convertToHtml(ArticleDto articleDto) {
        Parser parser = Parser.builder().build();
        Document document = parser.parse(articleDto.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        articleDto.setContent(renderer.render(document));
    }

    private ArticleDto saveArticle(ArticleDto articleDto, String type) {
        // TODO: このメソッドはトランザクション化した方がいい。理由は複数テーブル更新するため、最後のテーブル更新時に例外が発生するとデータに不整合が発生するため。

        Article article = this.modelMapper.map(articleDto, Article.class);

        if (INSERT.equals(type)) {
            this.articleMapper.save(article);
        } else if (UPDATE.equals(type)) {
            if (this.articleMapper.findById(article.getId()) == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, this.messageSource.getMessage("ERROR.ARTICLE_NOT_FOUND_WITH_ID", new String[]{Integer.valueOf(article.getId()).toString()}, Locale.JAPAN));
            }
            this.articleMapper.update(article);
        }

        if (UPDATE.equals(type)) {
            this.articleMapper.deleteArticleTagRelationByArticleId(article.getId());
        }
        if (article.getTagList().isEmpty()) {
            return articleDto;
        }
        if (INSERT.equals(type)) {
            article.setId(this.articleMapper.getLastInsertId());
        }
        this.articleMapper.saveArticleTagRelation(article);

        return articleDto;
    }

    private Map<String, Boolean> convertListToMap(List<String> fields) {
        Map<String, Boolean> fieldMap = new HashMap<>();
        for (String field : fields) {
            fieldMap.put(field, true);
        }
        return fieldMap;
    }

    private void setPartOfContent(ArticleDto articleDto) {
        this.convertToHtml(articleDto);
        String plainText = Jsoup.parse(articleDto.getContent()).text();
        if (plainText.length() > PART_OF_CONTENT_LENGTH) {
            articleDto.setPartOfContent(plainText.substring(0, PART_OF_CONTENT_LENGTH));
        } else {
            articleDto.setPartOfContent(plainText);
        }
    }
}
