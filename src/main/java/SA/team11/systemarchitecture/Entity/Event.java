package SA.team11.systemarchitecture.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String title;
    LocalDate startDate;
    LocalDate endDate;
    String category;
    String region; /*지역. 도, 광역시, 서울 등*/
    String place; /* 행사 장소 */
    @Column(length =  1000)
    String introduction; /* 소개 */
    String performer; /* 출연진 */
    String poster; /* 포스터 이미지 경로 */
    
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
}
