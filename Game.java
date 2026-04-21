import java.awt.*;
import javax.swing.*;

public class Game{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Falling Bombs");
        frame.setSize(600, 800);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Quando chiudo la pagina-->si chiude il programma

        MyPanel panel = new MyPanel();
        frame.add(panel);
        Container c = frame.getContentPane();
        c.add(panel);

        Terrorista terrorista = new Terrorista(10, false, panel);

        frame.show();
    }
}