package pe.roalwh.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.roalwh.backend.model.Article;

public interface BlogRepository extends JpaRepository<Article,Long> {

    
  }

