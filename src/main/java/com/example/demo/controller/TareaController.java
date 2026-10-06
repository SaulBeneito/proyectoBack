package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.TareaRequest;
import com.example.demo.controller.dto.TareaResponse;
import com.example.demo.controller.memoria.MemoriaProyecto;
import com.example.demo.controller.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final List<Tarea> tareas;
    private int siguienteId = 1;

    public TareaController(MemoriaProyecto memoria) {
        this.tareas = memoria.getTareas();
    }

    ///////////////////////////////
@GetMapping
    public List<TareaResponse> lista(
            @RequestParam(name = "completada", required = false) Boolean completada) {
        List<TareaResponse> resultado = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (completada == null || tarea.isCompletada() == completada) {
                resultado.add(TareaResponse.desde(tarea));
            }
        }
        return resultado;
    }

    @GetMapping("/{id}")
    public TareaResponse detalle(@PathVariable(name = "id") int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return TareaResponse.desde(tarea);
            }
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<TareaResponse> crear(
            @Valid @RequestBody TareaRequest peticion) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(peticion.getTitulo());
        tarea.setPrioridad(peticion.getPrioridad());
        tarea.setProyectoId(peticion.getProyectoId());
        tarea.setId(siguienteId);
        siguienteId = siguienteId + 1;
        tareas.add(tarea);
        return ResponseEntity.ok(TareaResponse.desde(tarea));
    }

    @PutMapping("/{id}")
    public Tarea actualizar(
            @PathVariable(name = "id") int id,
            @RequestBody Tarea datos) {

        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id) {
                datos.setId(id);
                tareas.set(i, datos);
                return datos;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable(name = "id") int id) {
        tareas.removeIf(tarea -> tarea.getId() == id);
    }

    @GetMapping("/diagnostico")
    public String diagnostico(
            @RequestHeader(name = "User-Agent") String cliente,
            @RequestHeader(name = "Accept") String acepta) {

        return "Me llama: " + cliente + "\nQuiere recibir: " + acepta;
    }

    @PostMapping("/espejo")
    public Tarea espejo(@RequestBody Tarea tarea) {
        System.out.println("He recibido: " + tarea.getTitulo()
                + " / " + tarea.getPrioridad()
                + " / completada=" + tarea.isCompletada());
        return tarea;
    }

}
