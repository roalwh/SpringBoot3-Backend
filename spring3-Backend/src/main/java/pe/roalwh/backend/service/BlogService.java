package pe.roalwh.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.roalwh.backend.domain.Article;
import pe.roalwh.backend.dto.AddArticleRequest;
import pe.roalwh.backend.repository.BlogRepository;

@RequiredArgsConstructor //final이 붙거나 @Notnull이 붙은 필드의 생성자 추가
@Service //빈으로 등록
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
}
