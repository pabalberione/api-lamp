package com.api_lamp.service;

import com.api_lamp.entity.Lamp;
import com.api_lamp.repository.LampRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LampServiceTest {

    @Mock
    LampRepository lampRepository;

    @InjectMocks
    private LampService lampService;

    @BeforeEach//Indica que se ejecutará antes de cada test
    void setUp(){
        MockitoAnnotations.openMocks(this);//Inicializa mocks y configuraciones necesarias antes de cada prueba
    }

    @Test
    void testGetLampById_Found(){
        //Configurar datos de la prueba
        Lamp lamp = new Lamp(1L, "Desk Lamp",35.26, "Bright light", true);
        when(lampRepository.findById(1L)).thenReturn(Optional.of(lamp));

        //Llamar al método a probar
        Optional<Lamp>result = lampService.getLampById(1L);

        //Verificar resultados
        assertTrue(result.isPresent());
        assertEquals("Desk Lamp", result.get().getBrand());
        verify(lampRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLampById_NotFound(){
        //Configurar mock para devolver un Optional vacio
        when(lampRepository.findById(1L)).thenReturn(Optional.empty());

        //Llamar al método a probar
        Optional<Lamp> result = lampService.getLampById(1L);

        //Verificar resultados
        assertFalse(result.isPresent());
        verify(lampRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveLamp(){
        //Configuracion de datos de prueba
        Lamp lamp = new Lamp(null, "Desk Lamp", 46.25, "Bright light",false);
        Lamp savedLamp = new Lamp(1L, "Desk Lamp", 46.25, "Bright light",false);
        when(lampRepository.save(lamp)).thenReturn(savedLamp);

        //Llamar al metodo a probar
        Lamp result = lampService.createLamp(lamp);

        //Verificar resultados
        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals("Desk Lamp", result.getBrand());
        verify(lampRepository, times(1)).save(lamp);
    }

    @Test
    void testDeleteLampNotFound(){
        //Configurar el mock para que findById devuelva un Optional vacio
        when(lampRepository.findById(1L)).thenReturn(Optional.empty());
        //Verificar que se lanza una RuntimeException cuando intentamos eliminar una lampara que no existe
        assertThrows(RuntimeException.class,()-> lampService.deletLamp(1L));
    }

    @Test
    void testDeleteLamp(){
        //Configurar el mock para que findById devuelva un Optional con una lámpara
        Lamp lamp = new Lamp();
        when(lampRepository.findById(1L)).thenReturn(Optional.of(lamp));

        //Ejecutar el método a probar
        lampService.deletLamp(1L);

        //Verificar que se haya llamado el método delete del repositorio exactamente una vez
        verify(lampRepository, times(1)).delete(lamp);
    }
}
