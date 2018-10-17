package com.ringcentral.service;

import com.ringcentral.domain.Card;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameServiceTester {
    @Test
    public void testScore() {
        List<Card> playerCards = new ArrayList<>();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_A));
        Assert.assertEquals(12, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_J));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_A));
        Assert.assertEquals(21, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_J));
        playerCards.add(new Card(Card.CardSymbol.Diamond, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_A));
        Assert.assertEquals(12, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_J));
        playerCards.add(new Card(Card.CardSymbol.Diamond, Card.CardCour.CARD_10));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_A));
        Assert.assertEquals(21, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_J));
        playerCards.add(new Card(Card.CardSymbol.Diamond, Card.CardCour.CARD_10));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_10));
        Assert.assertEquals(30, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_2));
        playerCards.add(new Card(Card.CardSymbol.Diamond, Card.CardCour.CARD_3));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_Q));
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_K));
        Assert.assertEquals(25, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Heart, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Diamond, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Club, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_A));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_6));
        Assert.assertEquals(20, GameService.calcScore(playerCards));

        playerCards.clear();
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_2));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_3));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_4));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_5));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_6));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_7));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_8));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_9));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_10));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_J));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_K));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_Q));
        playerCards.add(new Card(Card.CardSymbol.Spade, Card.CardCour.CARD_A));
        Assert.assertEquals(85, GameService.calcScore(playerCards));
    }
}
