package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.ActionDTO;
import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.Rule;
import ch.heigvd.amt.gamificator.repositories.RuleRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class EventProcessor {

    private RestTemplate restTemplate;

    private final RuleRepository ruleRepository;

    public EventProcessor(RuleRepository ruleRepository, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri("http://localhost:8080")
                .build();
        this.ruleRepository = ruleRepository;
    }

    public void process(Event event) {

        Iterable<Rule> matchedRules = ruleRepository.findAllByEventType(event.getType());

        for (Rule rule: matchedRules) {
            Gson g  = new Gson();
            ActionDTO actionDTO = g.fromJson(rule.getThen(),ActionDTO.class);
            proccessAction(actionDTO);
        }
    }

    private void proccessAction(ActionDTO actionDTO){

        HttpMethod method = HttpMethod.resolve(actionDTO.getMethode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Object> entity = new HttpEntity(actionDTO.getPayload(),headers);
        restTemplate.exchange(String.format("localhost:9090%s",actionDTO.getPath()),method,entity, (Class<Object>) null);
    }
}
