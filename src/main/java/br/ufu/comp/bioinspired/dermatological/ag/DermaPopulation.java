package br.ufu.comp.bioinspired.dermatological.ag;

import br.ufu.comp.bioinspired.Config;
import br.ufu.comp.bioinspired.Operator;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaAttributes;
import br.ufu.comp.bioinspired.dermatological.attributes.DermaClasses;
import br.ufu.comp.bioinspired.dermatological.chromosome.Chromosome;
import br.ufu.comp.bioinspired.dermatological.chromosome.Gene;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class DermaPopulation {

    private Chromosome[] individuals;
    private DermaClasses diseaseClass;

    public DermaPopulation() {
    }

    // Fitness is calculated here in the population initialization as well.
    public DermaPopulation(int populationSize, int chromosomeSize, int generations, DermaClasses diseaseClass, LinkedList<int[]> dataset) {
        this.individuals = new Chromosome[populationSize];
        this.diseaseClass = diseaseClass;
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new Chromosome(chromosomeSize);
        }
        individuals = Fitness.calculateFitness(individuals, this.diseaseClass, dataset);

        //Arrays.sort(individuals, Comparator.comparingDouble(Chromosome::getFitness).reversed());
        // Do tournament (CrossOver and Mutation will happen inside the tournament, and replace the new individuals in the population)
        // with the new population.

        for (int i = 0; i < generations; i++) {
            Arrays.sort(individuals, Comparator.comparingDouble(Chromosome::getFitness).reversed());
            // Mutation also happens here.
            individuals = Tournament.stochasticTournament(individuals, diseaseClass, dataset);
        }
        // sort again
        Arrays.sort(individuals, Comparator.comparingDouble(Chromosome::getFitness).reversed());
    }

    // TODO private or public?
    public void getBest(float testFit) {
        Chromosome[] normalized;
        Arrays.sort(individuals, Comparator.comparingDouble(Chromosome::getFitness).reversed());
        normalized = new Chromosome[1];
        LinkedList<Gene> normalizedGene = new LinkedList<>();

        for (int j = 0; j < individuals[0].genes().size(); j++) {
            // TODO centralize through predicate?
            if (individuals[0].genes().get(j).weight() > Config.WEIGHT_LIMIT) {
                if (individuals[0].genes().get(j).operator() == Operator.GREATER_EQUAL_THAN && individuals[0].genes().get(j).domainValue() == 0) {
                    //System.out.println("Filtering it out.... >= 0");
                } else {
                    // normalize means, add only the genes that have weight > 0.7
                    normalizedGene.add(individuals[0].genes().get(j));
                }
            }
        }

        normalized[0] = new Chromosome(normalizedGene);
        normalized[0].setFitness(individuals[0].getFitness());

        // TODO if debug print normalized
        System.out.println(printAsTable(normalized, testFit));
    }

    public Chromosome[] getIndividuals() {
        return individuals;
    }

    private StringBuilder printAsTable(Chromosome[] chromosomes, float testFitness) {
        StringBuilder b = new StringBuilder();
        String header = "Individual for Class " + this.diseaseClass.getName() + " - " + this.diseaseClass.getId() +
                ": Fitness: " + chromosomes[0].getFitness() + " / " + testFitness;
        for (Chromosome c : chromosomes) {
            b.append(String.format(" _______________________________________________________________________________\n"));
            b.append(String.format("|%" + (header.length() + (50 - header.length()) / 2) + "s                   |\n", header));
            b.append(String.format("|-------------------------------------------------------------------------------|\n"));
            b.append(String.format("|%-41s|%7s |%14s      |%6s |\n", "Attribute Name", "Weight", "Operator", "Value"));
            b.append(String.format("|--------------------------------------------------+--------------------+-------|\n"));
            for (Gene g : c.genes()) {
                b.append(String.format("|%-41s|%-7s |%-20s|%6s |\n", DermaAttributes.getByID(g.attributeID()), g.weight(), g.operator().name(), g.domainValue()));
            }
            b.append(String.format(" ‾"));
            for (int i = 0; i < 78; i++) {
                b.append(String.format("‾"));
            }
//            b.append(String.format("\n"));
        }
        return b;
    }

    @Override
    public String toString() {
        return printAsTable(this.individuals, 0.0f).toString();
    }


}
