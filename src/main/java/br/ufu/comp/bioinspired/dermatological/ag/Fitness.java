package br.ufu.comp.bioinspired.dermatological.ag;

import br.ufu.comp.bioinspired.Config;
import br.ufu.comp.bioinspired.Utils;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaClasses;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;
import br.ufu.comp.bioinspired.dermatological.dataset.DermatologicalDataset;

import java.util.LinkedList;

/**
 * This class calculates the generate rule fitness.
 * How it is calculated:
 * For each generated rule, this method will:
 * - iterate with all items in the given dataset and, for each dataset, we will count each occurrence of
 * - True Positive (tp): has the disease but does HAVE the same class
 * - False Positive (fp): has the disease but does NOT have the same class
 * - True Negative (tn): does NOT have disease and does NOT have the same class
 * - False Negative (fn): does NOT have disease and does HAVE the same class
 * This table will help us on identifying the items and understand better how these values are counted:
 * ______________________________________________________________
 * |                              |      Class is Present       |
 * --------------------------------------------------------------
 * |                              | Class Number| Not In class  |
 * --------------------------------------------------------------
 * | Class      | Class Number    |     TP      |       FP      |
 * | Predictive |  Not in Class   |     FN      |       TN      |
 * ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾
 * <p>
 * After all the parameters are properly counted, use the following formula to calculate the fitness:
 *    Se = TP / (TP + FN)
 *    Sp = TN / (TN + FP)
 *    Fitness = (Se * Sp)
 */
public abstract class Fitness {

    /**
     * This process can be slow as it perform 2 inner loops.
     * <p>
     * Only calculate the fitness for the genes wth weight < 0.3
     *
     * @param individuals
     * @return
     */
    public static Chromosome[] calculateFitness(Chromosome[] individuals, DermaClasses chromosomeClass, LinkedList<int[]> dataset) {
        for (Chromosome individual : individuals) {
            int tp = 0, fp = 0, tn = 0, fn = 0;

            boolean geneHasMatchedTheOperationalCondition = false;
            int datasetClass = 99;

            for (int j = 0; j < dataset.size(); j++) {
                datasetClass = dataset.get(j)[dataset.get(j).length - 1];
                // holds the number of times the gene has TP, which means generated gene matches the operational
                // condition and is in the same class as the dataset entry. At the end of the gene validation, if the
                // number of genes that matches the operational condition is greater than 0, then we have a True Positive;
                // for each chromosome, reset this counter.
                int tpCounter = 0;
                int fpCounter = 0;
                // holds the number of the valid genes that will be used to calculate the fitness
                // for each chromosome, reset this counter.
                int weightMatchCounter = 0;

                for (int i = 0; i < individual.genes().size(); i++) {
                    // TODO centralize through predicate?
                    if (individual.genes().get(i).weight() > Config.WEIGHT_LIMIT) {
                        geneHasMatchedTheOperationalCondition = false;
                        weightMatchCounter++;

                        // if the class in the dataset entry matches the chromosome class, then we have a match
                        // and we can calculate the True Positive (tp)
                        switch (individual.genes().get(i).operator()) {
                            case EQUAL:
                                if (dataset.get(j)[i] == individual.genes().get(i).domainValue()) {
                                    geneHasMatchedTheOperationalCondition = true;
                                }
                                break;

                            case NOT_EQUAL:
                                if (dataset.get(j)[i]  != individual.genes().get(i).domainValue()) {
                                    geneHasMatchedTheOperationalCondition = true;
                                }
                                break;

                            case LESS_THAN:
                                if (dataset.get(j)[i] < individual.genes().get(i).domainValue()  ) {
                                    geneHasMatchedTheOperationalCondition = true;
                                }
                                break;

                            case GREATER_EQUAL_THAN:
                                if (dataset.get(j)[i] >= individual.genes().get(i).domainValue()) {
                                    geneHasMatchedTheOperationalCondition = true;
                                }
                                break;

                            default:
                                geneHasMatchedTheOperationalCondition = false;
                                break;
                        }
                        // True positive, matches all the attributes and is in the same class
                        if ((chromosomeClass.getId() == datasetClass) && geneHasMatchedTheOperationalCondition) {
                            tpCounter++;

                        }
                        // False positive, matches all the attributes and is NOT in the same class
                        if ((chromosomeClass.getId() != datasetClass) && geneHasMatchedTheOperationalCondition) {
                            fpCounter++;
                        }
                    }
                }

                if ((tpCounter == weightMatchCounter) && (tpCounter != 0 && weightMatchCounter != 0)) {
                    // TRUE Positive
                    //all attributes matches, and the class matches ( tem a doença e a classe bateu)
                    tp++;

                } else if ((fpCounter == weightMatchCounter) && (fpCounter != 0 && weightMatchCounter != 0)) {
                    // all attributes matches, but the class does not match (tem a doença e a classe não bateu)
                    // FALSE Positive
                    fp++;
                    // only the class matches
                } else if ((chromosomeClass.getId() == datasetClass) && weightMatchCounter != 0) {
                    // only the class matches False Negative
                    fn++;
                } else if ((chromosomeClass.getId() != datasetClass) && weightMatchCounter != 0) {
                    // nothing matches False Negative True Negative
                    tn++;
                }
//                System.out.println(String.format("TP: %d, FP: %d, TN: %d, FN: %d", tp, fp, tn, fn));
//                System.out.println("==============================================");
            }
            // calculate the fitness.
            try {
                float sensitivity = (float) tp / (tp + fn);
                float specificity = (float) tn / (tn + fp);
                float fitness = Utils.round5Decimal((float) sensitivity * specificity);
                if (fitness == 0.0) {
                    fitness = 0.001f;
                }
                individual.setFitness(fitness);
            } catch (ArithmeticException e) {
                individual.setFitness(0.0001f);
            }
        }
        return individuals;
    }
}
