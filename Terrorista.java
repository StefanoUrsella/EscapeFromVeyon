public class Terrorista{
    int spawnRate;
    boolean gameOver;
    Thread osama;

    public Terrorista(int spawnRate, boolean gameOver){
        this.spawnRate = spawnRate;
        this.gameOver = gameOver;
        bombarda(spawnRate);
    }

    private synchronized void bombarda(int spawnRate){
        osama = new Thread(()->{
            while(!gameOver){
                int spawnX = (int)(Math.random()*10);
                int bombSpeed = (int)(Math.random()*10);
                spawnBomba(spawnX, bombSpeed);
                try {
                    Thread.sleep(spawnRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        osama.start();
    }

    private synchronized void spawnBomba(int x, int speed){
        Bomba bomba = new Bomba(x, speed);
    }

    public synchronized void modalità(){
        if(gameOver){
            gameOver = false;
        }else{
            gameOver = true;
        }
    }
}