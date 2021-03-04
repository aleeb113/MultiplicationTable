import javax.swing.*;
import java.awt.*;

public class MultiplicationTableStarter {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
         MultiplicationTableFrame frame = new MultiplicationTableFrame();
         frame.setVisible(true);
         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         frame.setLocationRelativeTo(null);
        });
    }
}
