package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;

public class Tag {

    //id
    private long id;

    //タグ名
    private final String name;

    //作成日時
    private LocalDateTime createdAt;

    //更新日時
    private LocalDateTime updatedAt;

    public Tag(String name) {

        if ("".equals(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }
}
