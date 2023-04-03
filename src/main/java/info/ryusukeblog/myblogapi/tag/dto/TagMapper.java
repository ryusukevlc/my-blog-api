package info.ryusukeblog.myblogapi.tag.dto;

import info.ryusukeblog.myblogapi.tag.model.Tag;
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
