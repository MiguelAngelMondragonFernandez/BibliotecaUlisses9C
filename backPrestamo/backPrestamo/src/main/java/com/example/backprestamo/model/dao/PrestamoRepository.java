package com.example.backprestamo.model.dao;

import com.example.backprestamo.model.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
