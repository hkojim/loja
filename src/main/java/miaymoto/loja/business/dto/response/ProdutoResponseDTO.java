package miaymoto.loja.business.dto.response;

public record ProdutoResponseDTO(Long id,
                                 String nome,
                                 String descricao,
                                 Double preco,
                                 String categoriaNome) {
}
