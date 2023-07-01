package br.ufu.comp.bioinspired.breast.chromosome;

import br.ufu.comp.bioinspired.Operator;
import br.ufu.comp.bioinspired.Utils;
import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;

import java.util.Random;


public class BreastGene {

    private float weight;
    private Operator operator;
    private String domainValue;
    private int attributeID;

    /**
     *
     * @param attribute
     * @return
     */
    public BreastGene(BreastAttributes attribute) {
        Random r = new Random();

        // 1 decimal place, 0.1, 0.2, etc.
        float weight = Utils.round1Decimal(r.nextFloat());

        String domainValue = Utils.generatedBreastDomainValue(attribute);

        int randOperator = 4;
        Operator operator = null;
        boolean proceed = false;
        while (!proceed) {
            if (BreastAttributes.stringOperator.contains(attribute.getName())) {
                // allows only == or != for categorical attributes
                randOperator = 2;
            }
            operator = Operator.values()[r.nextInt(randOperator)];
                if (operator == Operator.LESS_THAN && domainValue.equals("0")) {
                proceed = false;
            } else {
                proceed = true;
            }
        }

        this.weight = weight;
        this.operator = operator;
        this.domainValue = domainValue;
        this.attributeID = attribute.getId();
    }

    public BreastGene(BreastGene g) {
        this.weight = g.weight();
        this.operator = g.operator();
        this.domainValue = g.domainValue();
        this.attributeID = g.attributeID();
    }

    public float weight() {
        return weight;
    }

    public Operator operator() {
        return operator;
    }

    public String domainValue() {
        return domainValue;
    }

    public int attributeID() {
        return attributeID;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setDomainValue(String domainValue) {
        this.domainValue = domainValue;
    }

    @Override
    public String toString() {
        return "BreastGene{" +
                "weight=" + weight +
                ", operator=" + operator +
                ", value=" + domainValue +
                '}';
    }
}
