package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;

public class Tag {

    //id
    private long id;
    //タグ名
    private String name;

    //作成日時
    private LocalDateTime createdAt;

    //更新日時
    private LocalDateTime updatedAt;

    public Tag(int id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

        if ("".equals(name)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
