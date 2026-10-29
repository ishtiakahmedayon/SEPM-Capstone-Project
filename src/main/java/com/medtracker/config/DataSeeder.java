package com.medtracker.config;

import com.medtracker.model.AntibioticRef;
import com.medtracker.repository.AntibioticRefRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final List<String> NAMES = List.of(
            "amoxicillin", "ampicillin", "cloxacillin", "flucloxacillin", "penicillin",
            "azithromycin", "clarithromycin", "erythromycin",
            "ciprofloxacin", "levofloxacin", "moxifloxacin", "ofloxacin", "norfloxacin",
            "cefixime", "cefuroxime", "cefradine", "cefalexin", "cefaclor", "cefpodoxime",
            "ceftriaxone", "cefotaxime", "ceftazidime",
            "metronidazole", "tinidazole", "doxycycline", "tetracycline",
            "gentamicin", "amikacin", "clindamycin", "cotrimoxazole", "nitrofurantoin",
            "linezolid", "vancomycin", "meropenem",
            "azithro", "zimax", "zithromax", "ciprocin", "cef-3", "flagyl",
            "moxacil", "fimoxyclav", "augmentin");

    private final AntibioticRefRepository repository;

    public DataSeeder(AntibioticRefRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(NAMES.stream().map(AntibioticRef::new).toList());
        }
    }
}
