package io.tam.ssc1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "main.html";
    }

    @PostMapping("/test")
    @ResponseBody
//    @CrossOrigin("*") not recommended
    public String change() {

        // cors hanya mencegah response ke client tapi tidak mencegah access to endpoint
        // jadi kode yang ada disini akan tetap diesekusi tetapi tidak akan me return response ke client karena di block

        /*
        ini akan dieksekusi meskipun cors origin blocked
        System.out.println(":(");
        */

        return "TEST";
    }

}
