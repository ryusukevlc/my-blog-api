package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.model.Tag;
import info.ryusukeblog.myblogapi.article.repository.extractor.ArticlesExtractor;
import info.ryusukeblog.myblogapi.article.repository.extractor.TagsExtractor;
import info.ryusukeblog.myblogapi.article.repository.rowmapper.ArticleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Article article) {
        String sql = "insert into articles (id, title, content, part_of_content) values (?, ?, ?, ?)";
    }

    @Override
    public List<Article> selectForPagination(int limit, int offset) {
        String sql = "select id, title, part_of_content, created_at from articles where is_writing = 0 order by created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, new ArticlesExtractor(), limit, offset);
    }

    @Override
    public Article selectForArticleDetail(int id) {

        String sql_article = "select id, title, content, created_at, updated_at from articles where id = ?";
        Article article = jdbcTemplate.queryForObject(sql_article, new ArticleRowMapper(), id);
        String sql_tags = "select tags.id, tags.name, tags.created_at, tags.updated_at from articles_tags left join tags on articles_tags.tag_id=tags.id where articles_tags.article_id in (?);";
        List<Tag> tags = jdbcTemplate.query(sql_tags, new TagsExtractor(), id);

        article.setTagList(tags);
        return article;
    }

}
