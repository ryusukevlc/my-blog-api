package info.ryusukeblog.myblogapi.article.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Article {

    //id（pk）
    public final int id;

    //記事タイトル
    public final String title;

    //記事内容
    public final String content;

    //作成日時
    public final LocalDateTime createdAt;

    //更新日時
    public final LocalDateTime updatedAt;

    // 内容の一部（ホーム画面で表示する用）
    public final String partOfContent;

    // タグリスト
    public List<Tag> tagList = new ArrayList<>();

    private final static int MAX_PART_OF_CONTENT = 180;

    /**
     * 記事詳細用コンストラクタ
     *
     * @param id
     * @param title
     * @param content
     * @param tagList
     * @param createdAt
     * @param updatedAt
     */
    public Article(int id, String title, String content, List<Tag> tagList, LocalDateTime createdAt, LocalDateTime updatedAt) {

        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (Objects.isNull(content) || content.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.title = title;
        this.content = content;
        this.partOfContent = content.substring(0, MAX_PART_OF_CONTENT);

        if (tagList != null && !tagList.isEmpty()) {
            this.tagList = tagList;
        }

        if (createdAt != null) {
            this.createdAt = createdAt;
        } else {
            this.createdAt = null;
        }
        if (updatedAt != null) {
            this.updatedAt = updatedAt;
        } else {
            this.updatedAt = null;
        }
    }

    public Article(int id, String title, String partOfContent, LocalDateTime createdAt) {

        this.id = id;

        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (Objects.isNull(partOfContent) || partOfContent.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.title = title;
        this.partOfContent = partOfContent;

        if (createdAt != null) {
            this.createdAt = createdAt;
        } else {
            this.createdAt = null;
        }

        this.content = "";
        this.updatedAt = null;

    }

    public void setTagIntoTagList(Tag tag) {
        if (Objects.nonNull(tag)) {
            this.tagList.add(tag);
        }
    }

    public void setTagList(List<Tag> tagList) {
        if (Objects.nonNull(tagList)) {
            this.tagList = tagList;
        }
    }
}
