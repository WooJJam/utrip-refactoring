package woojjam.utrip.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/exception")
	public void throwException() {
		// throw new UserException(UserErr.DUPLICATE_EMAIL);
	}
}
