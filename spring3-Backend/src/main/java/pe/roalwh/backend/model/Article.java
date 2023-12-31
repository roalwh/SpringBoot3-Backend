package pe.roalwh.backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article {
//  pk 지정,Identity (자동증가)지정,컬럼 명 ,업데이트 불가 지정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
//    컬럼명, notnull 지정
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    생성
    @Builder
    public Article(String title, String content){
        this.title = title;
        this.content = content;
    }

//    수정
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }


}
