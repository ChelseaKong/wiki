package com.chelsea.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    /**
     * GET, POST, PUT, DELETE
     *
     * /user/1 : restful get user_id
     *
     * @RequestMapping : support all (GET, POST, PUT, DELETE)
     * @RequestMapping(value = "/hello", method = RequestMethod.GET)
     * @GetMapping : support GET
     * @PostMapping : support POST
     * @PutMapping : support PUT
     * @DeleteMapping : support DELETE
     */
    //@RequestMapping("/hello")
    @GetMapping ("/hello")
    // @PostMapping("/hello") status=405, request method not allowed
    public String hello() {
        return "Hello World!";
        //http://127.0.0.1:8880/hello
    }
}
