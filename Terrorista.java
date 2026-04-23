import java.util.ArrayList;
import java.util.List;

public class Terrorista{
    int spawnRate;
    boolean gameOver;
    Thread osama;
    MyPanel panel;

    public Terrorista(int spawnRate, boolean gameOver, MyPanel panel){
        this.spawnRate = spawnRate;
        this.gameOver = gameOver;
        this.panel = panel;
        bombarda(spawnRate);
    }

    private synchronized void bombarda(int spawnRate){
        osama = new Thread(()->{
            while(!gameOver){
                int spawnX = (int)(Math.random()*550);
                int bombSpeed = (int)(Math.random()*10);
                int startingY = 3;
                
                int numeroCasuale = (int)(Math.random()*10);
                if(numeroCasuale == 1){
                    spawnPowerup(spawnX, startingY, bombSpeed);
                }else{
                    spawnBomba(spawnX, startingY, bombSpeed);
                }

                try {
                    Thread.sleep(spawnRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        osama.start();
    }

    private synchronized void spawnBomba(int x, int startingY, int speed){
        Bomba bomba = new Bomba(x, startingY, speed, 700, panel);
        panel.bombeAttive.add(bomba);
    }

    private synchronized void spawnPowerup(int x, int startingY, int speed){
        PowerUp powerUp = new PowerUp(x, startingY, speed, 700, panel);
        panel.powerUpAttivi.add(powerUp);
    }

    public synchronized void modalità(){
        if(gameOver){
            gameOver = false;
        }else{
            gameOver = true;
        }
    }
}