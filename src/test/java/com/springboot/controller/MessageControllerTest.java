package com.springboot.controller;


import com.springboot.model.Message;
import com.springboot.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MessageController.class)

public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;
    List<String> mockText = Arrays.asList("love");
    String exampleCourseJson = "{\"sender\":\"Yohanan\",\"recipient\":\"Brurya\",\"text\":\"love\"}";


    @Test
    public void retrieveMessageByRecipient() throws Exception {

        Mockito.when(
                messageService.retrieveMessageByRecipient(Mockito.anyString())).thenReturn(mockText);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost/ReceiveMessages?recipient=Brurya").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        List<String> expected = Arrays.asList("love");
        JSONAssert.assertEquals(String.valueOf(expected), result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void SendMessage() throws Exception {
        Message mockMessage = new Message("Yohanan", "Brurya", "love");
        Mockito.when(
                messageService.sendMessage(Mockito.any(Message.class))).thenReturn(mockMessage);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/SendMessage")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/SendMessage/Brurya",
                response.getHeader(HttpHeaders.LOCATION));

    }
}
