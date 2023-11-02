package com.bbsk.banchanshop.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SampleController.class)
public class SampleControllerTests {
	
	@Autowired
	MockMvc mvc;
	
	@DisplayName("컨트롤러 테스트")
	@Test
	public void controllerTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/test"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}
