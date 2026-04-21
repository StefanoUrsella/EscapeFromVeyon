import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

public  class MyPanel extends JPanel{

    private Image imgBomba;

    public List<Bomba> bombeAttive = new CopyOnWriteArrayList<>();

    public MyPanel(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        imgBomba = tk.getImage(bomba.png);
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(imgBomba, WIDTH);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Bomba b : bombeAttive){
            g.drawImage(imgBomba, b.getX(), b.getY(), null);
        }
    }
}