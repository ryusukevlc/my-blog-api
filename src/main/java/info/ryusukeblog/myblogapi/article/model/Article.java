package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;

public class Article {

    //id（pk）
    private int id;

    //記事タイトル
    private String title;

    //記事内容
    private String content;

    //作成日時
    private LocalDateTime created_at;

    //更新日時
    private LocalDateTime updated_at;

    // 内容の一部（ホーム画面で表示する用）
    private String partOfContent;

    // タグリスト
    private List<Tag> tagList;

    public Article(String title, String content, String partOfContent, List<Tag> tagList) {
        this.title = title;
        this.content = content;
        this.partOfContent = partOfContent;
        this.tagList = tagList;
    }
}
