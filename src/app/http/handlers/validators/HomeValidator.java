package app.http.handlers.validators;

import com.skillcorp.sejoframework.contracts.http.IRequestValidator;
import com.skillcorp.sejoframework.helpers.RuleValidator;

import java.util.HashMap;

public class HomeValidator implements IRequestValidator {


    @Override
    public HashMap<String, RuleValidator> getRulesPost() {
        return null;
    }

    @Override
    public HashMap<String, RuleValidator> getRulesGet() {

        HashMap<String, RuleValidator> rules = new HashMap<String, RuleValidator>();

        rules.put("email", new RuleValidator("email"));
        rules.put("user_id", new RuleValidator("number"));

        return rules;
    }
}
