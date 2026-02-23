package miaymoto.loja.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    // Relacionamento One-to-Many: Uma categoria para muitos produtos
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<ProdutoEntity> produtos;
}
