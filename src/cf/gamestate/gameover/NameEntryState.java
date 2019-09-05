package cf.gamestate.gameover;

import java.awt.Color;

import cf.gameentity.score.GameScore;
import cf.gamestate.colorfall.BouncingPolygon;
import cf.main.ColorFall;
import gt.component.ComponentCreator;
import gt.gameentity.IGraphics;
import gt.gamestate.GameState;
import gt.gamestate.GameStateManager;
import gt.gamestate.UserInput;
import gt.util.EMath;

public class NameEntryState implements GameState {
    private final GameStateManager gameStateManager;
    private final GameScore score;
    private final HighScores highScores;
    private final BouncingPolygon bouncingPolygon;

    private String name = "";

    private double width;
    private double height;

    public NameEntryState(GameStateManager gameStateManager, GameScore score, HighScores highScores, BouncingPolygon bouncingPolygon) {
        this.gameStateManager = gameStateManager;
        this.score = score;
        this.highScores = highScores;
        this.bouncingPolygon = bouncingPolygon;
    }

    @Override
    public void update(double dt) {
        bouncingPolygon.update(dt);
    }

    @Override
    public void drawOn(IGraphics g) {
        g.fillRect(0, 0, width, height, ComponentCreator.backgroundColor());
        bouncingPolygon.drawOn(g);

        g.setColor(Color.GREEN);
        g.setFont(ColorFall.GAME_FONT_LARGE);
        g.drawCenteredXString(score.toString(), width / 2, 100);

        g.setFont(ColorFall.GAME_FONT);
        g.drawCenteredXString("Enter Name:", width / 2, 200);

        g.setFont(ColorFall.GAME_FONT_LARGE);
        g.drawCenteredXString(name + "_", width / 2, 275);
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void handleUserInput(UserInput input) {
        switch (input) {
        case BACK_SPACE_KEY_PRESSED:
            name = name.substring(0, Math.max(0, name.length() - 1));
            break;
        case ENTER_KEY_PRESSED:
            if (name.trim().length() > 0) {
                highScores.addHighScore(new HighScore(name, score.getScore(), score.getLevel(), score.getCaptures(), EMath.round(score.getTime())));
                highScores.saveToFile();
                gameStateManager.setGameState(new HighScoresState(gameStateManager, highScores));
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
