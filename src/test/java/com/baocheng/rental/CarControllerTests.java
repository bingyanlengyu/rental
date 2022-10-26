package com.baocheng.rental;

import com.baocheng.rental.controller.CarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(classes = RentalApplication.class)
@AutoConfigureMockMvc
class CarControllerTests {
    private MockMvc mockMvc;
    @Autowired
    CarController carController;

    @BeforeEach
    void setMockMvc(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void testCarsGet() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/car/cars")
                .param("start","20221021").param("end", "20221021")
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    void testOrderCarPost() throws Exception {
        String body = "{\n" +
                "\t\"customerId\":1,\n" +
                "\t\"carId\":1,\n" +
                "\t\"start\":\"20221030\",\n" +
                "\t\"end\":\"20221030\"\n" +
                "}";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/car/order")
                .content(body)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
    }

    @Test
    void testReturnsPut() throws Exception {
        String body = "{\n" +
                "\t\"orderId\": 1\n" +
                "}";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/car/returns")
                .content(body)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mockMvc.perform(requestBuilder);
        resultActions.andDo(MockMvcResultHandlers.print()).andReturn();
    }

}
