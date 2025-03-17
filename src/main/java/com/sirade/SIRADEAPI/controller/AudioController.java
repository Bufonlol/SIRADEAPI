package com.sirade.SIRADEAPI.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/audio")
@CrossOrigin(origins = "*")
public class AudioController {

    @PostMapping("/analizar")
    public ResponseEntity<?> analizarAudio(@RequestParam("file") MultipartFile file,
                                           @RequestParam("usuario") String usuario) {
        try {
            // Guardar el archivo temporalmente
            File audioFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(audioFile);

            // Ejecutar el script de Python
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "ruta/a/python-model/script.py", audioFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            process.waitFor();
            audioFile.delete();  // Eliminar el archivo temporal

            // Se espera que el script devuelva: "etiqueta,probabilidad"
            String[] salida = result.toString().split(",");
            String etiqueta = salida[0].trim();
            String probabilidad = salida[1].trim();

            // Crear reporte JSON
            Map<String, Object> reporte = new HashMap<>();
            reporte.put("nombre_usuario", usuario);
            reporte.put("fecha", LocalDateTime.now().toString());
            reporte.put("resultado", etiqueta);
            reporte.put("probabilidad", probabilidad);

            // Convertir a JSON utilizando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonReporte = objectMapper.writeValueAsString(reporte);

            return ResponseEntity.ok(jsonReporte);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el audio.");
        }
    }
}
