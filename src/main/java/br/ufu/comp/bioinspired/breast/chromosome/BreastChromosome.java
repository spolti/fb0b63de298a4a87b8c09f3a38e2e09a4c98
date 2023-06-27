package br.ufu.comp.bioinspired.breast.chromosome;

import br.ufu.comp.bioinspired.breast.attributes.BreastAttributes;

import java.util.LinkedList;

public class BreastChromosome {
        private LinkedList<BreastGene> genes;
        private float fitness;

    public BreastChromosome(int chromosomeSize) {
        // add 1 to the original value as the diseases starts with 1
        this.genes = new LinkedList<>();
        genes.add(0, null);
        for (int i = 1; i < chromosomeSize; i++) {
            genes.add(i, new BreastGene(BreastAttributes.getByID(i)));
        }
    }

    public BreastChromosome() {
        this.genes = new LinkedList<>();
        // skip the first position as it is the class
        genes.add(0, null);
        this.fitness = 0.0f;
    }

    public BreastChromosome(LinkedList<BreastGene> genes) {
        this.genes = genes;
    }

    public  LinkedList<BreastGene> genes() {
        return this.genes;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float getFitness() {
        return fitness;
    }
}
