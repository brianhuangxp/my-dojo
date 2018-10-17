package com.ringcentral.command;

import com.ringcentral.domain.BlackJackPlayer;
import com.ringcentral.service.GameService;

import java.util.List;

public interface Command {
    void showDescription(GameService gameService);

    void commandDispatch(GameService gameService, String key, List<String> params);
}
