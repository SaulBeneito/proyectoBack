package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {
    @GetMapping("/hola")
    public String hola() {
        return "Hola, mundo. Te responde mi servidor.";
    }

    @GetMapping("/status")
    public String status() {
        return "Servidor en funcionamiento";
    }

    @GetMapping("/prestamos/resumen")
    public String resumen() {
        return "Esta aplicación gestionará los préstamos de material";
    }

    @GetMapping("/anyo")
    public int anyo() {
        return 2026;
    }
}