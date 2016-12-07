package CostModel;

import java.util.HashMap;

/**
 * Created by Giovanni on 24/11/2016.
 */
public class PlansMap
{
    private HashMap<Integer, Plan> plansMap;

    public PlansMap()
    {
        plansMap = new HashMap<>();
    }

    public void addPlan(Plan plan)
    {
        int hashRelation = plan.getRelation().hashCode();
        int hashProvider = plan.getAssignedProviders().get(plan.getAssignedProviders().size()-1).hashCode();
        // Master hash code (combining hashRelation and hashProvider)
        int hashCode = 1013 * (hashRelation) ^ 1009 * (hashProvider);

        if (plansMap.containsKey(hashCode))
        {
            if (plansMap.get(hashCode).getCost() > plan.getCost())
                plansMap.put(hashCode, plan);
        }
        else
            plansMap.put(hashCode, plan);

    }

    public HashMap<Integer, Plan> getPlansMap()
    {
        return plansMap;
    }

    @Override
    public String toString() {
        return plansMap.toString();
    }
}