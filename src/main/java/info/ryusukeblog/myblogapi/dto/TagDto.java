package info.ryusukeblog.myblogapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class TagDto {

    private int id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
