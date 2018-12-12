package com.skillcorp.sejoframework.helpers;

public class RuleValidator {

    public String type;
    public boolean required;
    public boolean nullable;

    public RuleValidator(String type)
    {
        this.type = type;
        this.required = true;
    }

    public RuleValidator(String type, boolean required)
    {
        this.type = type;
        this.required = required;
    }

    public RuleValidator(String type, boolean required, boolean nullable)
    {
        this.type = type;
        this.required = required;
        this.nullable = nullable;
    }

}