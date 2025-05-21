package com.example.backprestamo.controller;

import com.example.backprestamo.kernel.ApiResponse;
import com.example.backprestamo.model.dto.PrestamoDTO;
import com.example.backprestamo.model.dto.PrestamoEntregaDTO;
import com.example.backprestamo.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/biblioteca/prestamo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PrestamoController {
    private final PrestamoService prestamoService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll() { return prestamoService.findAll();}

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) { return prestamoService.findById(id);}

    @PostMapping("/")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody PrestamoDTO prestamo) {
        return prestamoService.register(prestamo);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody PrestamoEntregaDTO prestamo) {
        try {
            prestamoService.update(prestamo);
            return new ResponseEntity<>(new ApiResponse(prestamo, HttpStatus.OK), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<ApiResponse> changeStatus(@Valid @PathVariable Long id) {
        prestamoService.changeStatus(id);
        return new ResponseEntity<>(new ApiResponse(id, HttpStatus.OK), HttpStatus.OK);
    }
}
