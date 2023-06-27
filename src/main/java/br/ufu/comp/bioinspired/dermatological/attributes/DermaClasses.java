package br.ufu.comp.bioinspired.dermatological.attributes;

// generated a enum called Classes which contains the folloing items: 1: Psoriasis 2: Seboreic dermatitis 3: Lichen planus 4: Pityriasis rosea 5: Chronic dermatitis 6: Pityriasis rubra pilaris each item must contain its id and name
public enum DermaClasses {
    PSORIASIS(1, "Psoriasis"),
    SEBOREIC(2, "Seboreic dermatitis"),
    LICHEN(3, "Lichen planus"),
    ROSEA(4, "Pityriasis rosea"),
    CHRONIC(5, "Chronic dermatitis"),
    RUBRA(6, "Pityriasis rubra pilaris");

    private int id;
    private String name;

    private DermaClasses(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // generated a method called getId which returns the value of the id attribute
    public int getId() {
        return id;
    }

    // generated a method called getName which returns the value of the name attribute
    public String getName() {
        return name;
    }

    // generated a method called fromId which receives a integer parameter and returns the corresponding item of the enumeration
    public static DermaClasses fromId(int id) {
        for (DermaClasses cls : DermaClasses.values()) {
            if (cls.getId() == id) {
                return cls;
            }
        }
        return null;
    }
}
