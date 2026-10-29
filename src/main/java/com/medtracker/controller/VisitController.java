package com.medtracker.controller;

import com.medtracker.model.Medicine;
import com.medtracker.model.Visit;
import com.medtracker.service.VisitService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class VisitController {

    private final VisitService service;

    public VisitController(VisitService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String list(@RequestParam(defaultValue = "false") boolean antibiotics, Model model) {
        model.addAttribute("visits", service.list(antibiotics));
        model.addAttribute("antibiotics", antibiotics);
        return "visits/list";
    }

    @GetMapping("/visits/new")
    public String newForm(Model model) {
        Visit visit = new Visit();
        visit.setVisitDate(LocalDate.now());
        model.addAttribute("visit", visit);
        return form(visit, model, "New visit", "/visits");
    }

    @PostMapping("/visits")
    public String create(@Valid @ModelAttribute("visit") Visit visit, BindingResult result, Model model) {
        visit.setId(null);
        if (hasErrors(visit, result)) {
            return form(visit, model, "New visit", "/visits");
        }
        Visit saved = service.create(visit);
        return "redirect:/visits/" + saved.getId();
    }

    @GetMapping("/visits/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("visit", service.get(id));
        return "visits/detail";
    }

    @GetMapping("/visits/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Visit visit = service.get(id);
        model.addAttribute("visit", visit);
        return form(visit, model, "Edit visit", "/visits/" + id);
    }

    @PostMapping("/visits/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("visit") Visit visit,
                         BindingResult result, Model model) {
        service.get(id);
        visit.setId(id);
        if (hasErrors(visit, result)) {
            return form(visit, model, "Edit visit", "/visits/" + id);
        }
        service.update(id, visit);
        return "redirect:/visits/" + id;
    }

    @PostMapping("/visits/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

    private boolean hasErrors(Visit visit, BindingResult result) {
        boolean named = visit.getMedicines().stream()
                .anyMatch(m -> m.getName() != null && !m.getName().isBlank());
        if (!named) {
            result.rejectValue("medicines", "medicines.required", "Add at least one medicine with a name");
        }
        return result.hasErrors();
    }

    private String form(Visit visit, Model model, String title, String action) {
        if (visit.getMedicines().isEmpty()) {
            visit.getMedicines().add(new Medicine());
        }
        model.addAttribute("title", title);
        model.addAttribute("action", action);
        return "visits/form";
    }
}
