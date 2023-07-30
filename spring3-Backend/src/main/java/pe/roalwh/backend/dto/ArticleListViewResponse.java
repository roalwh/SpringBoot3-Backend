package pe.roalwh.backend.dto;


import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pe.roalwh.backend.model.Article;

@Getter
public class ArticleListViewResponse {

  private final Long id;
  private final String title;
  private final String content;

  public  ArticleListViewResponse(Article article){

    this.id = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
  }
}
