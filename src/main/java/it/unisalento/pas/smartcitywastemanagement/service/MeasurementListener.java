package it.unisalento.pas.smartcitywastemanagement.service;

import it.unisalento.pas.smartcitywastemanagement.domain.MeasureWasteBin;
import it.unisalento.pas.smartcitywastemanagement.dto.WasteBinAlertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Component
public class MeasurementListener {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostConstruct
    public void watchMeasurements() {
        Flux<ChangeStreamEvent<MeasureWasteBin>> changes = reactiveMongoTemplate.changeStream(MeasureWasteBin.class)
                .watchCollection("measurements")
                .listen();

        changes.subscribe(change -> {
            MeasureWasteBin measurement = change.getBody();
            if (measurement != null) {
                // Aggiorna il fillingLevel nella collezione 'wasteContainers' per quell'ID specifico
                Query query = new Query(Criteria.where("_id").is(measurement.getIdBin()));
                Update update = new Update().set("fillingLevel", measurement.getFillingLevel());
                reactiveMongoTemplate.updateFirst(query, update, "wasteContainers").subscribe();

                if (measurement.getFillingLevel() > 0.8) {
                    System.out.println("Attenzione soglia superata: " + measurement.getIdBin());
                }
                    // Creazione di un DTO e impostazione dei dati
                WasteBinAlertDTO alertDTO = new WasteBinAlertDTO();
                alertDTO.setBinId(measurement.getIdBin());
                alertDTO.setFillingLevel(measurement.getFillingLevel());
                alertDTO.setTimestamp(measurement.getTimestamp());
                alertDTO.setIsAlarm(measurement.getFillingLevel() > 0.8);


                    // Invio del DTO tramite WebSocket
                simpMessagingTemplate.convertAndSend("/topic/alerts", alertDTO);

            }
        });
    }
}
