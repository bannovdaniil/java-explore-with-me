package ru.practicum.ewm.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/events")
public class AdminEventsController {
    @GetMapping
    void findAll() {

    }
}
