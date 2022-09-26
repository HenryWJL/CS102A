package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public abstract class ChessComponent extends JComponent {


    Color skyBlue=new Color(70,70,250);
    Color violet=new Color(238,100,238);
    public   Color[] BACKGROUND_COLORS ;
    protected ClickController clickController;
    protected ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    protected boolean selected;
    int cnt=1;//走几步判断器
    int cn=1;//是否走2格判断器
    int c=1;//是否吃过路兵判定器
    int app=1;//更换皮肤判断器



    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size,int app) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.app=app;
        if(app==3){
            BACKGROUND_COLORS=new Color[]{violet, skyBlue};
        }
        else if(app==2){
            BACKGROUND_COLORS=new Color[]{Color.red,Color.blue};
        }
        else{
            BACKGROUND_COLORS=new Color[]{Color.cyan,Color.blue};
        }
    }
    public ChessComponent(ChessColor chessColor){
        this.chessColor=chessColor;
    }

    public int getCn() {
        return cn;
    }

    public int getCnt() {
        return cnt;
    }

    public int getC() {
        return c;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public Color[] getBACKGROUND_COLORS() {
        return BACKGROUND_COLORS;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setBACKGROUND_COLORS(Color[] BACKGROUND_COLORS) {
        this.BACKGROUND_COLORS = BACKGROUND_COLORS;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(homework5.EmptySlotComponent)做交换
     */
    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
        cnt++;
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用所有监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            try {
                clickController.onClick(this);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 7)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false
     */
    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    /**
     * 这个方法主要用于加载一些特定资源，如棋子图片等等。
     *
     * @throws IOException 如果一些资源找不到(如棋子图片路径错误)，就会抛出异常
     */
    public abstract void loadResource() throws IOException;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        //System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        Color squareColor = BACKGROUND_COLORS[(chessboardPoint.getX() + chessboardPoint.getY()) % 2];
        g.setColor(squareColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
