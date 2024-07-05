package woojjam.utrip.common.exception;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import woojjam.utrip.controller.TestController;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ApiExceptionHandlerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	TestController testController;

	@Test
	public void testUserExceptionHandler() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/test/exception"))
			.andExpect(status().is4xxClientError())
			.andReturn();

		System.out.println("result = " + result);
		System.out.println(result.getResponse().getContentAsString());
	}

}