package pe.roalwh.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pe.roalwh.backend.dto.AddArticleRequest;
import pe.roalwh.backend.dto.UpdateArticleRequest;
import pe.roalwh.backend.model.Article;
import pe.roalwh.backend.repository.BlogRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class BlogApiControllerTest {

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  BlogRepository blogRepository;

  @BeforeEach // 테스트 실행 전 실행하는 메서드
  public void mockmvcSetup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .build();
    blogRepository.deleteAll();
  }

  @DisplayName("addArticle : 블로그 글 추가 성공")
  @Test
  public void addArticle() throws Exception {

    final String url = "/api/articles";
    final String title = "title";
    final String content = "content";
    final AddArticleRequest userRequest = new AddArticleRequest(title, content);

    final String requestBody = objectMapper.writeValueAsString(userRequest);

    // when
    // 설정한 내용을 바탕으로 요청 전송
    ResultActions result = mockMvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(requestBody));

    // then
    result.andExpect(status().isCreated());

    List<Article> articles = blogRepository.findAll();

    assertThat(articles.size()).isEqualTo(1); // 크기가 1인지 검증
    assertThat(articles.get(0).getTitle()).isEqualTo(title);
    assertThat(articles.get(0).getContent()).isEqualTo(content);
  }

  @DisplayName("findAllArticles:블로그 글 목록 조회 성공")
  @Test
  public void findAllArticles() throws Exception {

    final String url = "/api/articles";
    final String title = "title";
    final String content = "content";

    blogRepository.save(Article.builder()
        .title(title)
        .content(content)
        .build());

    // then
    final ResultActions resultActions = mockMvc.perform(get(url)
        .accept(MediaType.APPLICATION_JSON));

    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].content").value(content))
        .andExpect(jsonPath("$[0].title").value(title));
  }

  @DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
  @Test
  public void deleteArticle() throws Exception {
    // given
    final String url = "/api/articles/{id}";
    final String title = "title";
    final String content = "content";
    Article savedArticle = blogRepository.save(Article.builder()
        .title(title)
        .content(content)
        .build());
    // when
    mockMvc.perform(delete(url, savedArticle.getId()))
        .andExpect(status().isOk());
    // then
    List<Article> articles = blogRepository.findAll();
    assertThat(articles).isEmpty();
  }

  @DisplayName("updateArticle: 블로그 글 삭제에 성공한다.")
  @Test
  public void updateArticle() throws Exception {
    //수정할 값 만들기
    final String url = "/api/articles/{id}";
    final String title = "title";
    final String content = "content";

    // Given 블로그 글을 저장하고 글 수정
    Article savedArticle = blogRepository.save(Article.builder()
        .title(title)
        .content(content)
        .build());

    final String newTitle = "new title";
    final String newContent = "new content";
    UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

    // when //update 요청 
    ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(request)));
    
    // then //200 응답
    result.andExpect(status().isOk());
    Article article = blogRepository.findById(savedArticle.getId()).get();
    assertThat(article.getTitle()).isEqualTo(newTitle);
    assertThat(article.getContent()).isEqualTo(newContent);
  }

}