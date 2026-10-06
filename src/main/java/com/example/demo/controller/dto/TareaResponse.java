package com.example.demo.controller.dto;


import com.example.demo.controller.model.Tarea;

public record TareaResponse(
        int id,
        String titulo,
        String prioridad,
        boolean completada,
        int proyectoId) {

    public static TareaResponse desde(Tarea tarea) {
          if (tarea == null) {
            return null;
        }
        return new TareaResponse(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getPrioridad(),
                tarea.isCompletada(),
                tarea.getProyectoId()
        );
        }
}