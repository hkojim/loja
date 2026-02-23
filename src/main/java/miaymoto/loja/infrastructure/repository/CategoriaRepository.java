package miaymoto.loja.infrastructure.repository;

import miaymoto.loja.infrastructure.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}
