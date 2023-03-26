package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;

public class Tag {

    //id
    public final long id;
    //タグ名
    public final String name;

    //作成日時
    public final LocalDateTime createdAt;

    //更新日時
    public final LocalDateTime updatedAt;

    public Tag(int id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {

        if ("".equals(name)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
