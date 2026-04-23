public class Giocatore implements Runnable{//Serve per usare "run()"   (Gemini)
    private int x;
    private int y;
    private int speed;
    private int minX = 0;
    private int maxX = 600;
    private MyPanel panel;
    private boolean sinistra;
    private boolean destra;
    private boolean gameOver;

    public Giocatore(int x, int y, int speed, MyPanel panel){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.panel = panel;

        sinistra = false;
        destra = false;

        gameOver = false;

        new Thread(this).start();//Creo un thread di questo oggetto-->lo Starto   (Gemini)
    }

    @Override
    public void run() {//Esegue il thread in parallelo al resto della classe   (Gemini)
        while(gameOver == false){
            if(destra == true){
                sinistra = false;
                if(x+speed <= maxX){
                    x += speed;
                }
            }

            if(sinistra == true){
                destra = false;
                if(x-speed >= minX){
                    x -= speed;
                }
            }

            try{
                Thread.sleep(100);//Tempo che aspetta prima di muoversi di nuovo
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setDestra(boolean direzione){
        destra = direzione;
    }

    public void setSinistra(boolean direzione){
        sinistra = direzione;
    }
}