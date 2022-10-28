package ru.practicum.ewm.endpoints.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.utils.AdminStatsClient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AdminStatsController {
    private final AdminStatsClient adminStatsClient;

    @GetMapping("/admin/stats")
    public ResponseEntity<Object> getHits(@NotNull @RequestParam(name = "start") String start,
                                          @NotNull @RequestParam(name = "end") String end,
                                          @Valid
                                          @RequestParam(name = "uris", defaultValue = "", required = false) List<String> uris,
                                          @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("getHits: {},{},{},{}", start, end, uris, unique);
        return adminStatsClient.getHits(start, end, uris, unique);
    }
}
