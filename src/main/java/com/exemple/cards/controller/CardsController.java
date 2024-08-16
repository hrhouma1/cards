/**
 * 
 */
package com.exemple.cards.controller;

import java.util.List;

import com.exemple.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.exemple.cards.model.Cards;
import com.exemple.cards.model.Customer;
import com.exemple.cards.repository.CardsRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class CardsController {

	@Autowired
	private CardService cardService;


	@Operation(summary = "get card by id")
	@GetMapping("/myCard/{id}")
	public Cards getCardDetails(@PathVariable("id") int id) {
		return cardService.getCardById(id);
	}


	@Operation(summary = "get all cards")
	@GetMapping("/AllCards")
	public List<Cards> getAllCards(){
		return cardService.getAllCards();
	}

	@Operation(summary = "create a new card")
	@PostMapping("/newCard")
	public String newCard(@RequestBody Cards card){
		return cardService.saveCard(card);
	}

	@Operation(summary = "update card by id")
	@PutMapping("/update/{id}")
	public String updateCard(@PathVariable("id") int id, @RequestBody Cards updateCard) {
		return cardService.updateCard(id, updateCard);
	}

	@Operation(summary = "delete card by id")
	@DeleteMapping("/deleteCard/{id}")
	public String deleteCard(@PathVariable("id") int id){
		return cardService.deleteCard(id);
	}

//	@PostMapping("/myCards")
//	public List<Cards> getCardDetails(@RequestBody Customer customer) {
//		List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
//		if (cards != null) {
//			return cards;
//		} else {
//			return null;
//		}
//
//	}

}
