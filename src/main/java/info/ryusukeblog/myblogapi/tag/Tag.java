package info.ryusukeblog.myblogapi.tag;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Tag {

    private long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
