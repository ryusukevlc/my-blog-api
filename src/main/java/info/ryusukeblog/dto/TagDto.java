package info.ryusukeblog.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDto {

    private int id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
