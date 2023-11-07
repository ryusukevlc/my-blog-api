package info.ryusukeblog.myblogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    public int id;
    public String title;
    public String content;
    public boolean isWriting;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String partOfContent;

    public List<TagDto> tagList;

    public void setIsWriting(boolean isWriting) {
        this.isWriting = isWriting;
    }

    public boolean getIsWriting() {
        return this.isWriting;
    }

}
