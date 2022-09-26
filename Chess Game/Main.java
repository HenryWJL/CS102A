package model;

import model.ChessGameFrame;
import javax.swing.*;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
        public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame ChessGameFrame = new JFrame();
            ChessGameFrame.setTitle("Chess Game");
            ChessGameFrame.setSize(1000, 760);
            ChessGameFrame.setLocationRelativeTo(null); // Center the window.
            ChessGameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
            ChessGameFrame.setLayout(null);
            ChessGameFrame.setVisible(true);

            ImageIcon background = new ImageIcon("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\starsky.png");
            JLabel label = new JLabel(background);
            JPanel panel = (JPanel) ChessGameFrame.getContentPane();

            label.setSize(background.getIconWidth(), background.getIconHeight());
            ChessGameFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
            panel.setOpaque(false);
            panel.setLayout(null);

            ChessGameFrame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                }
                @Override
                public void windowClosing(WindowEvent e) {
                    FileWriter fileWriter= null;
                    try {
                        fileWriter = new FileWriter("C:\\Users\\86136\\savefile\\game0.txt");
                        fileWriter.write("");
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                @Override
                public void windowClosed(WindowEvent e) {
                }
                @Override
                public void windowIconified(WindowEvent e) {

                }
                @Override
                public void windowDeiconified(WindowEvent e) {
                }
                @Override
                public void windowActivated(WindowEvent e) {
                }
                @Override
                public void windowDeactivated(WindowEvent e) {
                }
            });

            Chessboard chessboard = null;
            try {
                chessboard = new Chessboard(884, 708);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameController gameController = new GameController(chessboard);
            chessboard.setLocation(76, 38);
            panel.add(chessboard);

        });
    }
}
