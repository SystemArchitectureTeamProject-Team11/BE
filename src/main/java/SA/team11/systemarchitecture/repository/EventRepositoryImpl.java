package SA.team11.systemarchitecture.repository;

import SA.team11.systemarchitecture.dto.EventCond;
import SA.team11.systemarchitecture.dto.EventOutput;
import SA.team11.systemarchitecture.dto.QEventOutput;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static SA.team11.systemarchitecture.Entity.QEvent.*;
import static org.springframework.util.StringUtils.*;

public class EventRepositoryImpl implements EventRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    public EventRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    @Override
    public List<EventOutput> search(EventCond condition) {
        return queryFactory
                .select(new QEventOutput(
                        event.id,
                        event.title,
                        event.place,
                        event.startDate.stringValue().append("~").append(event.endDate.stringValue()),
                        event.isStart,
                        event.poster
                ))
                .from(event)
                .where(startEq(condition.getStart()),
                        regionEq(condition.getRegion()),
                        categoryEq(condition.getCategory()))
                .orderBy(event.startDate.asc())
                .fetch();
    }
    
    private BooleanExpression startEq(String isStart) {
        if (isStart != null) {
            return event.isStart.eq(isStart);
        }
        return null;
    }
    
    private BooleanExpression regionEq(String region) {
        if (hasText(region)) {
            return event.region.contains(region);
        }
        return null;
    }
    
    private BooleanExpression categoryEq(String category) {
        if (hasText(category)) {
            return event.category.contains(category);
        }
        return null;
    }
}
