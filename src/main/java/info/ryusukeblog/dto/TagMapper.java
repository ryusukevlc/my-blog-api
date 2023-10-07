package info.ryusukeblog.dto;

import info.ryusukeblog.model.models.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {


    /**
     * POST用
     *
     * @param tagDto
     * @return
     */
    public Tag getTagFromDtoForCreate(TagDto tagDto) {
        return new Tag(
                tagDto.name
        );
    }

}
