package com.medtracker.service;

import com.medtracker.model.AntibioticRef;
import com.medtracker.model.Medicine;
import com.medtracker.model.Visit;
import com.medtracker.repository.AntibioticRefRepository;
import com.medtracker.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class VisitService {

    private final VisitRepository visits;
    private final AntibioticRefRepository antibiotics;

    public VisitService(VisitRepository visits, AntibioticRefRepository antibiotics) {
        this.visits = visits;
        this.antibiotics = antibiotics;
    }

    @Transactional(readOnly = true)
    public List<Visit> list(boolean antibioticsOnly) {
        List<Visit> all = visits.findAllByOrderByVisitDateDescIdDesc();
        if (!antibioticsOnly) {
            return all;
        }
        return all.stream().filter(v -> v.getAntibioticCount() > 0).toList();
    }

    @Transactional(readOnly = true)
    public Visit get(Long id) {
        return visits.findById(id).orElseThrow(VisitNotFoundException::new);
    }

    @Transactional
    public Visit create(Visit form) {
        Visit visit = new Visit();
        apply(visit, form);
        return visits.save(visit);
    }

    @Transactional
    public Visit update(Long id, Visit form) {
        Visit visit = get(id);
        apply(visit, form);
        return visit;
    }

    @Transactional
    public void delete(Long id) {
        visits.delete(get(id));
    }

    private void apply(Visit target, Visit form) {
        target.setVisitDate(form.getVisitDate());
        target.setDoctorName(form.getDoctorName().trim());
        target.setSymptoms(clean(form.getSymptoms()));
        target.setDiagnosis(clean(form.getDiagnosis()));

        List<String> refs = antibiotics.findAll().stream().map(AntibioticRef::getName).toList();
        target.getMedicines().clear();
        for (Medicine m : form.getMedicines()) {
            if (m.getName() == null || m.getName().isBlank()) {
                continue;
            }
            String name = m.getName().trim();
            String key = name.toLowerCase(Locale.ROOT);
            Medicine medicine = new Medicine();
            medicine.setName(name);
            medicine.setDose(clean(m.getDose()));
            medicine.setFrequency(clean(m.getFrequency()));
            medicine.setDurationDays(m.getDurationDays());
            medicine.setAntibiotic(refs.stream().anyMatch(key::contains));
            target.getMedicines().add(medicine);
        }
    }

    private String clean(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}
