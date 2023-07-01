package br.ufu.comp.bioinspired;

import br.ufu.comp.bioinspired.breast.ag.BreastFitness;
import br.ufu.comp.bioinspired.breast.ag.BreastPopulation;
import br.ufu.comp.bioinspired.breast.attributes.BreastClasses;
import br.ufu.comp.bioinspired.breast.chromosome.BreastChromosome;
import br.ufu.comp.bioinspired.breast.dataset.BreastDataset;

public abstract class RunBreast {

    private static BreastDataset breastDataset = new BreastDataset();

    public static void execute (int populationSize, int chromosomeSize, int generations, int turns) {

        BreastPopulation bestClass1 = new BreastPopulation();
        float class1Fitness = 0.0f;

        BreastPopulation bestClass2 = new BreastPopulation();
        float class2Fitness = 0.0f;
        for (int i = 0; i < turns; i++) {
            // Breast
            BreastPopulation breastClass1 = new BreastPopulation(populationSize, chromosomeSize, generations, BreastClasses.fromId(1), breastDataset.getTrainingDataset());
            if (breastClass1.getIndividuals()[0].getFitness() > class1Fitness) {
                class1Fitness = breastClass1.getIndividuals()[0].getFitness();
                bestClass1 = breastClass1;
            }

            BreastPopulation breastClass2 = new BreastPopulation(populationSize, chromosomeSize, generations, BreastClasses.fromId(2), breastDataset.getTrainingDataset());
            if (breastClass2.getIndividuals()[0].getFitness() > class2Fitness) {
                class2Fitness = breastClass2.getIndividuals()[0].getFitness();
                bestClass2 = breastClass2;
            }

        }
        // Breast class 1
        BreastChromosome[] bestc1 = new BreastChromosome[1];
        bestc1[0] = bestClass1.getIndividuals()[0];
        float fitnessFirstc1 = bestClass1.getIndividuals()[0].getFitness();
        float testFitc1 = BreastFitness.calculateFitness(bestc1, BreastClasses.fromId(1), breastDataset.testDataset())[0].getFitness();
        bestClass1.getIndividuals()[0].setFitness(fitnessFirstc1);
        bestClass1.getBest(testFitc1);
        Utils.formatResult(null, null, bestClass1.getIndividuals()[0],
                bestClass1.getBreastClass(), bestClass1.getIndividuals()[0].getFitness(), testFitc1);

        // Breast class 2
        BreastChromosome[] bestc2 = new BreastChromosome[1];
        bestc2[0] = bestClass2.getIndividuals()[0];
        float fitnessFirstc2 = bestClass2.getIndividuals()[0].getFitness();
        float testFitc2 = BreastFitness.calculateFitness(bestc2, BreastClasses.fromId(2), breastDataset.testDataset())[0].getFitness();
        bestClass2.getIndividuals()[0].setFitness(fitnessFirstc2);
        bestClass2.getBest(testFitc2);
        Utils.formatResult(null, null, bestClass2.getIndividuals()[0],
                bestClass2.getBreastClass(), bestClass2.getIndividuals()[0].getFitness(), testFitc2);
    }
}
