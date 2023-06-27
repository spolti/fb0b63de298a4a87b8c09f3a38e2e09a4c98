package br.ufu.comp.bioinspired.breast.attributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *    1. Class: no-recurrence-events, recurrence-events
 *    2. age: 10-19, 20-29, 30-39, 40-49, 50-59, 60-69, 70-79, 80-89, 90-99.
 *    3. menopause: lt40, lt40, premeno.
 *    4. tumor-size: 0-4, 5-9, 10-14, 15-19, 20-24, 25-29, 30-34, 35-39, 40-44,
 *                   45-49, 50-54, 55-59.
 *    5. inv-nodes: 0-2, 3-5, 6-8, 9-11, 12-14, 15-17, 18-20, 21-23, 24-26,
 *                  27-29, 30-32, 33-35, 36-39.
 *    6. node-caps: yes, no.
 *    7. deg-malig: 1, 2, 3.
 *    8. breast: left, right.
 *    9. breast-quad: left-up, left-low, right-up,	right-low, central.
 *   10. irradiate:	yes, no.
 */
public enum BreastAttributes {
    // Clinical
    AGE (1, "age"),
    MENOPAUSE (2, "menopause"),
    TUMOR_SIZE (3, "tumor-size"),
    INV_NODES (4, "inv-nodes"),
    NODE_CAPS (5, "node-caps"),
    DEG_MALIG (6, "deg-malig"),
    BREAST (7, "breast"),
    BREAST_QUAD (8, "breast-quad"),
    IRRADIATE (9, "irradiate");

    private static final Map<Integer, BreastAttributes> BY_ID = new HashMap<>();
    public static final HashMap<Integer, String> age = new HashMap<>();
    public static final HashMap<Integer, String> menopause = new HashMap<>();
    public static final HashMap<Integer, String> tumorSize = new HashMap<>();
    public static final HashMap<Integer, String> invNodes = new HashMap<>();
    public static final HashMap<Integer, String> yesNo = new HashMap<>();
    public static final HashMap<Integer, String> breast = new HashMap<>();
    public static final HashMap<Integer, String> breastQuad = new HashMap<>();
    // Operator for string comparison, categorical
    public static final HashSet<String> stringOperator = new HashSet<>();


    static {
        for (BreastAttributes d : values()) {
            BY_ID.put(d.id, d);
        }

        stringOperator.add("node-caps");
        stringOperator.add("breast");
        stringOperator.add("breast-quad");
        stringOperator.add("irradiate");
        stringOperator.add("menopause");

        age.put(0, "10-19");
        age.put(1, "20-29");
        age.put(2, "30-39");
        age.put(3, "40-49");
        age.put(4, "50-59");
        age.put(5, "60-69");
        age.put(6, "70-79");
        age.put(7, "80-89");
        age.put(8, "90-99");

        menopause.put(0, "lt40");
        menopause.put(1, "ge40");
        menopause.put(2, "premeno");

        tumorSize.put(0, "0-4");
        tumorSize.put(1, "5-9");
        tumorSize.put(2, "10-14");
        tumorSize.put(3, "15-19");
        tumorSize.put(4, "20-24");
        tumorSize.put(5, "25-29");
        tumorSize.put(6, "30-34");
        tumorSize.put(7, "35-39");
        tumorSize.put(8, "40-44");
        tumorSize.put(9, "45-49");
        tumorSize.put(10, "50-54");
        tumorSize.put(11, "55-59");

        invNodes.put(0, "0-2");
        invNodes.put(1, "3-5");
        invNodes.put(2, "6-8");
        invNodes.put(3, "9-11");
        invNodes.put(4, "12-14");
        invNodes.put(5, "15-17");
        invNodes.put(6, "18-20");
        invNodes.put(7, "21-23");
        invNodes.put(8, "24-26");
        invNodes.put(9, "27-29");
        invNodes.put(10, "30-32");
        invNodes.put(11, "33-35");
        invNodes.put(12, "36-39");

        yesNo.put(0, "yes");
        yesNo.put(1, "no");

        breast.put(0, "left");
        breast.put(1, "right");

        breastQuad.put(0, "left-up");
        breastQuad.put(1, "left-low");
        breastQuad.put(2, "right-up");
        breastQuad.put(3, "right-low");
        breastQuad.put(4, "central");
    }

    // Private fields to store the id and name
    private final int id;
    private final String name;

    // Private constructor to assign the id and name
    private BreastAttributes(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Public getters to access the id and name
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static BreastAttributes getByID(int id) {
        return BY_ID.get(id);
    }


}
