package miaymoto.loja.business.dto.request;

public record ProdutoRequestDTO(String nome,
                                String descricao,
                                Double preco,
                                Long categoriaId) {
}
