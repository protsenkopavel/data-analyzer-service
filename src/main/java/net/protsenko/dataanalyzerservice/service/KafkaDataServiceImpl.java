package net.protsenko.dataanalyzerservice.service;

import net.protsenko.dataanalyzerservice.model.Data;
import org.springframework.stereotype.Service;

@Service
public class KafkaDataServiceImpl implements KafkaDataService {

    @Override
    public void hande(Data data) {
        System.out.println("Data object is received: " + data.toString());
    }

}
