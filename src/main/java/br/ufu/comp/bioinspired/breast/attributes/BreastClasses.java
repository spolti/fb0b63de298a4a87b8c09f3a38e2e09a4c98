package br.ufu.comp.bioinspired.breast.attributes;

public enum BreastClasses {
    RECURRENCE(1, "recurrence-events"),
    NO_RECURRENCE(2, "no-recurrence-events");

    private int id;
    private String name;

    private BreastClasses(int id, String name) {
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
    public static BreastClasses fromId(int id) {
        for (BreastClasses cls : BreastClasses.values()) {
            if (cls.getId() == id) {
                return cls;
            }
        }
        return null;
    }
}
