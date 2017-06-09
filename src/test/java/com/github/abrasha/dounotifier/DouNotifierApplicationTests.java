package com.github.abrasha.dounotifier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"MAIL_USERNAME=mockedusername", "MAIL_PASSWORD=mockedpassword", "MAIL_RECIPIENTS=rec1@example.com,rec2@example.com"})
public class DouNotifierApplicationTests {

	@Test
	public void contextLoads() {
	}

}
