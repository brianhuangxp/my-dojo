package com.ringcentral.service;

import com.ringcentral.domain.BlackJackPlayer;
import com.ringcentral.domain.Card;
import com.ringcentral.domain.GameStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class GameService {
    public static int MAX_JOIN_PLAYER = 8;
    public static int OVER_FLOW_VALUE = 21;

    private List<BlackJackPlayer> playerList;
    private List<Card> cardPool;
    private GameStatus status;
    private GameDispatchService dispatch;
    BlackJackPlayer currentPlayer;

    public GameService(GameDispatchService dispatch) {
        this.dispatch = dispatch;
    }

    public void resetGame(boolean resetPlayer) {
        if (resetPlayer) {
            playerList = new ArrayList<>();
            statuesOnChange(GameStatus.INITIAL);
        } else {
            playerList.forEach(player -> player.resetGame());
        }
    }

    public void joinPlayer(BlackJackPlayer player) {
        if (playerList.size() >= MAX_JOIN_PLAYER) {
            throw new RuntimeException(String.format("There are more than %s players. Please join other games.", MAX_JOIN_PLAYER));
        }
        if (playerList.stream().filter(p -> p.getName().equals(player.getName())).count() >= 1) {
            throw new RuntimeException(String.format("%s has join this game.", player.getName()));
        }
        player.resetGame();
        player.welcome();
        playerList.add(player);
    }

    private void showPlayers() {
        StringJoiner sj = new StringJoiner(", ");
        playerList.forEach(player -> sj.add(player.getName()));
        System.out.println(String.format("GameService start! There are %s Players: %s", playerList.size(), sj.toString()));
    }

    public void showResult() {
        if(!status.isCompeted()) {
            throw new RuntimeException("Invalid status");
        }

        playerList.forEach(player -> player.showResult());
        List<BlackJackPlayer> hasScorePlayerList = playerList.stream().filter(player -> player.getScore() <= OVER_FLOW_VALUE)
                .sorted(Comparator.comparing(BlackJackPlayer::getScore)).collect(Collectors.toList());
        if (hasScorePlayerList.isEmpty()) {
            System.out.println("All of them lost.");
            return;
        }

        int maxScore = hasScorePlayerList.stream().mapToInt(player -> player.getScore()).max().getAsInt();
        StringJoiner sj = new StringJoiner(", ");
        hasScorePlayerList.stream().filter(player -> player.getScore() == maxScore).forEach(player -> {
            sj.add(player.getName());
        });
        System.out.println(String.format("Congratulate, %s, you win.", sj.toString()));
    }


    public void startGame() {
        if (playerList.size() <= 1) {
            throw new RuntimeException("The gameService should be more than 1 player!");
        }

        statuesOnChange(GameStatus.STARTING);
        showPlayers();
        cardPool = new ArrayList<>();

        List<Card> cardList = new ArrayList<>();
        for (Card.CardSymbol symbol : Card.CardSymbol.values()) {
            for (Card.CardCour cour : Card.CardCour.values()) {
                cardList.add(new Card(symbol, cour));
            }
        }

        while (cardList.size() > 0) {
            int randomIdx = Double.valueOf(Math.random() * cardList.size()).intValue();
            this.cardPool.add(cardList.remove(randomIdx));
        }

        System.out.println("");//todo
        playerList.forEach(player -> {
            sendCard(player);
            player.showCards();
        });
        System.out.println();
        playerList.forEach(player -> {
            sendCard(player);
            player.showCards();
        });

        statuesOnChange(GameStatus.SEND_CARD);
    }

    public void sendCard(BlackJackPlayer player) {
        if (player.isRequestCard()) {
            List<Card> playerCards = player.receiveCard(cardPool.remove(0));
            int score = calcScore(playerCards);
            player.setScore(score);
            try {
                Thread.sleep(200l); //delay 100ms as effect to display
            } catch (InterruptedException e) {
            }
        }
    }

     static int calcScore(List<Card> playerCards) {
        int score = playerCards.stream().mapToInt(card -> card.getCour().getValue()).sum();
        if (isIncludeAceCard(playerCards) && isAceEnough(score)) {
            score += 10;
        }

        return score;
    }

    private static boolean isAceEnough(int score) {
        return OVER_FLOW_VALUE - score >= 10;
    }

    static boolean isIncludeAceCard(List<Card> playerCards) {
        return playerCards.stream().anyMatch(card -> card.getCour().isCardA());
    }

    public void statuesOnChange(GameStatus status) {
        if (this.status != status) {
            this.status = status;
            System.out.println();
            dispatch.showDescription();
        }
    }

    public boolean hasNextPlayer() {
        return playerList.stream().filter(player -> player.getCardList().size() < 2 || player.isRequestCard()).count() > 0;
    }

    public BlackJackPlayer nextPlayer() {
        if (currentPlayer == null) {
            currentPlayer = playerList.get(0);
            return currentPlayer;
        }

        int idx = playerList.indexOf(currentPlayer);

        boolean next = true;
        while (next) {
            idx++;
            if (idx >= playerList.size()) {
                idx = 0;
            }
            BlackJackPlayer player = playerList.get(idx);
            if (player.isRequestCard()) {
                currentPlayer = player;
                return currentPlayer;
            } else {
                next = hasNextPlayer();
            }
        }
        return null;
    }

    public GameStatus getStatus() {
        return status;
    }

    public BlackJackPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
