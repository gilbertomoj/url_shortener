package com.challenge.url_shortener.controller;

import com.challenge.url_shortener.model.Url;
import com.challenge.url_shortener.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UrlService urlService;

    @Autowired
    private ObjectMapper objectMapper;
    private Url url;

    @BeforeEach
    public void init(){
        url = Url.builder().urlOrigin("www.goossgle.com").build();
    }
    @Test
    public void UrlController_ShouldCreate_ReturnCreatedUrl() throws Exception{
        given(urlService.addUrl(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("http://localhost:8080/url/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void UrlController_ShouldListAllUrls() throws Exception{
        ResultActions response = mockMvc.perform(get("http://localhost:8080/url/list")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UrlController_ShouldReturnStatistics() throws Exception{
        ResultActions response = mockMvc.perform(get("http://localhost:8080/url/statistics")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UrlController_ShouldEditAnUrl() throws Exception{
        String urlId = "77a3fed2"; // Trocar por algum ID existente
        Url url = new Url();
        url.setUrlId(urlId);
        url.setUrlOrigin("www.google.com");

        when(urlService.updateUrl(url)).thenReturn(url);

        ResultActions response = mockMvc.perform(put("http://localhost:8080/url/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(url)));

        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void UrlController_ShouldDeleteAnUrl() throws Exception{
        String urlId = "77a3fed2"; // Trocar por algum ID existente
        when(urlService.deleteUrl(urlId)).thenReturn(String.valueOf(true));

        ResultActions response = mockMvc.perform(delete("http://localhost:8080/url/delete/"+urlId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UrlController_ShouldGetOneUrl() throws Exception{
        String urlId = "77a3fed2";
        ResultActions response = mockMvc.perform(get("http://localhost:8080/url/get/"+urlId)
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
