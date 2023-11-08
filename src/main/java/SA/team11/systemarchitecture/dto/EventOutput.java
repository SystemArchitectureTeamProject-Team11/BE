package SA.team11.systemarchitecture.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
public class EventOutput {
    
    private Long id;
    private String title;
    private String place;
    private String period;
    private String isStart;
    private String poster;

    @Builder
    @QueryProjection
    public EventOutput(Long id, String title, String place, String period, String poster) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.period = period;
        this.poster = poster;
    }

    @QueryProjection
    public EventOutput(Long id, String title, String place, String period,String isStart, String poster) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.period = period;
        this.isStart = isStart;
        this.poster = poster;
    }
}
