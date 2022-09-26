package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;


    private Image rookImage;


    public void loadResource() throws IOException {
        if (app==3) {
            ROOK_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\jm.png"));
            ROOK_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\tzl.png"));

        }
        else if(app==2){
            ROOK_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\15.png"));
            ROOK_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\25.png"));

        }
        else{
            ROOK_WHITE = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\rook-white.png"));
            ROOK_BLACK = ImageIO.read(new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\rook-black.png"));
        }
    }

    public void setImage(String imageFile1,String imageFile2) throws IOException {
        ROOK_WHITE=ImageIO.read(new File(imageFile1));
        ROOK_BLACK=ImageIO.read(new File(imageFile2));
    }




    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int app) {
        super(chessboardPoint, location, color, listener, size,app);
        initiateRookImage(color);
    }



    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == destination.getX()) {
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
        else { // Not on the same row or the same column.
            return false;
        }
        return true;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
