package TIAB.timebox;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeboxApplicationTests {
	@Test
	@DisplayName("Main Class Test")
	void contextLoads() {
		TimeboxApplication.main(new String[]{});
	}

}
