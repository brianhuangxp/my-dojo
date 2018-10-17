package com.ringcentral.domain;

import com.ringcentral.service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class BlackJackPlayer extends Player {
    private List<Card> cardList;
    private boolean requestCard;
    private int score;

    public BlackJackPlayer(String name) {
        super(name);
        this.cardList = new ArrayList<>();
    }

    public boolean isRequestCard() {
        return requestCard && !isOverFlow();
    }

    public void setRequestCard(boolean requestCard) {
        this.requestCard = requestCard;
    }

    public boolean isOverFlow() {
        return score > GameService.OVER_FLOW_VALUE;
    }

    public List<Card> receiveCard(Card card) {
        cardList.add(card);
        return cardList;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        if (isOverFlow()) {
            setRequestCard(false);
            System.out.println(String.format("Your card has been over flow. Score: %s", score));
        }
    }

    public void resetGame() {
        cardList = new ArrayList<>();
        requestCard = true;
        score = 0;
    }

    public void welcome() {
        System.out.println(String.format("welcome %s join the game!", getName()));
    }

    public void showCards() {
        StringJoiner sj = new StringJoiner(", ");
        getCardList().forEach(card -> {
            sj.add(card.toString());
        });
        System.out.println(String.format("%s: %s", getName(), sj.toString()));
    }

    public void showResult() {
        StringJoiner sj = new StringJoiner(", ");
        getCardList().forEach(card -> {
            sj.add(card.toString());
        });
        System.out.println(String.format("%s: %s, score: %s", getName(), sj.toString(), score));
    }
}
