package br.ufu.comp.bioinspired.dermatological.ag;

import br.ufu.comp.bioinspired.dermatological.attributes.DermaAttributes;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaClasses;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;
import br.ufu.comp.bioinspired.dermatological.chromosome.Gene;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class Tournament {

    private static final int TOURNAMENT_SIZE = 3;
    private static final double CROSSOVER_PROBABILITY = 1.0; // 100%

    private static Random random = new Random();

    public static Chromosome[] stochasticTournament(Chromosome[] chromosomes, DermaClasses diseaseClass, LinkedList<int[]> dataset) {
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
        }

        Chromosome[] children = new Chromosome[chromosomes.length];
        Chromosome[] roundResult = new Chromosome[chromosomes.length + 1];
        int size = chromosomes.length;
        if (size % 2 != 0) {
            size += 1;
        }

        for (int i = 0; i < size / 2; i++) {
            Chromosome parent1 = performTournamentSelection(chromosomes, random, totalFitness, rouletteTotal);
            Chromosome parent2 = performTournamentSelection(chromosomes, random, totalFitness, rouletteTotal);

            // Perform crossover if crossover probability is met
            if (random.nextDouble() < CROSSOVER_PROBABILITY) {
                Chromosome[] cls = performTwoPointCrossover(parent1, parent2, random);
                children[i] = cls[0];
                //children[(size / 2) + i] = cls[1];
                try {
                    children[(size / 2) + i] = cls[1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    // ignore
                }

            } else {
                // If crossover is not performed, select one of the parents as the child
                System.out.println("No crossover - as the rate is 100%, this point should never be reached.");
                children[i] = parent1;
                children[(size / 2) + i] = parent2;
            }
        }

        // calculate the fitness of the new population
        children = Fitness.calculateFitness(children, diseaseClass, dataset);
        //Arrays.sort(children, Comparator.comparingDouble(Chromosome::getFitness).reversed());
        // Mutation
        for (int i = 0; i < children.length; i++) {
            // for each chromosome, all gene attributes have the 30% of chances to be mutated.
            for (int k = 0; k < children[i].genes().size(); k++) {
                if (random.nextDouble() < 0.3) {
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setWeight(Mutation.mutateWeight(children[i].genes().get(k).weight()));
                }
                if (random.nextDouble() < 0.3) {
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setOperator(Mutation.mutateOperator(children[i].genes().get(k).operator()));
                }
                if (random.nextDouble() < 0.3) {
                    DermaAttributes attribute = DermaAttributes.getByID(children[i].genes().get(k).attributeID());
                    // if the gene is mutated, the new value will be a random value between 0 and 1
                    children[i].genes().get(k).setDomainValue(Mutation.mutateDomainValue(attribute,
                            children[i].genes().get(k).domainValue(),
                            children[i].genes().get(k).operator()));
                }
            }
            roundResult[i] = children[i];
        }
        // best survives without mutation
        roundResult[roundResult.length-1] = chromosomes[0];
        return Fitness.calculateFitness(roundResult, diseaseClass, dataset);
    }

    // Perform two-point crossover between two parents
    private static Chromosome[] performTwoPointCrossover(Chromosome parent1, Chromosome parent2, Random random) {
        int middle = parent1.genes().size() / 2;
        int crossoverPoint = random.nextInt(0, middle);
        int crossoverPoint2 = random.nextInt(middle, parent1.genes().size());

        Chromosome[] children = new Chromosome[2];

        Chromosome child1 = new Chromosome();
        Chromosome child2 = new Chromosome();
        for (int i = 0; i < parent1.genes().size(); i++) {
            // first half
            if (i < crossoverPoint) {
                child1.genes().add(i, new Gene(parent1.genes().get(i)));
                child2.genes().add(i, new Gene(parent2.genes().get(i)));

            } else if (i > crossoverPoint && i < crossoverPoint2) {
                // middle
                child1.genes().add(i, new Gene(parent2.genes().get(i)));
                child2.genes().add(i, new Gene(parent1.genes().get(i)));
            } else {
                // last half
                child1.genes().add(i, new Gene(parent1.genes().get(i)));
                child2.genes().add(i, new Gene(parent2.genes().get(i)));
            }
        }
        children[0] = child1;
        children[1] = child2;
        return children;
    }

    // Perform stochastic tournament selection using roulette wheel selection
    private static Chromosome performTournamentSelection(Chromosome[] population, Random random, int totalFitness, int[] rouletteTotal) {

        Chromosome tournamentWinner = null;
        Chromosome[] tournament = new Chromosome[TOURNAMENT_SIZE];
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
