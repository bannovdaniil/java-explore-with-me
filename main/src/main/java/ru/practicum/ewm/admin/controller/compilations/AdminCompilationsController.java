package ru.practicum.ewm.admin.controller.compilations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {
    @GetMapping
    void findAll() {

    }
}
