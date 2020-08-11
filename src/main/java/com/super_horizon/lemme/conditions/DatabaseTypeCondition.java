package com.super_horizon.lemme.conditions;

import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import java.util.*;

public class DatabaseTypeCondition implements Condition {
    
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        try {
            Map<String, Object> attributes = metadata.getAnnotationAttributes(DatabaseType.class.getName());
            String type = (String) attributes.get("value");
            String environmentType = conditionContext.getEnvironment().getProperty("environment.type");
            return (environmentType.equalsIgnoreCase(type));
        }
        catch (ClassCastException e) {
        }
        catch (NullPointerException e) {
        }
        return false;
    }
}