package mx.edu.utez.Servicio.Biblioteca_Categorias.model.dto;

import lombok.*;
public class CategoriaDTO {


    private Long id_categoria;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public CategoriaDTO() {
    }

    public CategoriaDTO(boolean estado, String descripcion, String nombre, Long id_categoria) {
        this.estado = estado;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id_categoria = id_categoria;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
