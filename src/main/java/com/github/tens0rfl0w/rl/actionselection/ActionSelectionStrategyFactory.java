package com.github.tens0rfl0w.rl.actionselection;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by xschen on 9/27/2015 0027.
 */
public class ActionSelectionStrategyFactory {
    public static ActionSelectionStrategy deserialize(String conf){
        String[] comps = conf.split(";");

        HashMap <String, String> attributes = new HashMap <>();
        for (String comp : comps) {
            String[] field = comp.split("=");
            if (field.length < 2) continue;
            String fieldname = field[0].trim();
            String fieldvalue = field[1].trim();

            attributes.put(fieldname, fieldvalue);
        }
        if (attributes.isEmpty()) {
            attributes.put("prototype", conf);
        }

        String prototype = attributes.get("prototype");
        if(prototype.equals(GreedyActionSelectionStrategy.class.getCanonicalName())){
            return new GreedyActionSelectionStrategy();
        } else if(prototype.equals(SoftMaxActionSelectionStrategy.class.getCanonicalName())){
            return new SoftMaxActionSelectionStrategy();
        } else if(prototype.equals(EpsilonGreedyActionSelectionStrategy.class.getCanonicalName())){
            return new EpsilonGreedyActionSelectionStrategy(attributes);
        } else if(prototype.equals(GibbsSoftMaxActionSelectionStrategy.class.getCanonicalName())){
            return new GibbsSoftMaxActionSelectionStrategy();
        }

        return null;
    }

    public static String serialize(ActionSelectionStrategy strategy){
        Map<String, String> attributes = strategy.getAttributes();
        attributes.put("prototype", strategy.getPrototype());

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : attributes.entrySet()){
            if(first){
                first = false;
            }
            else{
                sb.append(";");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
}
