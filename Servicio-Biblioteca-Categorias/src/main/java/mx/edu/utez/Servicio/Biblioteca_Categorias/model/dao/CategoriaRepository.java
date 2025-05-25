package mx.edu.utez.Servicio.Biblioteca_Categorias.model.dao;

import mx.edu.utez.Servicio.Biblioteca_Categorias.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Busca todas las categorías habilitadas
    List<Categoria> findByEstado( Boolean estado);

    // Busca por nombre
    Categoria findByNombre(String nombre);

    // Busca las categorías que contengan una parte del nombre
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);

}
