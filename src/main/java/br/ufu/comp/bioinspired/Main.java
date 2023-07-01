package br.ufu.comp.bioinspired;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main implements QuarkusApplication {
    /**
     * both chromosome sizes based on its respectively dataset
     */
    // dermatological
    private final int DERMA_CHROMO_SIZE = 34;
    // breast
    private final int BREAST_CHROMO_SIZE = 9;
    // common ag attributes
    private final int D_POPULATION_SIZE = 50;
    private final int GENERATIONS = 50;
    private final int TURNS = 3;

    @Override
    public int run(String... args) {
        long start = System.currentTimeMillis();

        // Derma rules calculation
        RunDermatological.execute(D_POPULATION_SIZE, DERMA_CHROMO_SIZE, GENERATIONS, TURNS);

        // Breast cancer rules calculation
        RunBreast.execute(D_POPULATION_SIZE, BREAST_CHROMO_SIZE, GENERATIONS, TURNS);

        System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
        return 0;
    }


}
