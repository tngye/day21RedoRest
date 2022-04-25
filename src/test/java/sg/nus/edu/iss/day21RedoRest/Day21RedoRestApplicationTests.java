package sg.nus.edu.iss.day21RedoRest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import sg.nus.edu.iss.day21RedoRest.Repository.GameRepository;
import sg.nus.edu.iss.day21RedoRest.model.Comment;

@SpringBootTest
@AutoConfigureMockMvc
class Day21RedoRestApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	private GameRepository gRepo;


	@Test
	void contextLoads() {
	}

	@Test
	void ShouldReturn200(){

		RequestBuilder req = MockMvcRequestBuilders.get("/game/30")
			.accept(MediaType.APPLICATION_JSON_VALUE);
		
			MvcResult result = null;
			try {
				result = mvc.perform(req).andReturn();
			} catch (Exception ex) {
				fail("cannot perform mvc invocation", ex);
				return;
			}
	
			MockHttpServletResponse resp = result.getResponse();
			String payload;
			try {
				// JSON
				payload = resp.getContentAsString();
				assertNotNull(payload);
			} catch (Exception ex) {
				fail("cannot retrieve response payload", ex);
				return;
			}	
	}

	@Test
	void ShouldReturn11Comments(){
		int count = 11;
		List<Comment> list = gRepo.getCommentById(30);
		assertEquals(count, list.size(), "Expected %s comments. Got %s.".formatted(count, list.size()));

	}

}
