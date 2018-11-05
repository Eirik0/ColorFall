package cf.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cf.gameentity.FpsTracker;
import cf.gamestate.GameState;
import cf.util.GameConstants;

public class GameDelegate implements Runnable, KeyListener {
    private static final double TARGET_FPS = 60;

    private GameState currentState;
    private GameState pendingState;

    private BufferedImage gameImage;
    private Graphics2D gameGraphics;
    private final FpsTracker fpsTracker;

    public final JPanel gamePanel;

    public GameDelegate() {
        gamePanel = new JPanel();
        gamePanel.addKeyListener(this);

        fpsTracker = new FpsTracker();

        setSettings();
    }

    public void setSettings() {
        gameImage = new BufferedImage(GameSettings.componentWidth, GameSettings.componentHeight, BufferedImage.TYPE_INT_RGB);
        gameGraphics = gameImage.createGraphics();

        gamePanel.setPreferredSize(new Dimension(GameSettings.componentWidth, GameSettings.componentHeight));
    }

    public void setState(GameState state) {
        pendingState = state;
    }

    private void checkState() {
        if (currentState != pendingState) {
            currentState = pendingState;
            currentState.init();
        }
    }

    @Override
    public void run() {
        long lastloopStart = System.nanoTime();
        long loopStart = System.nanoTime();

        while (true) {
            loopStart = System.nanoTime();
            long dt = loopStart - lastloopStart;

            checkState();
            currentState.update(dt);

            checkState();
            currentState.drawOn(gameGraphics);

            fpsTracker.update(dt);
            if (GameSettings.showFps) {
                fpsTracker.drawOn(gameGraphics);
            }

            Graphics g = gamePanel.getGraphics();
            g.drawImage(gameImage, 0, 0, null);
            g.dispose();

            double timeToSleep = GameConstants.ONE_SECOND / TARGET_FPS - (System.nanoTime() - loopStart);
            if (timeToSleep > 0) {
                fpsTracker.addTimeSleeping(timeToSleep);
                try {
                    Thread.sleep(Math.round(timeToSleep / GameConstants.ONE_MILLISECOND));
                } catch (InterruptedException e) {
                }
            }

            lastloopStart = loopStart;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentState.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}