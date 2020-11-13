package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.RuleDTO;
import ch.heigvd.amt.gamificator.api.model.RuleUpdateCommand;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String condition;
    private String then;

    @OneToOne
    private Application application;

    public static Rule toEntity(RuleCreateCommand command){
        Rule rule = new Rule();

        rule.setCondition(command.getCondition());
        rule.setThen(command.getThen());

        return rule;
    }

    public static Rule toEntity(RuleUpdateCommand command,Long _id) {
        Rule rule = new Rule();
        rule.setId(_id);
        rule.setCondition(command.getCondition());
        rule.setThen(command.getCondition());

        return rule;
    }

    public RuleDTO toDTO() {
        RuleDTO ruleDTO = new RuleDTO();

        ruleDTO.setId(id);
        ruleDTO.setIf(condition);
        ruleDTO.setThen(then);

        return ruleDTO;
    }
}
