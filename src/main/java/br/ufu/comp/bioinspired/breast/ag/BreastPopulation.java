package br.ufu.comp.bioinspired.breast.ag;

import br.ufu.comp.bioinspired.Config;
import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;
import br.ufu.comp.bioinspired.breast.attributes.BreastClasses;
import br.ufu.comp.bioinspired.breast.chromosome.BreastChromosome;
import br.ufu.comp.bioinspired.breast.chromosome.BreastGene;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class BreastPopulation {
    private BreastChromosome[] individuals;
    private BreastClasses breastClass;

    public BreastPopulation() {}

    /**
     * The normalized chromosome will remove the attribute from the gene based on the
     * threshold defined, for this problem 0.3
     * genes with weight <= 0.3 will be removed from the gene
     */


    // Fitness is calculated here in the population initialization as well.
    public BreastPopulation(int populationSize, int chromosomeSize, int generations, BreastClasses breastClass, LinkedList<String[]> dataset) {
        this.individuals = new BreastChromosome[populationSize];
        this.breastClass = breastClass;
        for (int i = 0; i < populationSize; i++) {
            individuals[i] = new BreastChromosome(chromosomeSize);
        }

        individuals = BreastFitness.calculateFitness(individuals, this.breastClass, dataset);

        // Arrays.sort(individuals, Comparator.comparingDouble(BreastChromosome::getFitness).reversed());
        // Do tournament (CrossOver and Mutation will happen inside the tournament, and replace the new individuals in the population)
        // with the new population.

        for (int i = 0; i < generations; i++) {
            Arrays.sort(individuals, Comparator.comparingDouble(BreastChromosome::getFitness).reversed());
            // Mutation also happens here.
            individuals = Tournament.stochasticTournament(individuals, this.breastClass, dataset);
        }

        // sort again.
        Arrays.sort(individuals, Comparator.comparingDouble(BreastChromosome::getFitness).reversed());
    }

    // TODO private or public?
    public void getBest(float testFitness) {
        BreastChromosome[] normalized;
        Arrays.sort(individuals, Comparator.comparingDouble(BreastChromosome::getFitness).reversed());
        normalized = new BreastChromosome[1];
        LinkedList<BreastGene> normalizedGene = new LinkedList<>();

        for (int j = 1; j < individuals[0].genes().size(); j++) {
            // TODO centralize through predicate?
            if (individuals[0].genes().get(j).weight() > Config.WEIGHT_LIMIT) {
                // normalize means, add only the genes that have weight > 0.7
                normalizedGene.add(individuals[0].genes().get(j));
            }
        }

        normalized[0] = new BreastChromosome(normalizedGene);
        normalized[0].setFitness(individuals[0].getFitness());

        // TODO if debug print normalized
        System.out.println(printAsTable(normalized, testFitness));
    }

    public BreastChromosome[] getIndividuals() {
        return individuals;
    }

    public BreastClasses getBreastClass() {
        return breastClass;
    }

    public void setIndividuals(BreastChromosome[] individuals) {
        this.individuals = individuals;
    }

    public void setBreastClass(BreastClasses breastClass) {
        this.breastClass = breastClass;
    }

    private StringBuilder printAsTable(BreastChromosome[] chromosomes, float testFitness) {
        StringBuilder b = new StringBuilder();
        String header = "Individual for Class " + this.breastClass.getName() +
                ": Fitness: " + chromosomes[0].getFitness()+ " / " + testFitness;
        for (BreastChromosome c : chromosomes) {
            if (c == null) {
                break;
            }
            b.append(String.format(" ___________________________________________________________________\n"));
            b.append(String.format("|%" + (header.length() + (40 - header.length()) / 2) + "s  |\n", header));
            b.append(String.format("|------------------------------------------------------------------|\n"));
            b.append(String.format("|%-25s|%7s |%14s      |%9s |\n", "Attribute Name", "Weight", "Operator", "Value"));
            b.append(String.format("|-------------------------+--------+--------------------+----------|\n"));
            for (BreastGene g : c.genes()) {
                if (g != null) {
                    b.append(String.format("|%-25s|%-7s |%-20s|%9s |\n", BreastAttributes.getByID(g.attributeID()), g.weight(), g.operator().name(), g.domainValue()));;
                }
            }
            b.append(String.format(" ‾"));
            for (int i = 0; i < 65; i++) {
                b.append(String.format("‾"));
            }
            b.append(String.format("\n"));
        }
        return b;
    }

    @Override
    public String toString() {
        return printAsTable(this.individuals, 0.0f).toString();
    }
}
