package woojjam.utrip.controller;

import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.exception.UserException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/exception")
    public void throwException() {
        throw new UserException(StatusCode.DUPLICATE_EMAIL);
    }
}
