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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.ProyectoRequest;
import com.example.demo.controller.memoria.MemoriaProyecto;
import com.example.demo.controller.model.Proyecto;
import com.example.demo.controller.model.Tarea;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private int siguienteId = 1;
    private final List<Proyecto> proyectos;
    private final List<Tarea> tareas;



    public ProyectoController(MemoriaProyecto memoria) {
    this.proyectos = memoria.getProyectos();
    this.tareas = memoria.getTareas();
}


//////////////////////////////////////
    //Si solo entras a /proyectos te devuelve una lista

    @GetMapping
    public List<Proyecto> lista(@RequestParam(name = "activo", required = false) Boolean activo) {
        List<Proyecto> resultado = new ArrayList<>();

        if (activo = false) {
            return  proyectos;
        }
            for (Proyecto proyecto : proyectos) {
                if (proyecto.isActivo() == activo) {
                    resultado.add(proyecto);
                }
            }
        

        return proyectos;
    }

    //Filtrar pot id
    @GetMapping("/{id}")
 public Proyecto detalle(@PathVariable(name = "id") int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }

    @GetMapping(params = "estado")
    public String listarEstado(@RequestParam boolean estado) {
        return "Lista del proyecto con estado activo ";
    }

    @PostMapping
    public Proyecto crear(@Valid @RequestBody ProyectoRequest peticion) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(peticion.getNombre());
        proyecto.setDescripcion(peticion.getDescripcion());
        proyecto.setActivo(peticion.isActivo());
        proyecto.setId(siguienteId);
        siguienteId = siguienteId + 1;
        proyectos.add(proyecto);
        return proyecto;
    }

    @GetMapping("/{id}/incidencias")
    public String incidenciasDeProyectos(@PathVariable int id) {
        return "Incidencias del proyecto " + id;
    }

    @PutMapping("/{id}")
    public Proyecto actualizar(
            @PathVariable(name = "id") int id,
            @Valid @RequestBody ProyectoRequest peticion) {

        for (int i = 0; i < proyectos.size(); i++) {
            if (proyectos.get(i).getId() == id) {
                Proyecto proyecto = proyectos.get(i);
                proyecto.setNombre(peticion.getNombre());
                proyecto.setDescripcion(peticion.getDescripcion());
                proyecto.setActivo(peticion.isActivo());
                proyectos.set(i, proyecto);
                return proyecto;
            }
        }
        return null;
    }


@DeleteMapping("/{id}")
public void eliminar(@PathVariable(name = "id") int id) {
    proyectos.removeIf(proyecto -> proyecto.getId() == id);
}

@GetMapping("/{id}/tareas")
public ResponseEntity<List<Tarea>> tareasDelProyecto(
        @PathVariable(name = "id") int id) {
    boolean existe = false;
    for (Proyecto proyecto : proyectos) {
        if (proyecto.getId() == id) {
            existe = true;
            break;
        }
    }
    if (!existe) {
        return ResponseEntity.notFound().build();
    }

    List<Tarea> resultado = new ArrayList<>();
    for (Tarea tarea : tareas) {
        if (tarea.getProyectoId() == id) {
            resultado.add(tarea);
        }
    }

    return ResponseEntity.ok(resultado);
}


}
