package SA.team11.systemarchitecture.InitialDB;

import SA.team11.systemarchitecture.Entity.Event;
import SA.team11.systemarchitecture.repository.EventRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.XML;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class InitService {
    private final EventRepository eventRepository;
    
    public void concertApi() {
        String data;
        StringBuilder urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/pblprfr"); /*URL*/
        Event event;
        try {
            urlBuilder.append("?" + URLEncoder.encode("service", "UTF-8") +
                    "=a23b5f2b5d5f48ebbb42e822055cc99f"); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("stdate", "UTF-8") +
                    "=" + URLEncoder.encode(LocalDate.now().minusMonths(2).format(DateTimeFormatter.ofPattern("yyyyMMdd")).toString(), "UTF-8")); /*공연시작날짜*/
            urlBuilder.append("&" + URLEncoder.encode("eddate", "UTF-8") + "=" + URLEncoder.encode("20240331", "UTF-8")); /*공연종료일자*/
            urlBuilder.append("&" + URLEncoder.encode("cpage", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재페이지*/
            urlBuilder.append("&" + URLEncoder.encode("rows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*페이지당 목록 수*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = xmlJsonApi(urlBuilder);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        // "dbs" 및 "db" 필드에서 데이터 추출
        JsonObject dbs = jsonObject.getAsJsonObject("dbs");
        JsonArray dbArray = dbs.getAsJsonArray("db");
        
        // "prfpdfrom" 값을 추출 area fcltynm prfcast sty
        for (int i = 0; i < dbArray.size(); i++) {
            JsonObject dbObject = dbArray.get(i).getAsJsonObject();
            
            data = concertDetailApi(dbObject.get("mt20id").getAsString());
            Gson gson2 = new Gson();
            JsonObject jsonObject2 = gson2.fromJson(data, JsonObject.class);
            // "dbs" 및 "db" 필드에서 데이터 추출
            JsonObject dbs2 = jsonObject2.getAsJsonObject("dbs");
            JsonObject dbObject2 = dbs2.getAsJsonObject("db");
            
            if(dbObject.get("genrenm").getAsString().equals("대중음악")){
                event = new Event(dbObject.get("prfnm").getAsString(),
                        LocalDate.parse(dbObject.get("prfpdfrom").getAsString(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        LocalDate.parse(dbObject.get("prfpdto").getAsString(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                        "콘서트", dbObject2.get("area").getAsString(), dbObject2.get("fcltynm").getAsString(),
                        dbObject2.get("sty").getAsString(), dbObject2.get("prfcast").getAsString(), dbObject.get("poster").getAsString());
                if ((event.getStartDate().compareTo(LocalDate.now()) <= 0) && (event.getEndDate().compareTo(LocalDate.now()) >= 0)) {
                    event.changeIsStart("개최중");
                } else if (event.getStartDate().compareTo(LocalDate.now()) > 0) {
                    event.changeIsStart("개최 예정");
                }

                try {
                    eventRepository.save(event);
                } catch (Exception e) {
                    continue;
                }
            }
            event = new Event(dbObject.get("prfnm").getAsString(),
                    LocalDate.parse(dbObject.get("prfpdfrom").getAsString(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    LocalDate.parse(dbObject.get("prfpdto").getAsString(), DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    dbObject.get("genrenm").getAsString(), dbObject2.get("area").getAsString(), dbObject2.get("fcltynm").getAsString(),
                    dbObject2.get("sty").getAsString(), dbObject2.get("prfcast").getAsString(), dbObject.get("poster").getAsString());
            if ((event.getStartDate().compareTo(LocalDate.now()) <= 0) && (event.getEndDate().compareTo(LocalDate.now()) >= 0)) {
                event.changeIsStart("개최중");
            } else if (event.getStartDate().compareTo(LocalDate.now()) > 0) {
                event.changeIsStart("개최 예정");
            }
            try {
                eventRepository.save(event);
            } catch (Exception e) {
                continue;
            }
            
        }
    }
    
    public void seoulApi() {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/77627546666779743637486f746a79/json/culturalEventInfo/1/300/"); /*URL*/
        Event event;
        
        String data = jsonApi(urlBuilder);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(data, JsonObject.class);
        // "culturalEventInfo" 및 "row" 필드에서 데이터 추출
        JsonObject culturalEventInfo = jsonObject.getAsJsonObject("culturalEventInfo");
        JsonArray row = culturalEventInfo.getAsJsonArray("row");
    
        for (int i = 0; i < row.size(); i++) {
            JsonObject rowObject = row.get(i).getAsJsonObject();
            if(rowObject.get("CODENAME").getAsString().equals("기타") || rowObject.get("CODENAME").getAsString().contains("교육")) {
                continue;
            }
            String[] dates = rowObject.get("DATE").getAsString().split("~");
            event = new Event(rowObject.get("TITLE").getAsString(),
                    LocalDate.parse(dates[0], DateTimeFormatter.ISO_DATE), LocalDate.parse(dates[1], DateTimeFormatter.ISO_DATE),
                    rowObject.get("CODENAME").getAsString(), "서울", rowObject.get("PLACE").getAsString(),
                    rowObject.get("PROGRAM").getAsString(), rowObject.get("PLAYER").getAsString(), rowObject.get("MAIN_IMG").getAsString());
            if ((event.getStartDate().compareTo(LocalDate.now()) <= 0) && (event.getEndDate().compareTo(LocalDate.now()) >= 0)) {
                event.changeIsStart("개최중");
            } else if (event.getStartDate().compareTo(LocalDate.now()) > 0) {
                event.changeIsStart("개최 예정");
            }
            try {
                eventRepository.save(event);
            } catch (Exception e) {
                continue;
            }
        }
    }
    @Transactional
    public void deletePast() {
        eventRepository.deleteAllByIsStart(null);
    }
    @Transactional
    public void resetDB() {
        eventRepository.deleteAll();
    }
    
    private String concertDetailApi(String eventId) {
        StringBuilder urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/pblprfr/"); /*URL*/
        try {
            urlBuilder.append(URLEncoder.encode(eventId, "UTF-8")); /*Service Key*/
            urlBuilder.append("?" + URLEncoder.encode("service", "UTF-8") +
                    "=a23b5f2b5d5f48ebbb42e822055cc99f"); /*Service Key*/
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return xmlJsonApi(urlBuilder);
    }
    
    private String jsonApi(StringBuilder urlBuilder) {
        String data = null;
        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            data = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    private String xmlJsonApi(StringBuilder urlBuilder) {
        String data = null;
        JSONObject json;
        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            data = sb.toString();
            json = XML.toJSONObject(data);
            data = json.toString(4);
            data = data.replace("\\r", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
