package pe.roalwh.backend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import pe.roalwh.backend.dto.ArticleListViewResponse;
import pe.roalwh.backend.service.BlogService;

@RequiredArgsConstructor
@Controller
public class BlogViewController {

  private final BlogService blogService;

  @GetMapping("/articles")
  public String getArticles(Model model) {
    List<ArticleListViewResponse> article = blogService.findAll().stream()
        .map(ArticleListViewResponse::new)
        .toList();
    model.addAttribute("articles", article);

    return "articleList";
  }
}
