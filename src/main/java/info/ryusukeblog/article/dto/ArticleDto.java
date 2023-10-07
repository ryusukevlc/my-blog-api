package info.ryusukeblog.article.dto;

import info.ryusukeblog.article.model.Tag;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsWriting() {
        return isWriting;
    }

    public void setIsWriting(boolean isWriting) {
        this.isWriting = isWriting;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPartOfContent() {
        return partOfContent;
    }

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

}
