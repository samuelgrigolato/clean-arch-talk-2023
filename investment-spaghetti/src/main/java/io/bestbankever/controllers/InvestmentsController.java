package io.bestbankever.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/investments")
class InvestmentsController {

    @GetMapping
    String test() {
        return "test";
    }

    @PostMapping("/{uuid}/redeem")
    void redeem(@PathVariable UUID uuid) {
    }

}
