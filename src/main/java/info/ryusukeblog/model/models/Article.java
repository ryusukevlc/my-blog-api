package info.ryusukeblog.model.models;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class Article {

    private int id;

    //記事タイトル
    private String title;

    //記事内容
    private String content;

    //記事編集中フラグ
    private boolean isWriting = false;

    //作成日時
    private LocalDateTime createdAt;

    //更新日時
    private LocalDateTime updatedAt;

    // 内容の一部（ホーム画面で表示する用）
    private String partOfContent;

    // タグリスト
    private List<Tag> tagList = new ArrayList<>();

    private final static int MAX_PART_OF_CONTENT = 180;
    private final static String ERROR_MESSAGE_FOR_ID = "IDが存在しません。";
    private final static String ERROR_MESSAGE_FOR_TITLE = "タイトルが存在しません。";
    private final static String ERROR_MESSAGE_FOR_CONTENT = "記事内容が存在しません。";
    private final static String ERROR_MESSAGE_FOR_PART_OF_CONTENT = "記事内容(省略版)が存在しません。";

    public Article() {
    }

    /**
     * 記事詳細用コンストラクタ(get)
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
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_TITLE);
        }

        if (Objects.isNull(content) || content.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_CONTENT);
        }

        this.id = id;
        this.title = title;
        this.content = content;

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

        this.partOfContent = "";
    }

    /**
     * ホーム画面表示用コンストラクタ(get)
     *
     * @param id
     * @param title
     * @param partOfContent
     * @param createdAt
     */
    public Article(int id, String title, String partOfContent, LocalDateTime createdAt) {

        this.id = id;

        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_TITLE);
        }

        if (Objects.isNull(partOfContent) || partOfContent.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_PART_OF_CONTENT);
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

    /**
     * post用コンストラクタ
     *
     * @param title
     * @param content
     * @param tagList
     */
    public Article(String title, String content, List<Tag> tagList) {

        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_TITLE);
        }

        if (Objects.isNull(content) || content.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_CONTENT);
        }

        this.title = title;
        this.content = content;
        String text = convertMarkdownToPlainText(content);
        System.out.println(text);
        if (text.length() > 180) {
            this.partOfContent = text.substring(0, 180);
        } else {
            this.partOfContent = text;
        }

        if (tagList != null && !tagList.isEmpty()) {
            this.tagList = tagList;
        }
    }

    /**
     * PATCH用コンストラクタ
     *
     * @param title
     * @param content
     * @param tagList
     */
    public Article(int id, String title, String content, List<Tag> tagList) {

        if (Objects.isNull(id) || id == 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_ID);
        }

        this.id = id;

        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_TITLE);
        }

        if (Objects.isNull(content) || content.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_CONTENT);
        }

        this.title = title;
        this.content = content;
        String text = convertMarkdownToPlainText(content);
        System.out.println(text);
        if (text.length() > 180) {
            this.partOfContent = text.substring(0, 180);
        } else {
            this.partOfContent = text;
        }

        if (tagList != null && !tagList.isEmpty()) {
            this.tagList = tagList;
        }
    }

    /**
     * getArticlesのクエリパラメータに指定されたフィールド（カラム）が存在するカラムがチェックする（SQLインジェクション対策）
     *
     * @param fields
     */
    public void validateFieldNames(List<String> fields) {
        List<String> allowedFields = Arrays.asList("id", "title", "content", "part_of_content", "is_writing", "created_at", "updated_at");
        for (String field : fields) {
            if (!allowedFields.contains(field.trim())) {
                throw new IllegalArgumentException("Invalid field name: " + field);
            }
        }
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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean getIsWriting() {
        return isWriting;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getPartOfContent() {
        return partOfContent;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    private static String convertMarkdownToPlainText(String markdown) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        return removeHtmlTags(html);
    }

    private static String removeHtmlTags(String html) {
        String noTags = Pattern.compile("<[^>]*>").matcher(html).replaceAll("");
        return noTags.replaceAll("\\s+", " ").trim();
    }

}
