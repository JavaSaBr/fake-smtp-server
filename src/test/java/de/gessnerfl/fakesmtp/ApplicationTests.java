package de.gessnerfl.fakesmtp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("integrationtest")
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}
}
