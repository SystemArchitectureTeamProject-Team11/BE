package SA.team11.systemarchitecture.controller;

import SA.team11.systemarchitecture.Entity.Event;
import SA.team11.systemarchitecture.dto.EventCond;
import SA.team11.systemarchitecture.dto.EventOutput;
import SA.team11.systemarchitecture.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://210.119.237.42:5500")
public class EventController {
    private final EventService eventService;
    
    // 이벤트 목록 페이지.
    @GetMapping("/event")
    public List<EventOutput> list() {
        List<EventOutput> eventOutputList = eventService.getEventList();
        
        return eventOutputList;
    }
    
    // id 선택 시 세부사항 반환
    @GetMapping("/event/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
    
    @GetMapping("/event/search")//?start={start}&region={region}&category={category}
    public List<EventOutput> findEventBySearch(@ModelAttribute EventCond eventCond) {
    
        System.out.println("start = " + eventCond.getStart());
        System.out.println("region = " + eventCond.getRegion());
        System.out.println("category = " + eventCond.getCategory());
        
        return eventService.eventSearch(eventCond);
    }
    
}
