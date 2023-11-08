package SA.team11.systemarchitecture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCond {
    private String start;
    private String region;
    private String category;
}
