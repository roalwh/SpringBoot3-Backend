package pe.roalwh.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import pe.roalwh.backend.dto.AddArticleRequest;
import pe.roalwh.backend.dto.UpdateArticleRequest;
import pe.roalwh.backend.model.Article;
import pe.roalwh.backend.repository.BlogRepository;

import java.util.List;

@RequiredArgsConstructor //final이 붙거나 @Notnull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;
    //생성
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());

    }
    //전체 리스트
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    //    아이디로 찾기
    public Article findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : id"));
    }

    //  아이디값으로 삭제
    public void delete(Long id){
        blogRepository.deleteById(id);
    }

    // 아이디값으로 게시글 수정
    @Transactional
    public Article update(Long id, UpdateArticleRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
        article.update(article.getTitle(), request.getContent());

        return article;
    }



}
