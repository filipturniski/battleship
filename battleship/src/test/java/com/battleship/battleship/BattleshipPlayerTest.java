package com.battleship.battleship;


import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BattleshipPlayerTest {

    @Autowired
    private MockMvc mockMvc;
    private WebApplicationContext context;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @DisplayName("/player/list")
     void addPlayer() throws Exception {
        //RequestBuilder request = MockMvcRequestBuilders.post("/player/list").
        this.mockMvc.perform(post("/player/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"name\": \"pperi0\",\n" +
                        "\t\"email\": \"pero.peric@ag04.com0\"\n" +
                        "}"))
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    void addDuplicatePlayer() throws Exception {
        //RequestBuilder request = MockMvcRequestBuilders.post("/player/list").
        this.mockMvc.perform(post("/player/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"name\": \"pperi0\",\n" +
                        "\t\"email\": \"pero.peric@ag04.com0\"\n" +
                        "}"))
                .andExpect(status().isConflict());
    }


    @Test
    void getPlayers() throws Exception {

        mockMvc.perform(get("/player/list")).
                andExpect(status().isOk());
    }

}