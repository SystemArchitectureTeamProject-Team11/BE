package SA.team11.systemarchitecture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventCond {
    private LocalDate start;
    private String region;
    private String category;
}
