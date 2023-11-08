package SA.team11.systemarchitecture.repository;

import SA.team11.systemarchitecture.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> , EventRepositoryCustom{
    void deleteAllByIsStart(String isStart);
    List<Event> findTop100ByOrderByStartDateAsc();
}
