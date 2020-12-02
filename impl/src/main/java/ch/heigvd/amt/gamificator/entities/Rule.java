package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String condition;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long then;

    @OneToOne
    private Application application;

    public static Rule toEntity(RuleCreateCommand command){
        Rule rule = toEntityFromConditionAndAction(command.getCondition(), command.getThen());
        return rule;
    }

    public static Rule toEntity(RuleUpdateCommand command, Long _id) {
        Rule rule = toEntityFromConditionAndAction(command.getCondition(), command.getThen());
        rule.setId(_id);
        return rule;
    }

    private static Rule toEntityFromConditionAndAction(ConditionDTO conditionDTO, ActionDTO actionDTO){
        Rule rule = new Rule();
        rule.setCondition(conditionDTO.getType());
        return rule;
    }

    public RuleDTO toDTO() {
        RuleDTO ruleDTO = new RuleDTO();

        ruleDTO.setId(id);

        /*ConditionDTO conditionDTO = new ConditionDTO();
        conditionDTO.setType(condition);
        ruleDTO.setCondition(conditionDTO);

        ActionDTO actionDTO = new ActionDTO();
        //actionDTO.setAwardBadges(then.awardBadges);

        List<AwardPointDTO> listPointScaleDTO = new ArrayList<>();
        for (AwardPoint awardPoints : then.awardPoints){
            AwardPointDTO awardPointDTO = new AwardPointDTO();
            awardPointDTO.setPointScaleName(awardPoints.pointScale);
            awardPointDTO.setValue(awardPoints.points);
            listPointScaleDTO.add(awardPointDTO);
        }
        actionDTO.setAwardPoints(listPointScaleDTO);
        ruleDTO.setThen(actionDTO);*/

        return ruleDTO;
    }
}

