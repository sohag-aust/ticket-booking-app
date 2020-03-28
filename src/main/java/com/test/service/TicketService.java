package com.test.service;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.test.entities.Ticket;
import com.test.repositories.TicketDao;

@Service
public class TicketService {

	private TicketDao ticketDao;
	
	public TicketService(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}
	
	public Ticket createTicket(Ticket ticket) {
		return ticketDao.save(ticket);
	}

	public Ticket getTicketById(Integer ticketId) {
		return ticketDao.findById(ticketId).orElse(null);
	}

	public List<Ticket> getAllTickets() {
		return ticketDao.findAll();
	}

	public void deleteTicket(Integer ticketId) {
		ticketDao.deleteById(ticketId);
	}

	public Ticket updateTicket(Ticket newTicket, Integer ticketId) {
		Ticket oldTicket = ticketDao.findById(ticketId) .orElse(null);
		//oldTicket.setTicketId(newTicket.getTicketId());
		oldTicket.setBookingDate(newTicket.getBookingDate());
		oldTicket.setDestStation(newTicket.getDestStation());
		oldTicket.setEmail(newTicket.getEmail());
		oldTicket.setPassengerName(newTicket.getPassengerName());
		oldTicket.setSourceStation(newTicket.getSourceStation());
		
		//ticketDao.deleteById(ticketId);
		return ticketDao.save(oldTicket);
	}
}
