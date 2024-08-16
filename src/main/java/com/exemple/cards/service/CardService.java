package com.exemple.cards.service;

import com.exemple.cards.model.Cards;
import com.exemple.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CardService {
    @Autowired
    private CardsRepository cardsRepository;

    public List<Cards> getAllCards(){
        List<Cards> allCards = new ArrayList<Cards>();
        cardsRepository.findAll().forEach(card -> allCards.add(card));
        return allCards;
    }

    public Cards getCardById(int id){
        return cardsRepository.findById(id).get();
    }

    public String saveCard(Cards card){

        // need to check the customer exist in the future livrables

        card.setCreateDt(LocalDate.now());
        cardsRepository.save(card);
        return "saved";
    }

    public String deleteCard(int id){
        Cards cardFind = cardsRepository.findById(id).orElse(null);
        if(cardFind != null) {
            cardsRepository.deleteById(id);
            return "deleted!";
        }else {
            return "card not found!";
        }
    }

    public String updateCard(int id, Cards updateCard){
        Cards cardFind = cardsRepository.findById(id).orElse(null);
        if(cardFind != null){
            updateCard.setCardId(id);
            updateCard.setCreateDt(LocalDate.now());
            cardsRepository.save(updateCard);
            return "update successful!";
        }else {
            return "card not found!";
        }
    }



}
