package info.ryusukeblog.myblogapi.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class Article {

    private int id;

    private String title;

    private String content;

    private boolean isWriting = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String partOfContent;

    private List<Tag> tagList;

}
