package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KingChessComponent extends ChessComponent{
    private static Image King_WHITE;
    private static Image King_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (app==3) {
            King_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\dls.png"));
            King_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\hu.png"));

        }
        else if(app==2){
            King_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\12.png"));
            King_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\23.png"));

        }
        else{
            King_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\king-white.png"));
            King_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage=King_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage=King_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        King_WHITE=ImageIO.read(new File(imageFile1));
        King_BLACK=ImageIO.read(new File(imageFile2));
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiateKingImage(color);
    }



    public boolean isBesideKing(ChessComponent[][] chessComponents,int x,int y){
        if(x==0&&y>0&&y<7){
            if((chessComponents[x+1][y]instanceof KingChessComponent&&!chessComponents[x+1][y].getChessColor().equals(chessColor))||(chessComponents[x][y+1]instanceof KingChessComponent&&!chessComponents[x][y+1].getChessColor().equals(chessColor))||
                    (chessComponents[x][y-1]instanceof KingChessComponent&&!chessComponents[x][y-1].getChessColor().equals(chessColor))){
                return true;
            }
            if((chessComponents[x+1][y-1]instanceof KingChessComponent&&!chessComponents[x+1][y-1].getChessColor().equals(chessColor))||(chessComponents[x+1][y+1]instanceof KingChessComponent&&!chessComponents[x+1][y+1].getChessColor().equals(chessColor))){
                return true;
            }
        }
        else if(x>0&&y==0&&x<7){
            if((chessComponents[x+1][y]instanceof KingChessComponent&&!chessComponents[x+1][y].getChessColor().equals(chessColor))||(chessComponents[x-1][y]instanceof KingChessComponent&&!chessComponents[x-1][y].getChessColor().equals(chessColor))||
                    (chessComponents[x][y+1]instanceof KingChessComponent&&!chessComponents[x][y+1].getChessColor().equals(chessColor))){
                return true;
            }
            if((chessComponents[x-1][y+1]instanceof KingChessComponent&&!chessComponents[x-1][y+1].getChessColor().equals(chessColor))||(chessComponents[x+1][y+1]instanceof KingChessComponent&&!chessComponents[x+1][y+1].getChessColor().equals(chessColor))){
                return true;
            }
        }
        else if(x==7&&y>0&&y<7){
            if((chessComponents[x-1][y]instanceof KingChessComponent&&!chessComponents[x-1][y].getChessColor().equals(chessColor))||(chessComponents[x][y+1]instanceof KingChessComponent&&!chessComponents[x][y+1].getChessColor().equals(chessColor))||
                    (chessComponents[x][y-1]instanceof KingChessComponent&&!chessComponents[x][y-1].getChessColor().equals(chessColor))){
                return true;
            }
            if((chessComponents[x-1][y-1]instanceof KingChessComponent&&!chessComponents[x-1][y-1].getChessColor().equals(chessColor))||(chessComponents[x-1][y+1]instanceof KingChessComponent&&!chessComponents[x-1][y+1].getChessColor().equals(chessColor))){
                return true;
            }
        }
        else if(y==7&&x>0&&x<7){
            if((chessComponents[x+1][y]instanceof KingChessComponent&&!chessComponents[x+1][y].getChessColor().equals(chessColor))||(chessComponents[x][y-1]instanceof KingChessComponent&&!chessComponents[x][y-1].getChessColor().equals(chessColor))||
                    (chessComponents[x-1][y]instanceof KingChessComponent&&!chessComponents[x-1][y].getChessColor().equals(chessColor))){
                return true;
            }
            if((chessComponents[x+1][y-1]instanceof KingChessComponent&&!chessComponents[x+1][y-1].getChessColor().equals(chessColor))||(chessComponents[x-1][y-1]instanceof KingChessComponent&&!chessComponents[x-1][y-1].getChessColor().equals(chessColor))){
                return true;
            }
        }
        else if(x>0&&y>0&&x<7&&y<7){
            if((chessComponents[x+1][y]instanceof KingChessComponent&&!chessComponents[x+1][y].getChessColor().equals(chessColor))||(chessComponents[x][y+1]instanceof KingChessComponent&&!chessComponents[x][y+1].getChessColor().equals(chessColor))||
                    (chessComponents[x][y-1]instanceof KingChessComponent&&!chessComponents[x][y-1].getChessColor().equals(chessColor))||(chessComponents[x-1][y]instanceof KingChessComponent&&!chessComponents[x-1][y].getChessColor().equals(chessColor))){
                return true;
            }
            if((chessComponents[x+1][y-1]instanceof KingChessComponent&&!chessComponents[x+1][y-1].getChessColor().equals(chessColor))||(chessComponents[x+1][y+1]instanceof KingChessComponent&&!chessComponents[x+1][y+1].getChessColor().equals(chessColor))
            ||(chessComponents[x-1][y+1]instanceof KingChessComponent&&!chessComponents[x-1][y+1].getChessColor().equals(chessColor))||(chessComponents[x-1][y-1]instanceof KingChessComponent&&!chessComponents[x-1][y-1].getChessColor().equals(chessColor))){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
            if(Math.abs(source.getY() - destination.getY()) != 1||isBesideKing(chessComponents,destination.getX(),destination.getY())){
                return false;
            }
        }
        else if(source.getY() == destination.getY()) {
            if(Math.abs(source.getX() - destination.getX()) != 1||isBesideKing(chessComponents,destination.getX(),destination.getY())){
                return false;
            }
        }
        else if(Math.abs(destination.getX()-source.getX()) ==Math.abs(destination.getY()-source.getY())){
            if(Math.abs(destination.getX() - source.getX()) != 1 ||isBesideKing(chessComponents,destination.getX(),destination.getY())){
                return false;
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
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}


