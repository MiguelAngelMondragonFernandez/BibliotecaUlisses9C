package com.example.backprestamo.service;

import com.example.backprestamo.kernel.ApiResponse;
import com.example.backprestamo.model.dao.PrestamoRepository;
import com.example.backprestamo.model.dto.PrestamoDTO;
import com.example.backprestamo.model.dto.PrestamoEntregaDTO;
import com.example.backprestamo.model.entity.Prestamo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PrestamoService {
    private final PrestamoRepository repository;

    public PrestamoService(PrestamoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK), HttpStatus.OK );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findById(Long id){
        Optional<Prestamo> prestamo = repository.findById(id);
        if (prestamo.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "PrestamoNotFound"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ApiResponse(repository.findById(id), HttpStatus.OK), HttpStatus.OK );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> register(PrestamoDTO prestamodto){
        //Aqui se agrega lo del idLibro y idUsuario
        Prestamo prestamo = new Prestamo();
        prestamo.setStatus(prestamodto.getStatus());
        prestamo.setUsuarioId(prestamodto.getUsuarioId());
        prestamo.setLibroId(prestamodto.getLibroId());
        prestamo.setFechaSolicitud(prestamodto.getFechaSolicitud());
        prestamo.setFechaEntrega(prestamodto.getFechaEntrega());
        prestamo.setTipoPago(prestamodto.getTipoPago());
        Prestamo prestamoGuardado = repository.save(prestamo);
        return new ResponseEntity<>(new ApiResponse(prestamoGuardado, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> update(PrestamoEntregaDTO prestamodto){
        Prestamo prestamo = repository.findById(prestamodto.getId_prestamo()).orElseThrow(() -> new RuntimeException("PrestamoNotFound"));

        prestamo.setFechaEntrega(prestamodto.getFechaEntrega());
        repository.saveAndFlush(prestamo);
        return new ResponseEntity<>(new ApiResponse(prestamo, HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void changeStatus(Long id, Integer nuevoStatus){
        if (nuevoStatus < 0 || nuevoStatus > 2) {
            throw new IllegalArgumentException("Estado inválido, debe ser entre 0 y 2");
        }
        Prestamo prestamo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));

        prestamo.setStatus(nuevoStatus);
    }


}
