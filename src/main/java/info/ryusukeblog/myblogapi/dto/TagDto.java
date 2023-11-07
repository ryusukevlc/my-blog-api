package info.ryusukeblog.myblogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {

    private int id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
