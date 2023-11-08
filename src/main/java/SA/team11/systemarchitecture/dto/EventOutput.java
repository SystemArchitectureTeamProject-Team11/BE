package SA.team11.systemarchitecture.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOutput {
    
    private Long id;
    private String title;
    private String place;
    private String period;
    private String open;
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
}
