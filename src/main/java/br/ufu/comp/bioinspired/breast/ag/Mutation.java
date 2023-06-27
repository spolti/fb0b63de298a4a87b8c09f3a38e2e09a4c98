package br.ufu.comp.bioinspired.breast.ag;

import br.ufu.comp.bioinspired.Operator;
import br.ufu.comp.bioinspired.Utils;
import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;


import java.util.Random;

public class Mutation {
    private static Random rand = new Random();

    /**
     * mutate the given weight, the base value to add or subtract is 0.5f
     * If the new value is greater than 1.0f, it will be set to 1.0f and if it is less than 0.0f, it will be set to 0.0f
     *
     * @param weight
     * @return mutated weight
     */
    public static float mutateWeight(float weight) {
        float min = 0.1f;
        float max = 0.5f;
        float randomFloat = Utils.round1Decimal(rand.nextFloat() * (max - min) + min);
        // randomly select the sum or minus operation
        int sign = rand.nextBoolean() ? -1 : 1;
        // TODO put debug level
        // System.out.println(weight + " +  ("+sign+" * "+randomFloat+")");
        float mutatedWeith = Utils.round1Decimal(weight + (sign * randomFloat));
        if (mutatedWeith > 1.0f)
            return 1.0f;
        else if (mutatedWeith <= 0.0f)
            return 0.0f;
        else
            return mutatedWeith;
    }

    /**
     * mutate the given operator, need to confirm how to mutate, if is continuous or categorical
     *
     * @param operator
     * @return
     */
    public static Operator mutateOperator(Operator operator, BreastAttributes attribute) {
        int randOperator = 4;
        if (BreastAttributes.stringOperator.contains(attribute.getName())) {
            // allows only == or !=
            randOperator = 2;
        }
        int index = rand.nextInt(randOperator);
        while (operator.ordinal() == index) {
            index = rand.nextInt(randOperator);
        }
        return Operator.values()[index];
    }

    /**
     * generate a new randon value for the given attribute, respecting the domain values and special cases;
     *
     * @param attribute
     * @param value
     * @return mutated domain value for the given attribute
     */
    public static String mutateDomainValue(BreastAttributes attribute, String value) {
        String domainValue = Utils.generatedBreastDomainValue(attribute);
        while (domainValue == value) {
            domainValue = Utils.generatedBreastDomainValue(attribute);
        }
        return domainValue;
    }
}
