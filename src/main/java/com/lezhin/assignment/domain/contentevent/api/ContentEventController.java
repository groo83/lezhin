package com.lezhin.assignment.domain.contentevent.api;

import com.lezhin.assignment.common.dto.DataResponse;
import com.lezhin.assignment.domain.contentevent.ContentEvent;
import com.lezhin.assignment.domain.contentevent.dto.ContentEventReqDto;
import com.lezhin.assignment.domain.contentevent.dto.ContentEventResDto;
import com.lezhin.assignment.domain.contentevent.service.ContentEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content/event")
public class ContentEventController {

    private final ContentEventService contentEventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponse<ContentEventResDto> registerContentEvent(@Valid @RequestBody ContentEventReqDto requestDto) {
        ContentEventResDto registeredEvent = contentEventService.registerEvent(requestDto);
        return DataResponse.create(registeredEvent);
    }
}
