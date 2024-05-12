package controller;

import model.Etudiant;
import repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

	@Autowired
    private EtudiantRepository etudiantRepository;

    @GetMapping("/get")
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @PostMapping("/post")
    public Etudiant createEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @GetMapping("/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiantDetails) {
        Etudiant etudiant = etudiantRepository.findById(id).orElse(null);
        if (etudiant != null) {
            etudiant.setNom(etudiantDetails.getNom());
            etudiant.setPrenom(etudiantDetails.getPrenom());
            etudiant.setNbAbsences(etudiantDetails.getNbAbsences());
            etudiant.setReussite(etudiantDetails.isReussite()); // Update exam result or grade
            return etudiantRepository.save(etudiant);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable Long id) {
        etudiantRepository.deleteById(id);
    }

    @GetMapping("/taux-absence")
    public double getTauxAbsentéisme() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        int totalEtudiants = etudiants.size();
        if (totalEtudiants == 0) {
            return 0.0; // Avoid division by zero
        }
        int totalAbsences = etudiants.stream().mapToInt(Etudiant::getNbAbsences).sum();
        return ((double) totalAbsences / totalEtudiants) * 100.0;
    }

    @GetMapping("/taux-reussite")
    public double getTauxRéussite() {
    	 List<Etudiant> etudiants = etudiantRepository.findAll();
         int totalEtudiants = etudiants.size();
         if (totalEtudiants == 0) {
             return 0.0; // Avoid division by zero
         }
         long totalReussite = etudiants.stream().filter(Etudiant::isReussite).count();
         return ((double)totalReussite / totalEtudiants) * 100.0;
    }
}