package br.ufu.comp.bioinspired.dermatological.attributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Histopathological Attributes: (take values 0, 1, 2, 3)
 */
public enum DermaAttributes {
    // Clinical
    ERYTHEMA(0, "Erythema"),
    SCALING(1, "Scaling"),
    DEFINITE_BORDERS(2, "Definite borders"),
    ITCHING(3, "Itching"),
    KOEBNER_PHENOMENON(4, "Koebner phenomenon"),
    POLYGONAL_PAPULES(5, "Polygonal papules"),
    FOLLICULAR_PAPULES(6, "Follicular papules"),
    ORAL_MUCOSAL_INVOLVEMENT(7, "Oral mucosal involvement"),
    KNEE_AND_ELBOW_INVOLVEMENT(8, "Knee and elbow involvement"),
    SCALP_INVOLVEMENT(9, "Scalp involvement"),
    FAMILY_HISTORY(10, "Family history"), // 0 or 1
    AGE(33, "Age"), // linear

    // Histopathological
    MELANIN_INCONTINENCE(11, "Melanin incontinence"),
    EOSINOPHILS_IN_THE_INFILTRATE(12, "Eosinophils in the infiltrate"),
    PNL_INFILTRATE(13, "PNL infiltrate"),
    FIBROSIS_OF_THE_PAPILLARY_DERMIS(14, "Fibrosis of the papillary dermis"),
    EXOCYTOSIS(15, "Exocytosis"),
    ACANTHOSIS(16, "Acanthosis"),
    HYPERKERATOSIS(17, "Hyperkeratosis"),
    PARAKERATOSIS(18, "Parakeratosis"),
    CLUBBING_OF_THE_RETE_RIDGES(19, "Clubbing of the rete ridges"),
    ELONGATION_OF_THE_RETE_RIDGES(20, "Elongation of the rete ridges"),
    THINNING_OF_THE_SUPRAPAPILLARY_EPIDERMIS(21, "Thinning of the suprapapillary epidermis"),
    SPONGIFORM_PUSTULE(22, "Spongiform pustule"),
    MUNRO_MICROABCESS(23, "Munro microabcess"),
    FOCAL_HYPERGRANULOSIS(24, "Focal hypergranulosis"),
    DISAPPEARANCE_OF_THE_GRANULAR_LAYER(25, "Disappearance of the granular layer"),
    VACUOLISATION_AND_DAMAGE_OF_BASEL_LAYER(26, "Vacuolisation and damage of basal layer"),
    SPONGIOSIS(27, "Spongiosis"),
    SAW_TOOTH_APPEARANCE_OF_RETES(28, "Saw-tooth appearance of retes"),
    FOLLICULAR_HORN_PLUG(29, "Follicular horn plug"),
    PERIFOLLICULAR_PARAKERATOSIS(30, "Perifollicular parakeratosis"),
    INFLAMMATORY_MONOLUCLEAR_INFLITRATE(31, "Inflammatory monoluclear inflitrate"),
    BAND_LIKE_INFILTRATE(32, "Band-like infiltrate");


    private static final Map<Integer, DermaAttributes> BY_ID = new HashMap<>();

    static {
        for (DermaAttributes d : values()) {
            BY_ID.put(d.id, d);
        }
    }

    // Private fields to store the id and name
    private final int id;
    private final String name;

    // Private constructor to assign the id and name
    private DermaAttributes(int id, String name) {
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

    public static DermaAttributes getByID(int id) {
        return BY_ID.get(id);
    }

}
