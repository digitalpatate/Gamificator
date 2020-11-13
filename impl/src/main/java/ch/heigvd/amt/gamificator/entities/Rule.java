package ch.heigvd.amt.gamificator.entities;

import ch.heigvd.amt.gamificator.api.model.*;
import com.google.gson.Gson;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String  condition;
    private String then;

    @OneToOne
    private Application application;

    public static Rule toEntity(RuleCreateCommand command){
        Rule rule = new Rule();
        Gson g = new Gson();


        rule.setCondition(g.toJson(command.getCondition()));
        rule.setThen(g.toJson(command.getThen()));

        return rule;
    }

    public static Rule toEntity(RuleUpdateCommand command,Long _id) {
        Rule rule = new Rule();
        rule.setId(_id);
        rule.setCondition(command.getCondition().toString());
        rule.setThen(command.getCondition().toString());

        return rule;
    }

    public RuleDTO toDTO() {
        Gson g = new Gson();
        RuleDTO ruleDTO = new RuleDTO();

        ruleDTO.setId(id);
        ruleDTO.setCondition(g.fromJson(condition,ConditionDTO.class));
        ruleDTO.setThen(g.fromJson(then, ActionDTO.class));

        return ruleDTO;
    }
}
