package br.ufu.comp.bioinspired;

import br.ufu.comp.bioinspired.dermatological.ag.DermaPopulation;
import br.ufu.comp.bioinspired.dermatological.ag.Fitness;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaClasses;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;
import br.ufu.comp.bioinspired.dermatological.dataset.DermatologicalDataset;

public class RunDermatological {
    private static DermatologicalDataset dermaDataset = new DermatologicalDataset();

    public static void execute(int populationSize, int chromosomeSize, int generations, int turns) {
        DermaPopulation bestClass1 = new DermaPopulation();
        float class1Fitness = 0.0f;

        DermaPopulation bestClass2 = new DermaPopulation();
        float class2Fitness = 0.0f;

        DermaPopulation bestClass3 = new DermaPopulation();
        float class3Fitness = 0.0f;

        DermaPopulation bestClass4 = new DermaPopulation();
        float class4Fitness = 0.0f;

        DermaPopulation bestClass5 = new DermaPopulation();
        float class5Fitness = 0.0f;

        DermaPopulation bestClass6 = new DermaPopulation();
        float class6Fitness = 0.0f;


        for (int i = 0; i < turns; i++) {
            // dermatological
            DermaPopulation class1 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(1), dermaDataset.trainingDataset());
            if (class1.getIndividuals()[0].getFitness() > class1Fitness) {
                class1Fitness = class1.getIndividuals()[0].getFitness();
                bestClass1 = class1;
            }

            DermaPopulation class2 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(2), dermaDataset.trainingDataset());
            if (class2.getIndividuals()[0].getFitness() > class2Fitness) {
                class2Fitness = class2.getIndividuals()[0].getFitness();
                bestClass2 = class2;
            }

            DermaPopulation class3 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(3), dermaDataset.trainingDataset());
            if (class3.getIndividuals()[0].getFitness() > class3Fitness) {
                class3Fitness = class3.getIndividuals()[0].getFitness();
                bestClass3 = class3;
            }

            DermaPopulation class4 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(4), dermaDataset.trainingDataset());
            if (class4.getIndividuals()[0].getFitness() > class4Fitness) {
                class4Fitness = class4.getIndividuals()[0].getFitness();
                bestClass4 = class4;
            }

            DermaPopulation class5 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(5), dermaDataset.trainingDataset());
            if (class5.getIndividuals()[0].getFitness() > class5Fitness) {
                class5Fitness = class5.getIndividuals()[0].getFitness();
                bestClass5 = class5;
            }

            DermaPopulation class6 = new DermaPopulation(populationSize, chromosomeSize, generations, DermaClasses.fromId(6), dermaDataset.trainingDataset());
            if (class6.getIndividuals()[0].getFitness() > class6Fitness) {
                class6Fitness = class6.getIndividuals()[0].getFitness();
                bestClass6 = class6;
            }
        }

        if (bestClass1.getIndividuals() != null) {
            Chromosome[] bestc1 = new Chromosome[1];
            bestc1[0] = bestClass1.getIndividuals()[0];
            float fitnessFirstc1 = bestClass1.getIndividuals()[0].getFitness();
            float testFitc1 = Fitness.calculateFitness(bestc1, DermaClasses.fromId(1), dermaDataset.testDataset())[0].getFitness();
            bestClass1.getIndividuals()[0].setFitness(fitnessFirstc1);
            bestClass1.getBest(testFitc1);
            Utils.formatResult(bestClass1.getIndividuals()[0], DermaClasses.fromId(1), null,
                    null, bestClass1.getIndividuals()[0].getFitness(), fitnessFirstc1);
        }


        if (bestClass2.getIndividuals() != null) {
            Chromosome[] bestc2 = new Chromosome[1];
            bestc2[0] = bestClass2.getIndividuals()[0];
            float fitnessFirstc2 = bestClass2.getIndividuals()[0].getFitness();
            float testFitc2 = Fitness.calculateFitness(bestc2, DermaClasses.fromId(2), dermaDataset.testDataset())[0].getFitness();
            bestClass2.getIndividuals()[0].setFitness(fitnessFirstc2);
            bestClass2.getBest(testFitc2);
            Utils.formatResult(bestClass2.getIndividuals()[0], DermaClasses.fromId(2), null,
                    null, bestClass2.getIndividuals()[0].getFitness(), testFitc2);
        }

        if (bestClass3.getIndividuals() != null) {
            Chromosome[] bestc3 = new Chromosome[1];
            bestc3[0] = bestClass3.getIndividuals()[0];
            float fitnessFirstc3 = bestClass3.getIndividuals()[0].getFitness();
            float testFitc3 = Fitness.calculateFitness(bestc3, DermaClasses.fromId(3), dermaDataset.testDataset())[0].getFitness();
            bestClass3.getIndividuals()[0].setFitness(fitnessFirstc3);
            bestClass3.getBest(testFitc3);
            Utils.formatResult(bestClass3.getIndividuals()[0], DermaClasses.fromId(3), null,
                    null, bestClass3.getIndividuals()[0].getFitness(), testFitc3);
        }

        if (bestClass4.getIndividuals() != null) {
            Chromosome[] bestc4 = new Chromosome[1];
            bestc4[0] = bestClass4.getIndividuals()[0];
            float fitnessFirstc4 = bestClass4.getIndividuals()[0].getFitness();
            float testFitc4 = Fitness.calculateFitness(bestc4, DermaClasses.fromId(4), dermaDataset.testDataset())[0].getFitness();
            bestClass4.getIndividuals()[0].setFitness(fitnessFirstc4);
            bestClass4.getBest(testFitc4);
            Utils.formatResult(bestClass4.getIndividuals()[0], DermaClasses.fromId(4), null,
                    null, bestClass4.getIndividuals()[0].getFitness(), testFitc4);
        }

        if (bestClass5.getIndividuals() != null) {
            Chromosome[] bestc5 = new Chromosome[1];
            bestc5[0] = bestClass5.getIndividuals()[0];
            float fitnessFirstc5 = bestClass5.getIndividuals()[0].getFitness();
            float testFitc5 = Fitness.calculateFitness(bestc5, DermaClasses.fromId(5), dermaDataset.testDataset())[0].getFitness();
            bestClass5.getIndividuals()[0].setFitness(fitnessFirstc5);
            bestClass5.getBest(testFitc5);
            Utils.formatResult(bestClass5.getIndividuals()[0], DermaClasses.fromId(5), null,
                    null, bestClass5.getIndividuals()[0].getFitness(), testFitc5);
        }

        if (bestClass6.getIndividuals() != null) {
            Chromosome[] bestc6 = new Chromosome[1];
            bestc6[0] = bestClass6.getIndividuals()[0];
            float fitnessFirstc6 = bestClass6.getIndividuals()[0].getFitness();
            float testFitc6 = Fitness.calculateFitness(bestc6, DermaClasses.fromId(6), dermaDataset.testDataset())[0].getFitness();
            bestClass6.getIndividuals()[0].setFitness(fitnessFirstc6);
            bestClass6.getBest(testFitc6);
            Utils.formatResult(bestClass6.getIndividuals()[0], DermaClasses.fromId(6), null,
                    null, bestClass6.getIndividuals()[0].getFitness(), testFitc6);
        }

    }
}
