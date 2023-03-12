package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;

public class Tag {

    //id
    private long id;

    //タグ名
    private String name;

    //作成日時
    private LocalDateTime created_at;

    //更新日時
    private LocalDateTime updated_at;


    public Tag(String name) {
        this.name = name;
    }
}
