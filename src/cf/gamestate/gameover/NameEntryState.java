package cf.gamestate.gameover;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import cf.gameentity.score.GameScore;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;

public class NameEntryState implements GameState {
    private final GameScore score;

    private String name = "";

    private int width;
    private int height;

    public NameEntryState(GameScore score) {
        this.score = score;
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void drawOn(Graphics2D graphics) {
        fillRect(graphics, 0, 0, width, height, ComponentCreator.backgroundColor());

        graphics.setColor(Color.GREEN);
        graphics.setFont(ColorFall.GAME_FONT_LARGE);
        drawCenteredString(graphics, score.toString(), width / 2, 100);

        drawCenteredString(graphics, "Enter Name:", width / 2, 200);

        graphics.setFont(ColorFall.GAME_FONT);
        drawCenteredString(graphics, name + "_", width / 2, 250);
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void handleUserInput(UserInput input) {
        switch (input) {
        case BACK_SPACE_KEY_PRESSED:
            name = name.substring(0, Math.max(0, name.length() - 2));
            break;
        case ENTER_KEY_PRESSED:
            if (name.trim().length() > 0) {
                HighScores highScores = HighScores.loadFromFile();
                highScores.addHighScore(new HighScore(name, score.getScore(), score.getLevel(), score.getCaptures(), round(score.getTime())));
                try {
                    highScores.saveToFile();
                } catch (IOException e) {
                }
                GameStateManager.setGameState(new HighScoresState(highScores));
            }
            break;
        default:
            Character maybeCharacter = UserInput.toAscii(input);
            if (maybeCharacter != null) {
                name += maybeCharacter;
            }
            break;
        }
    }
}
