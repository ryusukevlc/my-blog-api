package info.ryusukeblog.myblogapi.tag;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ryusukeblog.myblogapi.dto.TagDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TagControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TagService tagService;

    private TagDto tagDto;

    private List<TagDto> tagDtoList;

    @BeforeEach
    public void setUpBeforeAll() {
        this.tagDto = new TagDto(1, "name", LocalDateTime.now(), LocalDateTime.now());
        this.tagDtoList = Arrays.asList(this.tagDto, this.tagDto);
    }


    @Test
    public void findAllTest() throws Exception {
        when(this.tagService.findAll()).thenReturn(this.tagDtoList);
        String responseJson = objectMapper.writeValueAsString(this.tagDtoList);
        mockMvc.perform(get("/tags")).andDo(print()).andExpect(status().isOk())
               .andExpect(content().json(responseJson));
    }

    @Test
    public void saveTest() throws Exception {
        when(this.tagService.save(any(TagDto.class))).thenReturn(this.tagDto);
        String requestJson = objectMapper.writeValueAsString(this.tagDto);
        mockMvc.perform(post("/tags").contentType(MediaType.APPLICATION_JSON).content(requestJson))
               .andDo(print()).andExpect(status().isOk()).andExpect(content().json(requestJson));
    }

    @Test
    public void deleteTest() throws Exception {
        doNothing().when(this.tagService).delete(any(Integer.class));
        mockMvc.perform(delete("/tags/1")).andDo(print())
               .andExpect(status().isNoContent());
    }

}
