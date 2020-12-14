package com.project.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ParentRepository parentRepository;

    @MockBean
    ParentService parentService;

    @Test
    void getAllParents() throws Exception {
        List<Parent> parentList = new ArrayList<>();
        Address addressOfParent1 = new Address(
                "22th Street",
                "Boston",
                "Virginia",
                "1212");
        Parent parent1 = new Parent("User","Name",addressOfParent1);
        parentList.add(parent1);
        when(parentService.findAll()).thenReturn(parentList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/parent")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$",hasSize(1)))
                .andDo(print());
    }

    @Test
    void getParentById() throws Exception {
        Address addressOfParent1 = new Address(
                "22th Street",
                "Boston",
                "Virginia",
                "1212");
        Parent parent1 = new Parent("User","Name",addressOfParent1);
        when(parentService.findById(anyLong())).thenReturn(parent1);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/parent/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.firstName").value(parent1.firstName))
                .andDo(print());
    }

    @Test
    void successfullyCreateParent() throws Exception {
        Address addressOfParent1 = new Address(
                "22th Street",
                "Boston",
                "Virginia",
                "1212");
        Parent parent1 = new Parent("User","Name",addressOfParent1);
        when(parentService.save(any(Parent.class))).thenReturn(parent1);
        ObjectMapper objectMapper = new ObjectMapper();
        String parentJson = objectMapper.writeValueAsString(parent1);
        ResultActions resultActions = mockMvc.perform(
                post("/parent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parentJson)
        );

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("User"))
                .andExpect(jsonPath("$.lastName").value("Name"))
                .andExpect(jsonPath("$.address.city").value("Boston"));
    }

    @Test
    void updateParent() throws Exception {
        Address addressOfParent1 = new Address(
                "22th Street",
                "Boston",
                "Virginia",
                "1212");
        Parent parent1 = new Parent("User","Name",addressOfParent1);
        when(parentService.update(anyLong(),any(Parent.class))).thenReturn(parent1);
        ObjectMapper objectMapper = new ObjectMapper();
        String parentJson = objectMapper.writeValueAsString(parent1);

        ResultActions resultActions = mockMvc.perform(
                put("/parent/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(parentJson)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("User"))
                .andExpect(jsonPath("$.lastName").value("Name"))
                .andExpect(jsonPath("$.address.city").value("Boston"));
    }

    @Test
    void deleteParent() throws Exception {
        Address addressOfParent1 = new Address(
                "22th Street",
                "Boston",
                "Virginia",
                "1212");
        Parent parent1 = new Parent("User","Name",addressOfParent1);
        //when(parentService.delete(anyLong())).thenReturn();
        ResultActions resultActions = mockMvc.perform(
                delete("/parent/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        resultActions.andExpect(status().isOk());
    }
}
