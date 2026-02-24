package miaymoto.loja.business;

import lombok.RequiredArgsConstructor;
import miaymoto.loja.business.converter.ConverterMapper;
import miaymoto.loja.business.dto.request.CategoriaRequestDTO;
import miaymoto.loja.business.dto.response.CategoriaResponseDTO;
import miaymoto.loja.infrastructure.entity.CategoriaEntity;
import miaymoto.loja.infrastructure.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    private final CategoriaRepository repository;
    private final ConverterMapper mapper;

    @Transactional
    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        // Converte DTO para Entity
        CategoriaEntity entity = mapper.toEntity(dto);
        // Salva no PostgreSQL
        entity = repository.save(entity);
        // Retorna o DTO de resposta
        return mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
        return mapper.toDto(entity);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        CategoriaEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        entity.setNome(dto.nome());
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}
