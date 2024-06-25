package CodeIt.utrip.controller;

import CodeIt.utrip.common.reponse.StatusCode;
import CodeIt.utrip.common.exception.UserException;
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
