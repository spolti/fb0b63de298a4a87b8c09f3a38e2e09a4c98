package br.ufu.comp.bioinspired.dermatological.chromosome;

import br.ufu.comp.bioinspired.dermatological.attributes.DermaAttributes;

import java.util.LinkedList;

public class Chromosome{
        private LinkedList<Gene> genes;
        private float fitness;

    public Chromosome(int chromosomeSize) {
        // add 1 to the original value as the diseases starts with 1
        int cs = chromosomeSize + 1;
        this.genes = new LinkedList<>();
        for (int i = 0; i < chromosomeSize; i++) {
            genes.add(i, new Gene(DermaAttributes.getByID(i)));
        }
    }

    public Chromosome() {
        this.genes = new LinkedList<>();
        this.fitness = 0.0f;
    }

    public Chromosome(LinkedList<Gene> genes) {
        this.genes = genes;
    }

    public  LinkedList<Gene> genes() {
        return this.genes;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public float getFitness() {
        return fitness;
    }
}
