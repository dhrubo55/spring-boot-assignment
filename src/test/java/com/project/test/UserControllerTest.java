package com.project.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ParentService parentService;

    @Test
    void getAllParents() throws Exception {
        List<Parent> parentList = new ArrayList<>();
        Address address = new Address(
                "23rd Street",
                "boston",
                "virgina",
                "1212"
        );
        parentList.add(new Parent("User","person1", address));
        when(parentService.findAll()).thenReturn(parentList);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/parent")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$",hasSize(1))).andDo(print());

    }
}
