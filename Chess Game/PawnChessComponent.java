package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class PawnChessComponent extends ChessComponent{
    private static Image Pawn_WHITE;
    private static Image Pawn_BLACK;
    private Image pawnImage;

    public Image getPawnImage() {
        return pawnImage;
    }

    public void loadResource() throws IOException {
        if (app==3) {
            Pawn_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\xp.png"));
            Pawn_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\al.png"));

        }
        else if(app==2){
            Pawn_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\14.png"));
            Pawn_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\26.png"));

        }
        else{
            Pawn_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\pawn-white.png"));
            Pawn_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\pawn-black.png"));
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        Pawn_WHITE=ImageIO.read(new File(imageFile1));
        Pawn_BLACK=ImageIO.read(new File(imageFile2));
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage=Pawn_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage=Pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiatePawnImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (chessColor==ChessColor.BLACK) {
            if (chessboardPoint.getX()==1) {//第一步判断
                if (destination.getX() < source.getX()) {
                    return false;
                }
                else {
                    if (destination.getX() - source.getX() == 2) {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) || !(chessComponents[destination.getX()-1][destination.getY() ] instanceof EmptySlotComponent)) {
                                return false;
                            } else {
                                cn++;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    else if (destination.getX() - source.getX() == 1) {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        } else if (Math.abs(destination.getY() - source.getY()) == 1) {
                            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    else if(destination.getX()==source.getX()){
                        if (chessComponents[destination.getX()][destination.getY()] instanceof PawnChessComponent &&
                                chessComponents[destination.getX()][destination.getY()].getCn() == 2 &&
                                chessComponents[destination.getX()][destination.getY()].getCnt() == 2 &&
                                 Math.abs(destination.getY() - source.getY()) == 1) {
                            c++;
                        }
                        else {
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                }
                return true;
            }
            else {//不是第一步
                if (destination.getX() < source.getX()) {
                    return false;
                }
                else if (destination.getX() > source.getX()) {
                    if (destination.getX() - source.getX() != 1) {//是否走一格
                        return false;
                    }
                    else {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                        else if (Math.abs(destination.getY() - source.getY()) == 1) {
                            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                else {//吃过路兵的情况

                    if (chessComponents[destination.getX()][destination.getY()] instanceof PawnChessComponent &&
                            chessComponents[destination.getX()][destination.getY()].getCnt() == 2 &&
                            chessComponents[destination.getX()][destination.getY()].getCn() == 2 && Math.abs(destination.getY() - source.getY()) == 1) {
                        c++;
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
            return true;
        }
        else {//白棋
            if (chessboardPoint.getX()==6) {//第一步判断
                if (destination.getX() > source.getX()) {
                    return false;
                }
                else {
                    if (destination.getX() - source.getX() == -2) {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) || !(chessComponents[destination.getX()+1][destination.getY() ] instanceof EmptySlotComponent)) {
                                return false;
                            }
                            else {
                                cn++;
                            }
                        }
                        else {
                            return false;
                        }
                    }
                    else if (destination.getX() - source.getX() == -1) {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        } else if (Math.abs(destination.getY() - source.getY()) == 1) {
                            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    else if(destination.getX()==source.getX()){

                        if (chessComponents[destination.getX()][destination.getY()] instanceof PawnChessComponent &&
                                chessComponents[destination.getX()][destination.getY()].getCnt() == 2 &&
                                chessComponents[destination.getX()][destination.getY()].getCn() == 2 && Math.abs(destination.getY() - source.getY()) == 1) {
                            c++;
                        }
                        else {
                            return false;
                        }
                    }
                    else{
                        return false;
                    }
                }
                return true;
            }
            else {//不是第一步
                if (destination.getX() > source.getX()) {
                    return false;
                }
                else if (destination.getX() < source.getX()) {
                    if (destination.getX() - source.getX() != -1) {//是否走一格
                        return false;
                    }
                    else {
                        if (destination.getY() == source.getY()) {
                            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                                return false;
                            }
                        }
                        else if (Math.abs(destination.getY() - source.getY()) == 1) {
                            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                }
                else {//吃过路兵的情况

                    if (chessComponents[destination.getX()][destination.getY()] instanceof PawnChessComponent &&
                            chessComponents[destination.getX()][destination.getY()].getCnt() == 2 &&
                            chessComponents[destination.getX()][destination.getY()].getCn() == 2 && Math.abs(destination.getY() - source.getY()) == 1) {
                        c++;
                    }
                    else {
                        return false;
                    }
                }
            }
            return true;
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}



