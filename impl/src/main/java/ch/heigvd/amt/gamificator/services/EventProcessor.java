package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.ActionDTO;
import ch.heigvd.amt.gamificator.api.model.ConditionDTO;
import ch.heigvd.amt.gamificator.entities.Event;
import ch.heigvd.amt.gamificator.entities.Rule;
import ch.heigvd.amt.gamificator.repositories.RuleRepository;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class EventProcessor {

    private RestTemplate restTemplate;

    private final RuleRepository ruleRepository;

    public EventProcessor(RuleRepository ruleRepository, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.ruleRepository = ruleRepository;
    }

    public void process(Event event) {
        Gson g  = new Gson();


        List<Rule> rules = (List<Rule>) ruleRepository.findAll();

        rules = rules.stream().filter(rule ->  {
            ConditionDTO conditionDTO = g.fromJson(rule.getCondition(),ConditionDTO.class);

            return conditionDTO.getType().equals(event.getType());
        }).collect(Collectors.toList());



        for (Rule rule: rules) {
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
        try {

            restTemplate.exchange(String.format("http://localhost:8080%s",actionDTO.getPath()),method,entity, (Class<Object>) null);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
