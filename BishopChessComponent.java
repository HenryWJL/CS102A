package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class BishopChessComponent extends ChessComponent{
    private static Image Bishop_WHITE;
    private static Image Bishop_BLACK;
    private Image bishopImage;

    public void loadResource() throws IOException {
        if (app==3) {
            Bishop_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\hq.png"));
            Bishop_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\tr.png"));
        }
        else if(app==2){
            Bishop_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\16.png"));
            Bishop_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\21.png"));
        }
        else {
            Bishop_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\bishop-white.png"));
            Bishop_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\bishop-black.png"));
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        Bishop_WHITE=ImageIO.read(new File(imageFile1));
        Bishop_BLACK=ImageIO.read(new File(imageFile2));
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage=Bishop_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage=Bishop_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiateBishopImage(color);
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
        else { // Not on the same diagonal.
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}



