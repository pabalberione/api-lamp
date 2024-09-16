package com.api_lamp.controller;

import com.api_lamp.entity.Lamp;
import com.api_lamp.service.LampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lamps")
public class LampController {

    @Autowired
    LampService lampService;

    //Crear una lampara
    @PostMapping
    public ResponseEntity<Lamp> createLamp(@RequestBody Lamp lamp){
        Lamp newLamp = lampService.createLamp(lamp);
        return ResponseEntity.ok(newLamp);
    }

    //Obtener lampara por Id
    @GetMapping("/{id}")
    public ResponseEntity<Lamp>getLampById(@PathVariable Long id){
        Optional<Lamp>lamp = lampService.getLampById(id);
        return lamp.map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    //Obtener todas las lamparas
    @GetMapping
    public ResponseEntity<List<Lamp>>getAllLamps(){
        List<Lamp>lamps = lampService.getAllLamps();
        return ResponseEntity.ok(lamps);
    }

    //Actualizar una lampara
    @PutMapping("/{id}")
    public ResponseEntity<Lamp>updateLamp(@PathVariable Long id, @RequestBody Lamp lampDetails){
        try{
            Lamp lamp = lampService.updateLamp(id, lampDetails);
            return ResponseEntity.ok(lamp);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar una lampara
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLamp(@PathVariable Long id){
        try{
            lampService.deletLamp(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
