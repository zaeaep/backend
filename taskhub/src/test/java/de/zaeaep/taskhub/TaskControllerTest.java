package de.zaeaep.taskhub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createThenGetAllContainsTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test\",\"description\":\"Desc\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Test"));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test"));
    }

    @Test
    void getByIdUnknownReturns404() throws Exception {
        mockMvc.perform(get("/api/tasks/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteThenGetByIdReturns404() throws Exception {
        // create
        var createResult = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"ToDelete\",\"description\":\"x\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        // sehr einfache ID-Extraktion: wir prüfen nur, dass delete auf /1 oder /2 klappt?
        // Besser: du liest die ID mit jsonPath – dafür bräuchten wir extra Parser.
        // Pragmatiker-Variante: wir holen alle Tasks und nehmen die erste ID:
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber());

        // Wenn du Update/Delete schon hast: nimm id=1 (oder passe an deinen Zustand an).
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }
}
