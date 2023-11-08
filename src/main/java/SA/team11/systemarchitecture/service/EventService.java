package SA.team11.systemarchitecture.service;

import SA.team11.systemarchitecture.Entity.Event;
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

    @Transactional
    public List<EventOutput> getEventList() {
        List<Event> eventList = eventRepository.findAll();
        List<EventOutput> eventOutputList = new ArrayList<>();

        for(Event event : eventList) {
            EventOutput eventOutput = EventOutput.builder()
                    .id(event.getId())
                    .title(event.getTitle())
                    .place(event.getPlace())
                    .period(formatPeriod(event.getStartDate(), event.getEndDate()))
                    .build();
            eventOutputList.add(eventOutput);
        }
        return eventOutputList;
    }

    private String formatPeriod(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return startDate.format(formatter) + " - " + endDate.format(formatter);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}
