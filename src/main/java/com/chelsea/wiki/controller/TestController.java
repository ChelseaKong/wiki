package com.chelsea.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${test.hello:TEST}") // prefer application.properties, if not, read TEST
    private String testHello;
    /**
     * GET, POST, PUT, DELETE
     *
     * /user/1 : restful get user_id
     *
     * @RequestMapping : support all (GET, POST, PUT, DELETE)
     * @RequestMapping(value = "/hello", method = RequestMethod.GET) : support GET
     * @GetMapping : support GET
     * @PostMapping : support POST
     * @PutMapping : support PUT
     * @DeleteMapping : support DELETE
     */

    // 03 add GET
    // @RequestMapping("/hello")
    @GetMapping ("/hello")
    public String hello() {
        return "Hello World!" + testHello;
        //http://127.0.0.1:8880/hello
        // Hello World!Hello
    }
    // @PostMapping("/hello") status=405, request method not allowed

    // 04 POST test
    @PostMapping("/hello/post")
    public String helloPost(String name) {
        return "Hello World! POST, " + name;
        //http://127.0.0.1:8880/hello
    }
}
