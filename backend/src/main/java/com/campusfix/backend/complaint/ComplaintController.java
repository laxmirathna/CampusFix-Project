package com.campusfix.backend.complaint;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@RestController @RequestMapping("/api/complaints")
public class ComplaintController {
 private final ComplaintRepository repository;
 public ComplaintController(ComplaintRepository repository){this.repository=repository;}
 @GetMapping public List<Complaint> all(Authentication auth){return isAdmin(auth)?repository.findAllByOrderByIdDesc():repository.findByReporterEmailIgnoreCaseOrderByIdDesc(auth.getName());}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Complaint create(@Valid @RequestBody Complaint complaint,Authentication auth){
  complaint.setId(null);complaint.setStatus("SUBMITTED");complaint.setReporterEmail(auth.getName());complaint.setAssignedTo(null);complaint.setResolutionNote(null);if(complaint.getImageData()!=null&&(!complaint.getImageData().startsWith("data:image/")||complaint.getImageData().length()>1500000))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image must be a supported image smaller than 1 MB");complaint.setCreatedAt(LocalDateTime.now().toString());if(complaint.getPriority()==null||!List.of("Low","Medium","High","Urgent").contains(complaint.getPriority()))complaint.setPriority("Medium");return repository.save(complaint);
 }
 @PatchMapping("/{id}/status") public Complaint updateStatus(@PathVariable Long id,@RequestBody StatusUpdate body,Authentication auth){requireAdmin(auth);Complaint c=get(id);if(body.status()==null||!List.of("SUBMITTED","ASSIGNED","IN_PROGRESS","RESOLVED","REJECTED").contains(body.status()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid status");c.setStatus(body.status());if(body.resolutionNote()!=null)c.setResolutionNote(body.resolutionNote().trim());return repository.save(c);}
 @PatchMapping("/{id}/assign") public Complaint assign(@PathVariable Long id,@RequestBody AssignUpdate body,Authentication auth){requireAdmin(auth);Complaint c=get(id);c.setAssignedTo(body.assignedTo()==null||body.assignedTo().isBlank()?null:body.assignedTo().trim());if(c.getAssignedTo()!=null&&"SUBMITTED".equals(c.getStatus()))c.setStatus("ASSIGNED");return repository.save(c);}
 private Complaint get(Long id){return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Complaint not found"));}
 private boolean isAdmin(Authentication a){return a.getAuthorities().stream().anyMatch(x->x.getAuthority().equals("ROLE_ADMIN"));}
 private void requireAdmin(Authentication a){if(!isAdmin(a))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Admin access required");}
 public record StatusUpdate(String status,String resolutionNote){} public record AssignUpdate(String assignedTo){}
}
