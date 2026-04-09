import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public  class MyPanel extends JPanel{

    private Image imgBomba;

    public List<Bomba> bombeAttive = new ArrayList<>();

    public MyPanel(){
        Toolkit tk = Toolkit.getDefaultToolkit();
        imgBomba = tk.getImage(TOOL_TIP_TEXT_KEY);
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(imgBomba, WIDTH);
        try {
            mt.waitForAll();
        } catch (InterruptedException e) {

        }
    }
}