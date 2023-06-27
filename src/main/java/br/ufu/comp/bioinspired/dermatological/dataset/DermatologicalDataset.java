package br.ufu.comp.bioinspired.dermatological.dataset;

import br.ufu.comp.bioinspired.Main;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class DermatologicalDataset {
    private Random r = new Random();

    private final String dermatologyDataset = "data-sets/dermatology/dermatology.data";

    private LinkedList<int[]> dataSet;
    private LinkedList<int[]> trainingDataset;
    private LinkedList<int[]> testDataset;

    public DermatologicalDataset() {
        dataSet = new LinkedList<>();
        trainingDataset = new LinkedList<>();
        testDataset = new LinkedList<>();

        try {
            this.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // break the dataset in smaller peaces opf the same class
        // then divide it in training and test datasets using the proportion 2/3 for training and the
        // remaining 1/3 for test
        LinkedList<int[]> class1ds = new LinkedList<>();
        LinkedList<int[]> class2ds = new LinkedList<>();
        LinkedList<int[]> class3ds = new LinkedList<>();
        LinkedList<int[]> class4ds = new LinkedList<>();
        LinkedList<int[]> class5ds = new LinkedList<>();
        LinkedList<int[]> class6ds = new LinkedList<>();
        for (int i = 0; i < dataSet.size(); i++) {
            if (dataSet.get(i)[dataSet.get(i).length - 1] == 1) {
                class1ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[dataSet.get(i).length - 1] == 2) {
                class2ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[dataSet.get(i).length - 1] == 3) {
                class3ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[dataSet.get(i).length - 1] == 4) {
                class4ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[dataSet.get(i).length - 1] == 5) {
                class5ds.add(dataSet.get(i));
            } else if (dataSet.get(i)[dataSet.get(i).length - 1] == 6) {
                class6ds.add(dataSet.get(i));
            }
        }

        // training set 2/3 of the dataset
        int class1SetSize = (2 * class1ds.size()) / 3;
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

        int class3SetSize = (2 * class3ds.size()) / 3;
        for (int i = 0; i < class3SetSize; i++) {
            int index = r.nextInt(class3ds.size());
            trainingDataset.add(class3ds.get(index));
            class3ds.remove(index);
        }

        int class4SetSize = (2 * class4ds.size()) / 3;
        for (int i = 0; i < class4SetSize; i++) {
            int index = r.nextInt(class4ds.size());
            trainingDataset.add(class4ds.get(index));
            class4ds.remove(index);
        }

        int class5SetSize = (2 * class5ds.size()) / 3;
        for (int i = 0; i < class5SetSize; i++) {
            int index = r.nextInt(class5ds.size());
            trainingDataset.add(class5ds.get(index));
            class5ds.remove(index);
        }

        int class6SetSize = (2 * class6ds.size()) / 3;
        for (int i = 0; i < class6SetSize; i++) {
            int index = r.nextInt(class6ds.size());
            trainingDataset.add(class6ds.get(index));
            class6ds.remove(index);
        }

        // test dataset 1/3 of the dataset
        testDataset.addAll(class1ds);
        testDataset.addAll(class2ds);
        testDataset.addAll(class3ds);
        testDataset.addAll(class4ds);
        testDataset.addAll(class5ds);
        testDataset.addAll(class6ds);
    }

    // TODO make private and se the
    public LinkedList<int[]> trainingDataset() {
        return trainingDataset;
    }

    public LinkedList<int[]> testDataset() {
        return testDataset;
    }

    private void load() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(dermatologyDataset);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] v = line.split(",");
            int[] values = new int[v.length];
            for (int k = 0; k < v.length; k++) {
                // represent the ? values as its decimal value, which is 63
                // ? means not present values for the given attribute
                if (v[k].charAt(0) == '?') {
                    values[k] = '?';
                } else {
                    values[k] = Integer.parseInt(v[k]);
                }
            }
            dataSet.add(values);
        }
    }
}
