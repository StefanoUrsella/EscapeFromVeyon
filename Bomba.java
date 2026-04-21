public class Bomba implements Runnable{//Serve per usare "run()"   (Gemini)
    private int x;
    private int y;
    private int speed;
    private int maxY;
    private MyPanel panel;
    private boolean attiva;

    public Bomba(int x, int y, int speed, int maxY, MyPanel panel){
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.maxY = maxY;
        this.panel = panel;
        attiva = true;
        System.out.println("A");

        new Thread(this).start();//Creo un thread di questo oggetto-->lo Starto   (Gemini)
    }

    @Override
    public void run() {//Esegue il thread in parallelo al resto della classe   (Gemini)
        while(attiva == true && y < maxY){
            y += speed;
            //panel.repaint();//Aggiorno il "MyPanel"

            try{
                Thread.sleep(100);//Tempo che aspetta prima di muoversi di nuovo
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }

        attiva = false;//Sono uscito dal while-->la bomba ha toccato terra-->fermo questa bomba
        panel.bombeAttive.remove(this);//Sono uscito dal while-->la bomba ha toccato terra-->tolgo quest abomba
    }

    public void distruggi(){
        attiva = false;
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

}