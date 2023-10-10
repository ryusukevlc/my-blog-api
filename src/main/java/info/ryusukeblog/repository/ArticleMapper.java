package info.ryusukeblog.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import info.ryusukeblog.model.Article;

@Mapper
public interface ArticleMapper {

    Article findById(Integer id);

    List<Article> findAll();

    List<Article> findWithPagination(Integer limit, Integer offset);

    int count();

    boolean save(Article article);

    boolean delete(int id);

    boolean update(Article article);

    int getLastInsertId();

    int saveArticleTagRelation(Article article);

    boolean deleteArticleTagRelationByArticleId(int articleId);
    
}
