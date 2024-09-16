package com.api_lamp.service;

import com.api_lamp.entity.Lamp;
import com.api_lamp.repository.LampRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LampService {

    @Autowired
    LampRepository lampRepository;

    //Crear una nueva lampara
    public Lamp createLamp(Lamp lamp){
        return lampRepository.save(lamp);
    }

    //Obtener una lampara por Id
    public Optional<Lamp> getLampById(Long id){
        return lampRepository.findById(id);
    }

    //Obtener todas las lamparas
    public List<Lamp>getAllLamps(){
        return lampRepository.findAll();
    }


    //Modificar una lampara por id
    public Lamp updateLamp(Long id, Lamp lampDetails){
        Lamp lamp = lampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lampara no encontrada"));
        lamp.setType(lampDetails.getType());
        lamp.setPrice(lampDetails.getPrice());
        lamp.setBrand(lampDetails.getBrand());
        lamp.setAvailable(lampDetails.isAvailable());
        return lampRepository.save(lamp);
    }

    //Eliminar una lampara por id
    public void deletLamp(Long id){
        Lamp lamp = lampRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Lampara no encontrada"));
        lampRepository.delete(lamp);
    }
}
