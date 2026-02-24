package miaymoto.loja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import miaymoto.loja.business.ProdutoService;
import miaymoto.loja.business.dto.request.ProdutoRequestDTO;
import miaymoto.loja.business.dto.response.ProdutoResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
// Define o agrupamento de endpoints no Swagger UI
@Tag(name = "Produtos", description = "Recursos para gerenciamento de Produtos e seus vínculos com Categorias")
public class ProdutoController {
    private final ProdutoService service;

    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cadastra um produto vinculado a uma categoria existente através do ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "ID da Categoria informado não encontrado")
    })
    public ResponseEntity<ProdutoResponseDTO> post(@RequestBody ProdutoRequestDTO dto) {
        // O Service cuidará da conversão via Mapper e busca da CategoriaEntity
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna todos os produtos com os nomes de suas categorias")
    public ResponseEntity<List<ProdutoResponseDTO>> getAll() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não localizado")
    })
    public ResponseEntity<ProdutoResponseDTO> getById(@Parameter(description = "ID do produto a ser pesquisado")
                                                      @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto ou Categoria não encontrados")
    })
    public ResponseEntity<ProdutoResponseDTO> put(@PathVariable Long id,
                                                  @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Remove permanentemente um produto do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletar(id);
        // Retorna 204 No Content para exclusões bem-sucedidas
        return ResponseEntity.noContent().build();
    }
}
