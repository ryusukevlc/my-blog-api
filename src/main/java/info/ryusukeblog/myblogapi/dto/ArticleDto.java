package info.ryusukeblog.myblogapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleDto {

    public int id;
    public String title;
    public String content;
    public boolean isWriting;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String partOfContent;

    public List<TagDto> tagList;

}
