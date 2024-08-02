package net.protsenko.dataanalyzerservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.protsenko.dataanalyzerservice.config.LocalDateTimeDeserializer;
import net.protsenko.dataanalyzerservice.model.Data;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaDataReceiverImpl implements KafkaDataReceiver {

    private final KafkaReceiver<String, Object> receiver;
    private final LocalDateTimeDeserializer deserializer;
    private final KafkaDataService dataService;

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        deserializer)
                .create();

        receiver.receive()
                .subscribe(r -> {
                    Data data = gson.fromJson(r.value().toString(), Data.class);
                    dataService.hande(data);
                    r.receiverOffset().acknowledge();
                });
    }

}
