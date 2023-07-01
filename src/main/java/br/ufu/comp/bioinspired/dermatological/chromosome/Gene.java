package br.ufu.comp.bioinspired.dermatological.chromosome;

import br.ufu.comp.bioinspired.Utils;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaAttributes;
import br.ufu.comp.bioinspired.Operator;

import java.util.Random;


public class Gene {

    private float weight;
    private Operator operator;
    private int domainValue;
    private int attributeID;

    /**
     *
     * @param attribute
     * @return
     */
    public Gene(DermaAttributes attribute) {
        Random r = new Random();

        // 1 decimal place, 0.1, 0.2, etc.
        float weight = Utils.round1Decimal(r.nextFloat());

        int domainValue = Utils.generatedDermatologicalDomainValue(attribute, false);

        int excludeLessThanOperator = operator.values().length;
        if (domainValue <= 0) {
            excludeLessThanOperator = 3;
        }
        Operator operator = Operator.values()[r.nextInt(excludeLessThanOperator)];

        this.weight = weight;
        this.operator = operator;
        this.domainValue = domainValue;
        this.attributeID = attribute.getId();
    }

    public Gene(Gene g) {
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

    public int domainValue() {
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

    public void setDomainValue(int domainValue) {
        this.domainValue = domainValue;
    }

    @Override
    public String toString() {
        return "Gene{" +
                "weight=" + weight +
                ", operator=" + operator +
                ", value=" + domainValue +
                '}';
    }
}
