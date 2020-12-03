package ch.heigvd.amt.gamificator.api.rule;

import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.entities.Rule;

public class RuleMapper {

    public static Rule toEntity(RuleCreateCommand command){
        Rule rule = toEntityFromCondition(command.getCondition());
        return rule;
    }

    public static Rule toEntity(RuleUpdateCommand command, Long id) {
        Rule rule = toEntityFromCondition(command.getCondition());
        rule.setId(id);
        return rule;
    }

    private static Rule toEntityFromCondition(ConditionDTO conditionDTO){
        Rule rule = new Rule();
        rule.setCondition(conditionDTO.getType());
        return rule;
    }

    public static RuleDTO toDTO(Rule rule){
        RuleDTO ruleDTO = new RuleDTO();

        ruleDTO.setId(rule.getId());

        ConditionDTO conditionDTO = new ConditionDTO();
        conditionDTO.setType(rule.getCondition());
        ruleDTO.setCondition(conditionDTO);

        return ruleDTO;
    }
}
