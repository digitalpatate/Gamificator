package ch.heigvd.amt.gamificator.api.rule;

import ch.heigvd.amt.gamificator.api.model.RuleDTO;
import ch.heigvd.amt.gamificator.api.model.RuleUpdateCommand;
import ch.heigvd.amt.gamificator.entities.Application;
import ch.heigvd.amt.gamificator.entities.Rule;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.RuleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.heigvd.amt.gamificator.api.model.RuleCreateCommand;

import java.util.LinkedList;
import java.util.List;


@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    private final ApplicationRepository applicationRepository;


    public long create(RuleCreateCommand ruleCreateCommand) throws RelatedObjectNotFound {

        Rule rule = Rule.toEntity(ruleCreateCommand);
        Application application = applicationRepository.findById(ruleCreateCommand.getApplicationId()).orElseThrow(() -> new RelatedObjectNotFound("Application"));

        rule.setApplication(application);

        rule = ruleRepository.save(rule);

        return rule.getId();
    }

    public void delete(Long id) {
        ruleRepository.deleteById(id);
    }

    public List<RuleDTO> getAllRules() {

        List<RuleDTO> rules = new LinkedList<>();
        for (Rule rule : ruleRepository.findAll()){
            rules.add(rule.toDTO());
        }

        return rules;

    }

    public RuleDTO update(Long id,RuleUpdateCommand command) throws NotFoundException {
        if(!ruleExists(id)){
            throw new NotFoundException("asd");
        }
        Rule rule = Rule.toEntity(command,id);

        rule = ruleRepository.save(rule);

        return rule.toDTO();
    }

    private boolean ruleExists(long id){
        return ruleRepository.findById(id).isPresent();
    }
}
