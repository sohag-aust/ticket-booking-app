package com.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.Ticket;
import com.test.model.AuthenticationRequest;
import com.test.model.AuthenticationResponse;
import com.test.service.MyUserDetailsService;
import com.test.service.TicketService;
import com.test.util.JwtUtil;

@RestController
public class TicketController {
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	
	 @Autowired
	 private MyUserDetailsService userDetailsService;
	
	 @Autowired
	 private JwtUtil jwtTokenUtil;

	 private TicketService ticketService;
	 
	 public TicketController(TicketService ticketService) {
		 this.ticketService = ticketService;
	 }
	 
	 @PostMapping("/authenticate")
	 public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		
		 try {
			 authenticationManager.authenticate(
				 new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())	
			 );
		 }catch(BadCredentialsException e) {
			 throw new Exception("Incorrect Username or password" , e);
		 }
		
		 final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		 final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		 return ResponseEntity.ok(new AuthenticationResponse(jwt));
	 }
	 
	 @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	 @PostMapping("/createTicket")
	 public Ticket createTicket(@RequestBody Ticket ticket) {
		 return ticketService.createTicket(ticket);
	 }
	 
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @GetMapping("/ticket/{ticketId}")
	 public Ticket getTicketById(@PathVariable("ticketId")Integer ticketId) {
		 return ticketService.getTicketById(ticketId);
	 }
	 
	 @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	 @GetMapping("/allTickets")
	 public List<Ticket> getAllTickets(){
		 return ticketService.getAllTickets();
	 }
	 
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @PutMapping("/ticket/{ticketId}")
	 public Ticket updateTicket(@RequestBody Ticket ticket, @PathVariable("ticketId") Integer ticketId) {
		 return ticketService.updateTicket(ticket, ticketId);
	 }
	 
	 @PreAuthorize("hasAuthority('ADMIN')")
	 @DeleteMapping("/ticket/{ticketId}")
	 public void deleteTicket(@PathVariable("ticketId")Integer ticketId) {
		 ticketService.deleteTicket(ticketId);
	 }
}
