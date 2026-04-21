import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

public  class MyPanel extends JPanel{

    //private Image imgBomba; NON USO PIù L'IMMAGINE

    public List<Bomba> bombeAttive = new java.util.concurrent.CopyOnWriteArrayList<>();//Invece che cambiare dierettamente il valore della "ArrayList<>()"-->Fa una copia, Modifica la copia, Sostituisce l'originale con la Copia-->Sennò con i Tanti thread si incastrerebbe   (Gemini)

    public MyPanel(){
        /*Toolkit tk = Toolkit.getDefaultToolkit();
        imgBomba = tk.getImage("bomba.png");
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(imgBomba, WIDTH);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {

        }*/// NON USO PIù L'IMMAGINE

        int repaintRate = 50;

        //Thread che fà repaint ogni tot tempo-->Se lo metto dentro alle bombe avrei TROPPI thread che fanno repaint-->Si incarterebbe tutto   (Gemini)
        Thread austrianRepainter = new Thread(()->{
            while(true){

                repaint();

                try {
                    Thread.sleep(repaintRate);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        austrianRepainter.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Bomba b : bombeAttive){
            //g.drawImage(imgBomba, b.getX(), b.getY(), null); NON USO PIù L'IMMAGINE

            g.setColor(Color.RED);
            g.fillOval(b.getX(), b.getY(), 40, 40);
        }
    }
}