package info.ryusukeblog.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
