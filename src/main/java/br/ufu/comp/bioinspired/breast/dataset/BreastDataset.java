package br.ufu.comp.bioinspired.breast.dataset;

import br.ufu.comp.bioinspired.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

public class BreastDataset {

    private final String breastDataset = "data-sets/breast-cancer/breast-cancer.data";

    private LinkedList<String[]> dataSet;
    private LinkedList<String[]> trainingDataset;
    private LinkedList<String[]> testDataset;

    public BreastDataset() {
        dataSet = new LinkedList<>();
        trainingDataset = new LinkedList<>();
        testDataset = new LinkedList<>();

        try {
            this.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LinkedList<String[]> class1ds = new LinkedList<>();
        LinkedList<String[]> class2ds = new LinkedList<>();
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i)[0].equals("no-recurrence-events")) {
                class1ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[0].equals("recurrence-events")){
                class2ds.add(dataSet.get(i));
            }
        }

        // training set 2/3 of the dataset
        Random r = new Random();
        int class1SetSize = 2 * class1ds.size() / 3;
        for (int i = 0; i < class1SetSize; i++) {
            int index = r.nextInt(class1ds.size());
            trainingDataset.add(class1ds.get(index));
            class1ds.remove(index);
        }

        int class2SetSize = (2 * class2ds.size()) / 3;
        for (int i = 0; i < class2SetSize; i++) {
            int index = r.nextInt(class2ds.size());
            trainingDataset.add(class2ds.get(index));
            class2ds.remove(index);
        }

        // training set 1/3 of the dataset, the remaining that was not selected for the training set
        testDataset.addAll(class1ds);
        testDataset.addAll(class2ds);
    }

    public LinkedList<String[]> getTrainingDataset() {
        return trainingDataset;
    }

    public LinkedList<String[]> testDataset() {
        return testDataset;
    }

    private void load() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(breastDataset);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] v = line.split(",");
            String[] values = new String[v.length];
            for (int k = 0; k < v.length; k++) {
                values[k] = v[k];
            }
            dataSet.add(values);
        }
    }
}
