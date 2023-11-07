package info.ryusukeblog.myblogapi.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.ryusukeblog.myblogapi.dto.ArticleDto;
import info.ryusukeblog.myblogapi.dto.TagDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    @MockBean
    ArticleControllerValidator validator;

    @Autowired
    ObjectMapper objectMapper;


    static ArticleDto testArticleDto;

    static ArticleDto testDraftArticleDto;

    @BeforeAll
    public static void setUpBeforeAll() {
        List<TagDto> tagDtoList = new ArrayList<>();
        TagDto tagDto = new TagDto(1, "name", LocalDateTime.now(), LocalDateTime.now());
        tagDtoList.add(tagDto);
        testArticleDto = new ArticleDto(1, "title", "content", false, LocalDateTime.now(), LocalDateTime.now(),
                "partOfContent", tagDtoList);
        testDraftArticleDto = new ArticleDto(1, "title", "content", true, LocalDateTime.now(), LocalDateTime.now(),
                "partOfContent", tagDtoList);
    }

    @Test
    public void getOneTest() throws Exception {
        when(articleService.getOne(1, false)).thenReturn(testArticleDto);
        String expectedJson = objectMapper.writeValueAsString(testArticleDto);
        mockMvc.perform(get("/articles/1")).andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk()).andExpect(content().json(expectedJson));
    }

    @Test
    public void getOneWithMarkdownTrue() throws Exception {
        when(articleService.getOne(1, true)).thenReturn(testArticleDto);
        String expectedJson = objectMapper.writeValueAsString(testArticleDto);
        mockMvc.perform(get("/articles/1").param("markdown", "true"))
               .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
               .andExpect(content().json(expectedJson));
    }

    @Test
    public void getOneWithMarkdownFalse() throws Exception {
        when(articleService.getOne(1, false)).thenReturn(testArticleDto);
        String expectedJson = objectMapper.writeValueAsString(testArticleDto);
        mockMvc.perform(get("/articles/1").param("markdown", "false"))
               .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
               .andExpect(content().json(expectedJson));
    }


    @Test
    public void getCountTest() throws Exception {
        when(articleService.getCount()).thenReturn(10L);
        String expectedJson = "{'allArticleNumbers':10}";
        mockMvc.perform(get("/articles/count")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
               .andExpect(content().json(expectedJson));
    }

    @Test
    public void saveTest() throws Exception {
        when(articleService.save(any(ArticleDto.class))).thenReturn(testArticleDto);
        String requestBody = objectMapper.writeValueAsString(testArticleDto);
        mockMvc.perform(post("/articles").contentType(MediaType.APPLICATION_JSON).content(requestBody))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk()).andExpect(content().json(requestBody));
    }

    @Test
    public void saveAsDraftTest() throws Exception {
        when(articleService.saveAsDraft(any(ArticleDto.class))).thenReturn(testDraftArticleDto);
        String requestBody = objectMapper.writeValueAsString(testDraftArticleDto);
        mockMvc.perform(post("/articles/drafts").contentType(MediaType.APPLICATION_JSON).content(requestBody))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk()).andExpect(content().json(requestBody));
    }

    @Test
    public void updateTest() throws Exception {
        when(articleService.update(any(ArticleDto.class))).thenReturn(testArticleDto);
        String requestBody = objectMapper.writeValueAsString(testArticleDto);
        mockMvc.perform(patch("/articles").contentType(MediaType.APPLICATION_JSON).content(requestBody))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(status().isOk()).andExpect(content().json(requestBody));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/articles/1")).andDo(MockMvcResultHandlers.print())
               .andExpect(status().isNoContent());
    }
}