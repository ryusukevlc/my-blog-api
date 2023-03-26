package info.ryusukeblog.myblogapi.article.repository.extractor;

import info.ryusukeblog.myblogapi.article.model.Article;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticlesExtractor implements ResultSetExtractor<List<Article>> {

    @Override
    public List<Article> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<Article> articles = new ArrayList<>();

        while (rs.next()) {
            Article article = new Article(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("part_of_content"),
                    rs.getObject("created_at", LocalDateTime.class)
            );
            articles.add(article);
        }
        return articles;
    }
}
