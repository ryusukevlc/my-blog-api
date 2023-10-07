package info.ryusukeblog.model;

import java.time.LocalDateTime;

public class Article {

    private int id;

    //タイトル
    private String title;

    //内容
    private String content;

    //編集中かどうか（true: 編集中、false: 編集済み）
    private boolean isWriting = false;

    //作成日時
    private LocalDateTime createdAt;

    //更新日時
    private LocalDateTime updatedAt;

    // 内容の一部
    private String partOfContent;

}
