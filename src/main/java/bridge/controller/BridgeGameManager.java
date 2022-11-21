package bridge.controller;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.config.NumberGeneratorDependencyContainer;
import bridge.service.BridgeGame;
import bridge.view.InputView;
import bridge.view.OutputView;
import bridge.vo.GameProgressMessage;
import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class BridgeGameManager {

    private InputView inputView;
    private OutputView outputView;
    private List<StringBuilder> currentBridgeStatus;
    private BridgeGame bridgeGame;
    private BridgeNumberGenerator bridgeNumberGenerator;

    public BridgeGameManager(NumberGeneratorDependencyContainer numberGeneratorDependencyContainer) {
        inputView = new InputView();
        outputView = new OutputView();
        bridgeNumberGenerator = numberGeneratorDependencyContainer.bridgeNumberGenerator();
    }

    public void setUpGame() {
        outputView.printGameProgressMessage(GameProgressMessage.GAME_START_INPUT_LENGTH_MESSAGE);

        int length = inputView.readBridgeSize();

        List<String> createdBridge
                = new BridgeMaker(bridgeNumberGenerator).makeBridge(length);

        bridgeGame = new BridgeGame(createdBridge);
    }

    public void move() {
        while (!bridgeGame.isUserDead() && !bridgeGame.checkPlayerCrossedAllBridge()) {
            outputView.printGameProgressMessage(GameProgressMessage.MOVE_MESSAGE);

            String direction = inputView.readMoving();
            currentBridgeStatus = bridgeGame.move(direction);
            outputView.printMap(currentBridgeStatus);
        }
    }

    public void printResult() {
        long gameTryCount = bridgeGame.getGameTryCount();

        String gameResultMessage = checkGameResultMessage();
        outputView.printResult(currentBridgeStatus, gameTryCount, gameResultMessage);
    }

    public String checkGameResultMessage() {
        final String SUCCESS_MESSAGE = "성공";
        final String FAIL_MESSAGE = "실패";

        if(bridgeGame.checkPlayerCrossedAllBridge()) {
            return SUCCESS_MESSAGE;
        }

        return FAIL_MESSAGE;
    }

    public boolean GameKeepGoingOrNot() {
        if(!bridgeGame.checkPlayerCrossedAllBridge()) {
            System.out.println(GameProgressMessage.GAME_RETRY_MESSAGE);
            String input = Console.readLine();

            return bridgeGame.retry(input);
        }
        return false;
    }

    public void exceptionalGameEnd(String errorMessage) {
        outputView.printErrorMessage(errorMessage);
    }
}
