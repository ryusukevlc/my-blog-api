package info.ryusukeblog.myblogapi.article.repository;

import info.ryusukeblog.myblogapi.article.model.Article;
import info.ryusukeblog.myblogapi.article.model.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static ResultSetExtractor<List<Article>> ARTICLES_EXTRACTOR = (resultSet) -> {

        List<Article> articles = new ArrayList<>();

        while (resultSet.next()) {
            Article article = new Article(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("part_of_content"),
                    resultSet.getObject("created_at", LocalDateTime.class)
            );
            articles.add(article);
        }
        return articles;
    };

    private final static ResultSetExtractor<List<Article>> ARTICLE_LIST_EXTRACTOR = (resultSet) -> {

        List<Article> articleList = new ArrayList<>();
        List<Tag> tagList = new ArrayList<>();

        int beforeId = 0;

        while (resultSet.next()) {

            if (beforeId != resultSet.getInt("id")) {

                tagList = new ArrayList<>();
                if (Objects.nonNull(resultSet.getString("tag_name"))) {
                    tagList.add(new Tag(resultSet.getString("tag_name")));
                }

                Article article = new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        tagList,
                        resultSet.getObject("created_at", LocalDateTime.class),
                        resultSet.getObject("updated_at", LocalDateTime.class)
                );
                articleList.add(article);
            }

            if (Objects.nonNull(resultSet.getString("tag_name"))) {
                tagList.add(new Tag(resultSet.getString("tag_name")));
            }

            beforeId = resultSet.getInt("id");
        }

        return articleList;
    };

    public void save(Article article) {

    }

    @Override
    public List<Article> selectForPagination(int limit, int offset) {
        String sql = "select id, title, part_of_content, created_at from articles where is_writing = 0 order by created_at desc limit ? offset ?";
        return jdbcTemplate.query(sql, ARTICLES_EXTRACTOR, limit, offset);
    }

    @Override
    public List<Article> selectAll() {
        String sql = "select * from articles ";
        return jdbcTemplate.query(sql, ARTICLE_LIST_EXTRACTOR);
    }


}
