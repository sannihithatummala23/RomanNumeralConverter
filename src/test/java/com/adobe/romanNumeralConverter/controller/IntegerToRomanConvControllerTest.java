package com.adobe.romanNumeralConverter.controller;

import com.adobe.romanNumeralConverter.model.RomanNumeral;
import com.adobe.romanNumeralConverter.service.IntegerToRomanConverterService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
@RunWith(SpringJUnit4ClassRunner.class)
public class IntegerToRomanConvControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IntegerToRomanConverterService integerToRomanConverterService;

    @Test
    public void getConvertedRomanValTest() throws Exception {

        when(integerToRomanConverterService.convertIntegerToRoman(22)).
                thenReturn(RomanNumeral.builder().input("22").output("XXII").build());

        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=22"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.input", Matchers.is("22")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.output", Matchers.is("XXII")));

    }

    @Test
    public void getConvertedRomanValErrorTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral?query=s"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("Invalid Request. Input should be in the range of 1-3999 numbers"));

    }

    @Test
    public void getConvertedRomanValInvalidURITest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/romannumeral"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("URI is not supported. Please make a request to : http://localhost:8080/romannumeral?query={provide integer input here} for getting integer converted to Roman numeral"));

    }

}
