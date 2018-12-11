package com.skillcorp.sejoframework.contracts.http;

import com.skillcorp.sejoframework.helpers.RuleValidator;

import java.util.HashMap;

public interface IRequestValidator {

    public HashMap<String, RuleValidator> getRulesPost();
    public HashMap<String, RuleValidator> getRulesGet();
}
