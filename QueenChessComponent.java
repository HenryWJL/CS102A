package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class QueenChessComponent extends ChessComponent{
    private static Image Queen_WHITE;
    private static Image Queen_BLACK;
    private Image queenImage;

    public void loadResource() throws IOException {
        if (app==3) {
            Queen_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\xn.png"));
            Queen_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\ye.png"));

        }
        else if(app==2){
            Queen_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\11.png"));
            Queen_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\24.png"));

        }
        else {
            Queen_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\queen-white.png"));
            Queen_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\queen-black.png"));
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        Queen_WHITE=ImageIO.read(new File(imageFile1));
        Queen_BLACK=ImageIO.read(new File(imageFile2));
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage=Queen_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage=Queen_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiateQueenImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(destination.getX()-source.getX()) ==Math.abs(destination.getY()-source.getY())) {
            //judge if there's other chess blocking
            if(source.getX()<destination.getX()&&source.getY()<destination.getY()||source.getX()>destination.getX()&&source.getY()>destination.getY()){
                for (int col = Math.min(source.getY(), destination.getY()) + 1,row=Math.min(source.getX(), destination.getX()) + 1;
                     col < Math.max(source.getY(), destination.getY()); col++,row++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            else if(source.getX()>destination.getX()&&source.getY()<destination.getY()){
                for(int row=source.getX()-1,col=source.getY()+1;col<destination.getY();row--,col++){
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            else if(source.getX()<destination.getX()&&source.getY()>destination.getY()){
                for(int row=destination.getX()-1,col=destination.getY()+1;col<source.getY();row--,col++){
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        }
        else if(source.getX() == destination.getX()) {
            //judge if there's other chess blocking
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        }
        else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
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
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}

