package SA.team11.systemarchitecture.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String title;
    LocalDate startDate;
    LocalDate endDate;
    @Column(columnDefinition = "TEXT")
    String category;
    @Column(columnDefinition = "TEXT")
    String region; /*지역. 도, 광역시, 서울 등*/
    @Column(columnDefinition = "TEXT")
    String place; /* 행사 장소 */
    @Column(columnDefinition = "TEXT")
    String introduction; /* 소개 */
    @Column(columnDefinition = "TEXT")
    String performer; /* 출연진 */
    @Column(columnDefinition = "TEXT")
    String poster; /* 포스터 이미지 경로 */
    String isStart;
    
    public Event() {
    
    }
    
    public Event(String title, LocalDate startDate, LocalDate endDate, String category, String region, String place, String introduction, String performer, String poster) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.region = region;
        this.place = place;
        this.introduction = introduction;
        this.performer = performer;
        this.poster = poster;
    }
    
    public Event(String title, LocalDate startDate, LocalDate endDate, String category, String region, String place, String introduction) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.region = region;
        this.place = place;
        this.introduction = introduction;
    }

    public void changeIsStart(String isStart) {
        this.isStart = isStart;
    }
}
