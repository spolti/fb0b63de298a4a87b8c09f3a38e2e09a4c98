package br.ufu.comp.bioinspired;

import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;
import br.ufu.comp.bioinspired.breast.attributes.BreastClasses;
import br.ufu.comp.bioinspired.breast.chromosome.BreastChromosome;
import br.ufu.comp.bioinspired.breast.chromosome.BreastGene;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaAttributes;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaClasses;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;
import br.ufu.comp.bioinspired.dermatological.chromosome.Gene;

import java.util.Random;

public abstract class Utils {

    private static Random r = new Random();

    public static float round1Decimal(float v) {
        return Math.round(v * 10) / 10f;
    }

    public static float round5Decimal(float v) {
        return Math.round(v * 10000) / 10000f;
    }

    /**
     * @param min
     * @param max
     * @return a randon number between the range
     */
    public static int randomBetween(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    /**
     * define how many children will be generated, as the crossover probability is 100%, all parents will generate 2
     * children, which means that the number of children will be the same as the number of parents
     *
     * @param size
     * @param percentage
     * @return
     */
    public static int rafflePopulation(int size, double percentage) {
        int sizeOfPopulation = Math.toIntExact(Math.round(size * percentage));
        //System.out.println("Number of the population sample(s):  " + sizeOfPopulation);
        return sizeOfPopulation;
    }

    /**
     * Generate a random value for all attributes, respecting the domain values and special cases;
     *
     * @param attribute
     * @return random value based on its domain
     */
    public static int generatedDermatologicalDomainValue(DermaAttributes attribute, boolean greaterThanZero) {
        int domainValue;
        // there are 2 special fields
        switch (attribute) {
            case AGE:
                domainValue = r.nextInt(100);
                while (domainValue == 0) {
                    domainValue = r.nextInt(100);
                }
                break;

            case FAMILY_HISTORY:
                domainValue = r.nextInt(2);
                break;

            default:
                domainValue = r.nextInt(4);
                if (greaterThanZero) {
                    domainValue = randomBetween(1, 4);
                }
                break;

        }
        return domainValue;
    }

    /**
     * Generate a random value for all attributes, respecting the domain values and special cases;
     *
     * @param attribute
     * @return
     */
    public static String generatedBreastDomainValue(BreastAttributes attribute) {
        String domainValue;
        switch (attribute) {
            case AGE:
                domainValue = BreastAttributes.age.get(r.nextInt(9));
                break;

            case MENOPAUSE:
                domainValue = BreastAttributes.menopause.get(r.nextInt(3));
                break;

            case TUMOR_SIZE:
                domainValue = BreastAttributes.tumorSize.get(r.nextInt(12));
                break;

            case INV_NODES:
                domainValue = BreastAttributes.invNodes.get(r.nextInt(13));
                break;

            case NODE_CAPS:
            case IRRADIATE:
                domainValue = BreastAttributes.yesNo.get(r.nextInt(2));
                break;

            case DEG_MALIG:
                domainValue = "" + r.nextInt(4);
                while (domainValue.equals("0")) {
                    domainValue = "" + r.nextInt(4);
                }
                break;

            case BREAST:
                domainValue = BreastAttributes.breast.get(r.nextInt(2));
                break;

            case BREAST_QUAD:
                domainValue = BreastAttributes.breastQuad.get(r.nextInt(5));
                break;

            default:
                throw new IllegalArgumentException("Attribute not valid " + attribute.getName());

        }
        return domainValue;
    }

    /**
     * pre-format the rule
     * TODO print all formatted
     *
     * @param chromosome
     * @param dc
     * @param breastChromosome
     * @param breastClasses
     * @param trainFit
     * @param testFit
     */
    public static void formatResult(Chromosome chromosome, DermaClasses dc, BreastChromosome breastChromosome,
                                    BreastClasses breastClasses, float trainFit, float testFit) {
        StringBuilder b = new StringBuilder();
        String rule = "IF ";

        if (chromosome != null) {
            for (Gene c : chromosome.genes()) {
                // TODO centralize through predicate?
                if (c.weight() > Config.WEIGHT_LIMIT) {
                    if (c.operator() == Operator.GREATER_EQUAL_THAN && c.domainValue() == 0) {
                        //System.out.println("Filtering it out.... >= 0");
                    } else {
                        rule += "(" + DermaAttributes.getByID(c.attributeID()).getName() + ") " + c.operator().getOperator() + " " + c.domainValue() + " AND ";
                    }
                }
            }
            b.append(String.format("%s - %s - %s", dc.getName(), rule, trainFit + " - " + testFit));
        }
        if (breastChromosome != null) {
            for (BreastGene c : breastChromosome.genes()) {
                if (c != null) {
                    // TODO centralize using predicate?
                    if (c.weight() > Config.WEIGHT_LIMIT) {
                        if (c.operator() == Operator.GREATER_EQUAL_THAN && c.domainValue().equals("0")) {
                            //System.out.println("Filtering it out.... >= 0");
                        } else {
                            rule += "(" + BreastAttributes.getByID(c.attributeID()).getName() + ") " + c.operator().getOperator() + " " + c.domainValue() + " AND";
                        }
                    }
                }
            }
            b.append(String.format("%s - %s - %s \n", breastClasses.getName(), rule, trainFit + " - " + testFit));
        }
        System.out.println(b);
    }

}