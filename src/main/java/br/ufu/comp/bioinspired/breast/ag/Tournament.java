package br.ufu.comp.bioinspired.breast.ag;

import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;
import br.ufu.comp.bioinspired.breast.attributes.BreastClasses;
import br.ufu.comp.bioinspired.breast.chromosome.BreastChromosome;
import br.ufu.comp.bioinspired.breast.chromosome.BreastGene;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Tournament {
    private static final int TOURNAMENT_SIZE = 3;
    private static final double CROSSOVER_PROBABILITY = 1.0; // 100%
    private static final double MUTATION_PROBABILITY = 0.3; // 100%

    private static Random random = new Random();

    /**
     *
     */
    public static BreastChromosome[] stochasticTournament(BreastChromosome[] chromosomes, BreastClasses breastClass, LinkedList<String[]> dataset) {
        // create the roulette wheel
        int fit[];
        int[] rouletteTotal = new int[]{};
        int[] result;
        int totalFitness = 0;
        for (int i = 0; i < chromosomes.length; i++) {
            // as the fitness is a real value from 0 to 1, let's get a integer value for each, let's multiply by 100
            // to decide how many houses each chromosome will have in the roulette
            int by100 = Math.round(chromosomes[i].getFitness() * 100);

            // if the fitness is 0, add 1 house for it.
            if (by100 == 0) {
                by100 = 1;
            }

            totalFitness += by100;
            fit = new int[by100];
            // fill the array with the houses of each chromosome have.
            // the index of the array is the chromosome index
            Arrays.fill(fit, i);

            int aLen = rouletteTotal.length;
            int bLen = fit.length;
            result = new int[aLen + bLen];
            System.arraycopy(rouletteTotal, 0, result, 0, aLen);
            System.arraycopy(fit, 0, result, aLen, bLen);
            rouletteTotal = result;
            //System.out.println(Arrays.toString(fit));
        }
//        System.out.println(Arrays.toString(rouletteTotal));
//        System.out.println("Total Fitness " + totalFitness);


        BreastChromosome[] children = new BreastChromosome[chromosomes.length];
        // TODO small bug, only accepts even numbers for population size > 2
        int j = 0;
        for (int i = 0; i < chromosomes.length / 2; i++) {
            BreastChromosome parent1 = performTournamentSelection(chromosomes, random, totalFitness, rouletteTotal);
            BreastChromosome parent2 = performTournamentSelection(chromosomes, random, totalFitness, rouletteTotal);

            // Perform crossover if crossover probability is met
            if (random.nextDouble() < CROSSOVER_PROBABILITY) {
                BreastChromosome[] cls = performTwoPointCrossover(parent1, parent2, random);
                children[i] = cls[0];
                children[(chromosomes.length / 2) + i] = cls[1];
                //children[j++] = performTwoPointCrossover(parent1, parent2, random)[0];
                //children[j++] = performTwoPointCrossover(parent1, parent2, random)[1];

            } else {
                // If crossover is not performed, select one of the parents as the child
                children[j++] = parent1;
                children[j++] = parent2;
            }
        }

        // calculate the fitness of the new population
        children = BreastFitness.calculateFitness(children, breastClass, dataset);
        Arrays.sort(children, Comparator.comparingDouble(BreastChromosome::getFitness).reversed());

        // Mutation
        // best chromosome will not be mutated, so start the loop from 1.
        for (int i = 1; i < children.length; i++) {
            // for each chromosome, all gene attributes have the 30% of chances to be mutated.
            for (int k = 1; k < children[i].genes().size(); k++) {
                if (random.nextDouble() < MUTATION_PROBABILITY) {
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setWeight(Mutation.mutateWeight(children[i].genes().get(k).weight()));
                }
                if (random.nextDouble() < MUTATION_PROBABILITY) {
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setOperator(Mutation.mutateOperator(children[i].genes().get(k).operator(),
                            BreastAttributes.getByID(children[i].genes().get(k).attributeID())));
                }
                if (random.nextDouble() < MUTATION_PROBABILITY) {
                    BreastAttributes attribute = BreastAttributes.getByID(children[i].genes().get(k).attributeID());
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setDomainValue(Mutation.mutateDomainValue(attribute, children[i].genes().get(k).domainValue()));
                }
            }
        }
        return children;
    }

    // Perform two-point crossover between two parents
    private static BreastChromosome[] performTwoPointCrossover(BreastChromosome parent1, BreastChromosome parent2, Random random) {
        int middle = parent1.genes().size() / 2;
        int crossoverPoint = random.nextInt(1, middle);
        int crossoverPoint2 = random.nextInt(middle, parent1.genes().size());

        BreastChromosome[] children = new BreastChromosome[2];

        BreastChromosome child1 = new BreastChromosome();
        BreastChromosome child2 = new BreastChromosome();
        for (int i = 1; i < parent1.genes().size(); i++) {
            // first half
            if (i < crossoverPoint) {
                child1.genes().add(i, new BreastGene(parent1.genes().get(i)));
                child2.genes().add(i, new BreastGene(parent2.genes().get(i)));
                // middle
            } else if (i > crossoverPoint && i < crossoverPoint2) {
                child1.genes().add(i, new BreastGene(parent2.genes().get(i)));
                child2.genes().add(i, new BreastGene(parent1.genes().get(i)));
            } else {
                // last half
                child1.genes().add(i, new BreastGene(parent1.genes().get(i)));
                child2.genes().add(i, new BreastGene(parent2.genes().get(i)));
            }
        }
        children[0] = child1;
        children[1] = child2;
        return children;
    }

    // Perform stochastic tournament selection using roulette wheel selection
    private static BreastChromosome performTournamentSelection(BreastChromosome[] population, Random random, int totalFitness, int[] rouletteTotal) {

        BreastChromosome tournamentWinner = null;
        BreastChromosome[] tournament = new BreastChromosome[TOURNAMENT_SIZE];
        // Perform tournament selection
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int candidate = random.nextInt(totalFitness);
            tournament[i] = population[rouletteTotal[candidate]];
        }

        tournamentWinner = tournament[0];
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            if (tournament[i].getFitness() > tournamentWinner.getFitness()) {
                tournamentWinner = tournament[i];
            }
        }
        return tournamentWinner;
    }
}
