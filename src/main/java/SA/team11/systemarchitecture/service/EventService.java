package SA.team11.systemarchitecture.service;

import SA.team11.systemarchitecture.dto.EventCond;
import SA.team11.systemarchitecture.dto.EventOutput;
import SA.team11.systemarchitecture.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    
    public List<EventOutput> eventSearch(EventCond condition) {
        return eventRepository.search(condition);
    }
    
}
