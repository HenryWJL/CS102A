package model;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.event.ChangeListener;

import static javax.swing.UIManager.setLookAndFeel;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private  final int WIDTH;
    private  final int HEIGTH;
    private GameController gameController;
    private int cnt = 30;
    private int ChessboardSize;
    ChessColor currentColor;
    int counter;
    ImageIcon background = new ImageIcon("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\starsky.png");
    JLabel label = new JLabel(background);
    JPanel panel = (JPanel) getContentPane();


    public ChessGameFrame(int width, int height) throws IOException {
        setTitle("Chess"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ChessboardSize = HEIGTH * 4 / 5;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        label.setSize(background.getIconWidth(), background.getIconHeight());
        getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        panel.setOpaque(false);
        panel.setLayout(null);

        Chessboard chessboard = new Chessboard(ChessboardSize+276 , ChessboardSize+100 );
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 20);
        panel.add(chessboard);
        currentColor = chessboard.getCurrentColor();



        /*addComponentListener(new ComponentAdapter() {//窗口大小变化
            @Override
            public void componentResized(ComponentEvent e) {
                int width=getWidth();
                int height=getHeight();
                chessboard.setBounds(width/10,height/10,height*4/5+276,height*4/5+100);
                panel.setBounds(width/10,height/10,height*4/5+276,height*4/5+100);
                label.setSize(width,height);
            }
        });*/
    }

}