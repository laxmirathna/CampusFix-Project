package com.campusfix.backend.complaint;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner seedComplaints(ComplaintRepository repository) {
        return args -> {
            if (repository.count() > 0) {
                return;
            }

            save(repository, "Broken classroom fan", "Electrical",
                    "Block A, Room 204", "High",
                    "The ceiling fan is not working.");

            save(repository, "Water leakage", "Plumbing",
                    "Library, Ground Floor", "Medium",
                    "Water is leaking near the wash area.");

            save(repository, "Projector not working", "Classroom Equipment",
                    "Block B, Room 105", "Low",
                    "The projector does not turn on.");
        };
    }

    private void save(ComplaintRepository repository, String title,
                      String category, String location, String priority,
                      String description) {
        Complaint complaint = new Complaint();
        complaint.setTitle(title);
        complaint.setCategory(category);
        complaint.setLocation(location);
        complaint.setPriority(priority);
        complaint.setDescription(description);
        complaint.setStatus("SUBMITTED");
        complaint.setCreatedAt(LocalDateTime.now().toString());
        repository.save(complaint);
    }
}
