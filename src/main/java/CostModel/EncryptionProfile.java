package CostModel;

import ConfigurationParser.Provider;
import RelationProfileTreeBuilder.Relation;
import TreeStructure.BinaryTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncryptionProfile
{
    // Key = attribute, Value = List of Encryption supported
    private Map<String, List<String>> map;

    public EncryptionProfile()
    {
        map = new HashMap();
    }

    // Create a NEW copy of a EncryptionProfile
    public EncryptionProfile(EncryptionProfile profile)
    {
        this();
        this.map.putAll(profile.getMap());
    }

    public Map<String, List<String>> getMap()
    {
        return map;
    }

    // Assign the all the encryption types to all the attributes
    public void assignDefaultProfiles(BinaryTree<Relation> tree)
    {
        List<String> supportedEncryption = new ArrayList<>();

        supportedEncryption.add("aes");
        supportedEncryption.add("homomorphic");

        List<Relation> relations = tree.DFSVisit();

        // For every relation in the tree...
        for (int i=0; i<relations.size(); i++)
        {
            // If is the current operation is a LogicalRelation...
            if (relations.get(i).getOperation().equals("LogicalRelation"))
            {
                // For every attribute of the LogicalRelation...
                for (int j=0; j< relations.get(i).getAttributes().size(); j++)
                {
                    map.put(relations.get(i).getAttributes().get(j), supportedEncryption);
                }
            }
        }

    }

    // Update the EncryptionProfile of the current relation
    public void updateEncryptionProfile(Relation relation)
    {
        switch (relation.getOperator())
        {
            // <=, <, >=, >
            case "lessthanorequal":
            case "lessthan":
            case "greaterthanorequal":
            case "greaterthan":

                List<String> update = new ArrayList<>();
                update.add("homomorphic");

                for (int i=0; i<relation.getAttributes().size(); i++)
                {
                    if (map.containsKey(relation.getAttributes().get(i)))
                    {
                       // Replace the value with update encryption
                       map.put(relation.getAttributes().get(i), update);
                    }
                }
                break;
        }
    }

}
