package info.ryusukeblog.myblogapi.repository;

import info.ryusukeblog.myblogapi.model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

    Article findById(Integer id);

    List<Article> findAll();

    List<Article> findWithPagination(Integer limit, Integer offset, Map<String, Boolean> fieldMap);

    int count();

    boolean save(Article article);

    boolean delete(int id);

    boolean update(Article article);

    int getLastInsertId();

    int saveArticleTagRelation(Article article);

    boolean deleteArticleTagRelationByArticleId(int articleId);

}
