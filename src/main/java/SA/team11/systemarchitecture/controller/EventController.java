package SA.team11.systemarchitecture.controller;

import SA.team11.systemarchitecture.dto.EventCond;
import SA.team11.systemarchitecture.dto.EventOutput;
import SA.team11.systemarchitecture.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.PathVariable;
import java.time.LocalDate;
import java.time.format.DataTimeFormatter;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    
    @GetMapping("/event/search")//?start={start}&region={region}&category={category}
    public List<EventOutput> findEventBySearch(@ModelAttribute EventCond eventCond) {
    
        System.out.println("start = " + eventCond.getStart());
        System.out.println("region = " + eventCond.getRegion());
        System.out.println("category = " + eventCond.getCategory());
        
        return eventService.eventSearch(eventCond);
    }

    // 이벤트 목록 페이지.
    @GetMapping("/event")
    public String list(Model model) {
        List<EventOutput> eventOutputList = eventService.getEventList();
        model.addAttribute("postList", eventOutputList);
        return "event/list.html";
    }

    // id 선택 시 세부사항 반환
    @GetMapping("/event/{id}")
    public EventOutput getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        EventOutput eventoutput = EventOutput.builder()
                .id(event.getId())
                .title(event.getTitle())
                .place(event.getPlace())
                .period(formatPeriod(event.getStartDate(), event.getEndDate()))
                .poster(event.getPoster())
                .build();
        return eventOutput;
    }

    private String formatPeriod(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return startDate.format(formatter) + " - " + endDate.format(formatter);
    }
}
