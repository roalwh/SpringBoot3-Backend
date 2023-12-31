package pe.roalwh.backend.dto;


import java.time.LocalDateTime;

import lombok.Getter;
import pe.roalwh.backend.model.Article;

@Getter
public class ArticleListViewResponse {

  private final Long id;
  private final String title;
  private final String content;
  private LocalDateTime createdAt;

  public  ArticleListViewResponse(Article article){

    this.id = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
    this.createdAt = article.getCreatedAt();
  }
}
