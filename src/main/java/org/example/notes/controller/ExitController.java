package org.example.notes.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExitController {

    @PostMapping("/exit")
    public void exitApp() {
        System.out.println("Closing...");
        System.exit(0);
    }
}
