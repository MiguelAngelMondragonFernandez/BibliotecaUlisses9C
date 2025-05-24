package mx.edu.utez.Servicio.Biblioteca_Categorias.controller;

import mx.edu.utez.Servicio.Biblioteca_Categorias.model.dto.CategoriaDTO;
import mx.edu.utez.Servicio.Biblioteca_Categorias.model.dto.RequestDto;
import mx.edu.utez.Servicio.Biblioteca_Categorias.service.CategoriaService;
import mx.edu.utez.Servicio.Biblioteca_Categorias.utils.CryptoUtils;
import mx.edu.utez.Servicio.Biblioteca_Categorias.utils.CustomResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CryptoUtils encript;
    private final CustomResponse customResponse;

    public CategoriaController(CategoriaService categoriaService, CryptoUtils encript, CustomResponse customResponse) {
        this.categoriaService = categoriaService;
        this.encript = encript;
        this.customResponse = customResponse;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAll() {
        try {
            Object categorias = categoriaService.findAll(); // método que devuelve lista de DTOs
            return customResponse.getOkResponse(categorias);
        } catch (Exception e) {
            return customResponse.get500Response("Error al obtener categorías", e.getMessage());
        }
    }
    @GetMapping("/{idEncrypted}")
    public ResponseEntity<?> getById(@PathVariable String idEncrypted) {
        try {
            // Desencriptar el ID recibido
            Optional<Long> id = encript.decryptValue(idEncrypted.replace('_', '/'), Long.class);

            if (id.isEmpty()) {
                return customResponse.get400Response(400, "ID inválido", "Bad Request");
            }

            // Obtener la respuesta del service (que devuelve un DTO)
            ResponseEntity<?> response = categoriaService.findById(id.get());

            // Extraer el DTO de la respuesta original si fue exitosa
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof CategoriaDTO) {
                CategoriaDTO dto = (CategoriaDTO) response.getBody();

                // Usar CustomResponse para cifrar el DTO y devolverlo
                return customResponse.getOkResponse(dto);
            }

            // Si no fue exitosa, devolver la respuesta original
            return response;

        } catch (Exception e) {
            return customResponse.get500Response("Error al obtener categoría", e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody RequestDto req) throws Exception {
        Optional<?> object = encript.decryptObject(req.getData(), CategoriaDTO.class);
        CategoriaDTO categoria;

        if (object.isPresent()) {
            categoria = (CategoriaDTO) object.get();
        } else {
            return customResponse.get400Response(400, "Ha ocurrido un error al enviar los datos", "Bad Request");
        }

        return categoriaService.save(categoria);
    }

    @DeleteMapping("/{idEncrypted}")
    public ResponseEntity<?> delete(@PathVariable String idEncrypted) {
        try {
            Optional<Long> id = encript.decryptValue(idEncrypted.replace('_', '/'), Long.class);
            if (id.isEmpty()) {
                return customResponse.get400Response(400, "ID inválido", "Bad Request");
            }
            return categoriaService.delete(id.get()); // devuelve CustomResponse desde el service
        } catch (Exception e) {
            return customResponse.get500Response("Error al eliminar categoría", e.getMessage());
        }
    }
}
