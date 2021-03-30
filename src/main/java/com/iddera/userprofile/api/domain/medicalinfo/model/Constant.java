package com.iddera.userprofile.api.domain.medicalinfo.model;

import java.util.Set;

public class Constant {
    //TODO: create an endpoint that sends these categories to the user
    public static final Set<String> MEDICATION_CATEGORY = Set.of(
            "Analgesics/Pain Killers",
            "Anti-pyretic (for fevers)",
            "Antihistamine",
            "Anti-hypertensive",
            "Anti-emetics (nausea, vomiting)",
            "Anti-diabetic",
            "Antibiotics (for bacterial infections)",
            "Antacids (for hyperacidity)",
            "Contraceptives",
            "Decongestants",
            "Electrolytes",
            "Laxatives",
            "Muscle relaxants"
    );
}
