package SA.team11.systemarchitecture.repository;

import SA.team11.systemarchitecture.dto.EventCond;
import SA.team11.systemarchitecture.dto.EventOutput;

import java.util.List;

public interface EventRepositoryCustom {
    List<EventOutput> search(EventCond condition);
}
