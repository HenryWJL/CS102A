package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KnightChessComponent extends ChessComponent {
    private static Image Knight_WHITE;
    private static Image Knight_BLACK;
    private Image knightImage;

    public void loadResource() throws IOException {
        if (app==3) {
            Knight_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\jd.png"));
            Knight_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\gt.png"));

        }
        else if(app==2){
            Knight_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\13.png"));
            Knight_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\22.png"));

        }
        else{
            Knight_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\knight-white.png"));
            Knight_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\knight-black.png"));
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        Knight_WHITE=ImageIO.read(new File(imageFile1));
        Knight_BLACK=ImageIO.read(new File(imageFile2));
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage=Knight_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage=Knight_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiateKnightImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(destination.getX()-source.getX())==2&&Math.abs(destination.getY()-source.getY())==1||Math.abs(destination.getX()-source.getX())==1&&Math.abs(destination.getY()-source.getY())==2) {
            ;
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}

