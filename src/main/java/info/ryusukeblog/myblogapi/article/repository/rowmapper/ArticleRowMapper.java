package info.ryusukeblog.myblogapi.article.repository.rowmapper;

import info.ryusukeblog.myblogapi.article.model.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ArticleRowMapper implements RowMapper<Article> {

    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Article(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                null,
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("updated_at", LocalDateTime.class)
        );
    }
}
