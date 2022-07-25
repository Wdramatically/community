package life.majiang.community.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.invoke.MethodHandles;

@Controller
public class IndexController {

    Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @GetMapping("/")
    public String index() {
        LOGGER.info("Call index.");
        return "index";
    }
}
