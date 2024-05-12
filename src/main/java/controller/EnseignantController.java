package controller;

import model.Enseignant;
import repository.EnseignantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    @PostMapping
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @GetMapping("/{id}")
    public Enseignant getEnseignantById(@PathVariable Long id) {
        return enseignantRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Enseignant updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignantDetails) {
        Enseignant enseignant = enseignantRepository.findById(id).orElse(null);
        if (enseignant != null) {
            enseignant.setNom(enseignantDetails.getNom());
            enseignant.setPrenom(enseignantDetails.getPrenom());
            enseignant.setMatiere(enseignantDetails.getMatiere());
            return enseignantRepository.save(enseignant);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEnseignant(@PathVariable Long id) {
        enseignantRepository.deleteById(id);
    }
}