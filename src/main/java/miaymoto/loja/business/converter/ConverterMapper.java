package miaymoto.loja.business.converter;

import miaymoto.loja.business.dto.request.CategoriaRequestDTO;
import miaymoto.loja.business.dto.request.ProdutoRequestDTO;
import miaymoto.loja.business.dto.response.CategoriaResponseDTO;
import miaymoto.loja.business.dto.response.ProdutoResponseDTO;
import miaymoto.loja.infrastructure.entity.CategoriaEntity;
import miaymoto.loja.infrastructure.entity.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConverterMapper {
    // Mapeamento de Categoria
    CategoriaResponseDTO toDto(CategoriaEntity entity);
    CategoriaEntity toEntity(CategoriaRequestDTO dto);

    // Mapeamento de Produto (Tratando o ID da categoria manualmente se necessário)
    @Mapping(source = "categoria.nome", target = "categoriaNome")
    ProdutoResponseDTO toDto(ProdutoEntity entity);

    @Mapping(target = "categoria", ignore = true) // Categoria será setada no Service
    ProdutoEntity toEntity(ProdutoRequestDTO dto);
}
