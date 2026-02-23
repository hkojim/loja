package miaymoto.loja.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;

    // Relacionamento Many-to-One: Muitos produtos pertencem a uma categoria
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;
}
