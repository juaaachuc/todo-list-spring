package com.juaachuc.todo_list.controller;

import com.juaachuc.todo_list.model.Tarea;
import com.juaachuc.todo_list.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<Tarea> obtenerTodas() {
        return tareaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id) {
        Optional<Tarea> tarea = tareaService.obtenerPorId(id);
        return tarea.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarea crear(@RequestBody Tarea tarea) {
        return tareaService.guardar(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea tareaActualizada) {
        try {
            Tarea tarea = tareaService.actualizar(id, tareaActualizada);
            return ResponseEntity.ok(tarea);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tarea> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        try {
            Tarea tarea = tareaService.actualizarParcial(id, campos);
            return ResponseEntity.ok(tarea);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (tareaService.obtenerPorId(id).isPresent()) {
            tareaService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
