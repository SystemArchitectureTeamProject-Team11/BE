package SA.team11.systemarchitecture.InitialDB;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    
    @PostConstruct
    public void init() {
        initService.concertApi();
        initService.seoulApi();

    }
}
