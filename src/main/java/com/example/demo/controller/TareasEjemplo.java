package com.example.demo.controller;



import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.model.Tarea;

@RestController
@RequestMapping("/tareas")
public class TareasEjemplo {

    @GetMapping("/ejemplo")
public List<Tarea> lista() {
    return List.of(
        new Tarea(1, "Revisar el login", null, false),
        new Tarea(2, "Actualizar dependencias", "baja", true)
    );
    }
}

