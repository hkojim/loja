package miaymoto.loja.business;

import lombok.RequiredArgsConstructor;
import miaymoto.loja.business.converter.ConverterMapper;
import miaymoto.loja.business.dto.request.ProdutoRequestDTO;
import miaymoto.loja.business.dto.response.ProdutoResponseDTO;
import miaymoto.loja.infrastructure.entity.CategoriaEntity;
import miaymoto.loja.infrastructure.entity.ProdutoEntity;
import miaymoto.loja.infrastructure.repository.CategoriaRepository;
import miaymoto.loja.infrastructure.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ConverterMapper mapper;

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        // 1. Busca a categoria obrigatória
        CategoriaEntity categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Erro: Categoria ID " + dto.categoriaId() + " não existe."));

        // 2. Mapeia DTO para Entity
        ProdutoEntity produto = mapper.toEntity(dto);

        // 3. Define o relacionamento
        produto.setCategoria(categoria);

        // 4. Salva e retorna o DTO mapeado (o Mapper extrai o nome da categoria para o ResponseDTO)
        return mapper.toDto(repository.save(produto));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        // Verifica se o produto existe
        ProdutoEntity produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se a nova categoria existe
        CategoriaEntity categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Atualiza campos manuais ou via mapper
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setCategoria(categoria);

        return mapper.toDto(repository.save(produto));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado para exclusão");
        }
        repository.deleteById(id);
    }
}
