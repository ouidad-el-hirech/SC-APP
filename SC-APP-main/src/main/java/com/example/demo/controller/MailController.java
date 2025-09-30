package com.example.demo.controller;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/mails")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping("/send")
	public ResponseEntity<String> sendSimpleEmail(@RequestBody SendMailRequest req) {
		try {
			mailService.sendSimpleEmail(req.getTo(), req.getSubject(), req.getText());
			return ResponseEntity.ok("Email envoyé avec succès à " + req.getTo());
		} catch (Exception e) {
			Throwable root = e;
			while (root.getCause() != null) root = root.getCause();
			return ResponseEntity.badRequest()
					.body("Erreur lors de l'envoi de l'email: " + root.getMessage());
		}
	}

	// Request DTO for JSON input
	public static class SendMailRequest {
		private String to;
		private String subject;
		private String text;

		// Getters and setters
		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
}