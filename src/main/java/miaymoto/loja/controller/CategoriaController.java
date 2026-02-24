package miaymoto.loja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import miaymoto.loja.business.CategoriaService;
import miaymoto.loja.business.dto.request.CategoriaRequestDTO;
import miaymoto.loja.business.dto.response.CategoriaResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
// Tag do Swagger para agrupar os endpoints no dashboard
@Tag(name = "Categorias", description = "Gerenciamento de categorias dos produtos")
public class CategoriaController {
    private final CategoriaService service;

    @PostMapping
    @Operation(summary = "Cadastrar categoria", description = "Cria uma nova categoria no banco de dados")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    public ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody CategoriaRequestDTO dto) {
        // Retorna 201 Created com o corpo do objeto salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas", description = "Retorna uma lista de todas as categorias cadastradas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna uma única categoria baseada no ID informado")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {
        // Reutilizando lógica de busca que deve estar no Service
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir categoria", description = "Remove uma categoria. Cuidado: pode afetar produtos vinculados!")
    @ApiResponse(responseCode = "204", description = "Categoria removida")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
