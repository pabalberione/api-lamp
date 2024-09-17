package com.api_lamp.controller;

import com.api_lamp.entity.Lamp;
import com.api_lamp.service.LampService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LampController.class)//Para simular soliictudes HTTP y validar respuestas
@ExtendWith(MockitoExtension.class)
public class LampControllerTestJson {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LampService lampService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Lamp lampCreate;
    private Lamp lampUpdate;
    private List<Lamp> lampList;


    @BeforeEach
    void setUp() throws Exception{
        //Cargar datos de los archivos .json
        lampCreate = objectMapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/lamp-create.json")), Lamp.class);
        lampUpdate = objectMapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/lamp-update.json")),Lamp.class);
        lampList = Arrays.asList(objectMapper.readValue(
                Files.readAllBytes(Paths.get("src/test/resources/lamp-list.json")),Lamp[].class));
    }

    @Test
    void testCreateLamp() throws Exception{
        Mockito.when(lampService.createLamp(any(Lamp.class))).thenReturn(lampCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/lamps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lampCreate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("Philips"))
                .andExpect(jsonPath("$.price").value(45.26))
                .andExpect(jsonPath("$.type").value("Incandescentes"))
                .andExpect(jsonPath("$.available").value("true"));
    }

    @Test
    void testGetLampById() throws Exception{
        Mockito.when(lampService.getLampById(anyLong())).thenReturn(Optional.of(lampCreate));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lamps/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("Philips"))
                .andExpect(jsonPath("$.price").value(45.26))
                .andExpect(jsonPath("$.type").value("Incandescentes"))
                .andExpect(jsonPath("$.available").value("true"));
    }

    @Test
    void testGetLampByIdNotFound() throws Exception{
        Mockito.when(lampService.getLampById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lamps/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllLamps() throws Exception{
        Mockito.when(lampService.getAllLamps()).thenReturn(lampList);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/lamps")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].brand").value("UMAGE"))
                .andExpect(jsonPath("$[0].price").value(333.33))
                .andExpect(jsonPath("$[0].type").value("Incandescentes"))
                .andExpect(jsonPath("$[0].available").value("false"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].brand").value("WIPRO"))
                .andExpect(jsonPath("$[1].price").value(433.93))
                .andExpect(jsonPath("$[1].type").value("Halogenas"))
                .andExpect(jsonPath("$[1].available").value("true"));
    }

    @Test
    void testUpdateLamp() throws Exception{
        Mockito.when(lampService.updateLamp(anyLong(), any(Lamp.class))).thenReturn(lampUpdate);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/lamps/1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lampUpdate)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("UMAGE"))
                .andExpect(jsonPath("$.price").value(333.33))
                .andExpect(jsonPath("$.type").value("Updateadas"))
                .andExpect(jsonPath("$.available").value("false"));
    }

    @Test
    void testDeleteLamp() throws Exception{
        Mockito.doNothing().when(lampService).deletLamp(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/lamps/1"))
                .andExpect(status().isNoContent());
    }
}
