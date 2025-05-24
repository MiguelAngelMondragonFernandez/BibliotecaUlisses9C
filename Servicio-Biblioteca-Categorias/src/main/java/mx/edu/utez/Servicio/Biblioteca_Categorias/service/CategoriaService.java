package mx.edu.utez.Servicio.Biblioteca_Categorias.service;
import mx.edu.utez.Servicio.Biblioteca_Categorias.model.dao.CategoriaRepository;
import mx.edu.utez.Servicio.Biblioteca_Categorias.model.dto.CategoriaDTO;
import mx.edu.utez.Servicio.Biblioteca_Categorias.model.entity.Categoria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }


    @Transactional(readOnly = true)
    public ResponseEntity<?> findAll() {
        List<Categoria> categorias = repository.findAll();
        return ResponseEntity.ok(categorias);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> findById(Long id) {
        Optional<Categoria> found = repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
        }

        Categoria categoria = found.get();
        CategoriaDTO dto = new CategoriaDTO(
                categoria.isEstado(),
                categoria.getDescripcion(),
                categoria.getNombre(),
                categoria.getId_categoria()
        );
        return ResponseEntity.ok(dto);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> save(CategoriaDTO dto) {
        try {
            // Validaciones de nombre
            String nombre = dto.getNombre();
            if (nombre == null || nombre.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El nombre no puede estar vacío");
            }

            nombre = nombre.trim();

            if (!nombre.matches("^[A-Z][a-z]*(\\s[A-Z][a-z]*)*$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El nombre debe comenzar con mayúscula, sin caracteres especiales ni números");
            }

            // Validar que el nombre sea único
            if (repository.existsByNombreIgnoreCase(nombre)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Ya existe una categoría con ese nombre");
            }

            // Validaciones de descripción
            String descripcion = dto.getDescripcion();
            if (descripcion == null || descripcion.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La descripción no puede estar vacía");
            }

            if (descripcion.length() > 200) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La descripción no debe exceder los 200 caracteres");
            }

            // Crear entidad y guardar
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion.trim());
            categoria.setEstado(dto.isEstado());

            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(categoria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar la categoría");
        }
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<?> delete(Long id) {
        Optional<Categoria> found = repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoría no encontrada");
        }
        repository.delete(found.get());
        return ResponseEntity.ok("Categoría eliminada");
    }

}