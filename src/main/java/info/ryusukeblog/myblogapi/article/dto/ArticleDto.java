package info.ryusukeblog.myblogapi.article.dto;

import info.ryusukeblog.myblogapi.article.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDto {
    public int id;
    public String title;
    public String content;
    public boolean isWriting;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String partOfContent;
    public List<Tag> tagList;
}
