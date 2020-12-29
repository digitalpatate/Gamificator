package ch.heigvd.amt.gamificator.api.rule;

import ch.heigvd.amt.gamificator.api.RulesApi;
import ch.heigvd.amt.gamificator.api.model.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.RuleDTO;
import ch.heigvd.amt.gamificator.api.model.RuleUpdateCommand;
import ch.heigvd.amt.gamificator.exceptions.NotAuthorizedException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@Log
public class RuleController implements RulesApi {

    @Autowired
    private SecurityContextService securityContextService;

    private final RuleService ruleService;


    @Override
    public ResponseEntity<RuleDTO> createRule(@Valid RuleCreateCommand ruleCreateCommand) {
        Long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();
        RuleDTO ruleDTO;

        try {
            ruleDTO = ruleService.create(ruleCreateCommand, applicationId);
        } catch (RelatedObjectNotFound e) {
            return new ResponseEntity(e.getMessage(), e.getCode());

        }
        return new ResponseEntity(ruleDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteRule(Long id) {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();
        try {
            ruleService.delete(id, applicationId);
        } catch (NotFoundException | NotAuthorizedException e) {
            return new ResponseEntity(e.getMessage(), e.getCode());
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<RuleDTO>> getAllRules() {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();
        return new ResponseEntity(ruleService.getAllRules(applicationId),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateRule(Long id, @Valid RuleUpdateCommand ruleUpdateCommand) {

        try {
            RuleDTO ruleDTO = ruleService.update(id, ruleUpdateCommand);
            return new ResponseEntity(ruleDTO,HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity(e.getMessage(), e.getCode());
        }
    }
}
