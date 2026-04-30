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
                int bombSpeed = (int)(Math.random()*9+1);
                int startingY = 3;
                
                int numeroCasuale = (int)(Math.random()*10);
                if(numeroCasuale == 2){
                    spawnPowerup(spawnX, startingY, bombSpeed);
                }else if(numeroCasuale == 3){
                    spawnEsplosivo(spawnX, startingY, bombSpeed);
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
        Bomba bomba = new Bomba(x, startingY, speed, 600, panel);
        panel.bombeAttive.add(bomba);
    }

    private synchronized void spawnPowerup(int x, int startingY, int speed){
        PowerUp powerUp = new PowerUp(x, startingY, speed, 630, panel);
        panel.powerUpAttivi.add(powerUp);
    }

    private synchronized void spawnEsplosivo(int x, int startingY, int speed){
        Esplosivo esplosivo = new Esplosivo(x, startingY, speed, 600, panel);
        panel.esplosiviAttivi.add(esplosivo);
    }

    public synchronized void modalità(){
        if(gameOver){
            gameOver = false;
        }else{
            gameOver = true;
        }
    }
}