package springbackendexemplo.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbackendexemplo.api.dto.StatusAppTO;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/status")
    public StatusAppTO testApplication() {
        StatusAppTO status = new StatusAppTO();
        status.setMsg("Status OK. Applicacao online.");

        return status;
    }

}
