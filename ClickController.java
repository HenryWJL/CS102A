package model;
import model.ChessComponent;
import model.Chessboard;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) throws IOException {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                chessboard.reveal(first);
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessboard.hideReveal();
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                chessboard.hideReveal();
                if(chessboard.getAi()==0){
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.eatPawn(first);
                    chessboard.swapColor();
                    chessboard.RecordFunction();
                }
                else if(chessboard.getAi()==1){
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.swapColor();
                    AI1();
                    chessboard.swapColor();
                    chessboard.RecordFunction();
                }
                else{
                    chessboard.swapChessComponents(first, chessComponent);
                    chessboard.swapColor();
                    AI2();
                    chessboard.swapColor();
                    chessboard.RecordFunction();
                }
                first.setSelected(false);
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return !(chessComponent.getChessColor().equals(chessboard.getCurrentColor())) &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
    private void AI1(){
        ChessComponent[][]chessComponents=chessboard.getChessComponents();
        ArrayList<ChessboardPoint>canMovePoints=new ArrayList<>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(chessboard.canMove(new ChessboardPoint(i,j))&&chessComponents[i][j].getChessColor()==chessboard.getCurrentColor()){
                    canMovePoints.add(new ChessboardPoint(i,j));
                }
            }
        }
        Random random=new Random();
        int number1=random.nextInt(canMovePoints.size());
        ChessboardPoint selectedPoint=canMovePoints.get(number1);//随机选中一个棋子行棋
        int number2=random.nextInt(chessboard.getCanMovePoints(selectedPoint).size());
        ChessboardPoint destination=chessboard.getCanMovePoints(selectedPoint).get(number2);//随机选中一个位置行棋
        chessboard.swapChessComponents(chessComponents[selectedPoint.getX()][selectedPoint.getY()],chessComponents[destination.getX()][destination.getY()]);
    }
    private void AI2(){
        ChessComponent[][]chessComponents=chessboard.getChessComponents();
        ArrayList<ChessboardPoint>canMovePoints=new ArrayList<>();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(chessboard.canMove(new ChessboardPoint(i,j))&&chessComponents[i][j].getChessColor()==chessboard.getCurrentColor()){
                    canMovePoints.add(new ChessboardPoint(i,j));
                }
            }
        }
        String chess="";
        int sourceX=0,sourceY=0,destinationX=0,destinationY=0;
        for(int i=0;i<canMovePoints.size();i++) {
            for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                if(chessComponents[x][y]instanceof KingChessComponent){
                    chess="king";
                    sourceX=canMovePoints.get(i).getX();
                    sourceY=canMovePoints.get(i).getY();
                    destinationX=x;
                    destinationY=y;
                    break;
                }
            }
        }
        if(!chess.equals("king")){
            for(int i=0;i<canMovePoints.size();i++) {
                for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                    int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                    int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                    if(chessComponents[x][y]instanceof QueenChessComponent){
                        chess="queen";
                        sourceX=canMovePoints.get(i).getX();
                        sourceY=canMovePoints.get(i).getY();
                        destinationX=x;
                        destinationY=y;
                        break;
                    }
                }
            }
        }
        if(!chess.equals("king")&&!chess.equals("queen")){
            for(int i=0;i<canMovePoints.size();i++) {
                for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                    int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                    int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                    if(chessComponents[x][y]instanceof RookChessComponent){
                        chess="rook";
                        sourceX=canMovePoints.get(i).getX();
                        sourceY=canMovePoints.get(i).getY();
                        destinationX=x;
                        destinationY=y;
                        break;
                    }
                }
            }
        }
        if(!chess.equals("king")&&!chess.equals("queen")&&!chess.equals("rook")){
            for(int i=0;i<canMovePoints.size();i++) {
                for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                    int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                    int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                    if(chessComponents[x][y]instanceof KnightChessComponent){
                        chess="knight";
                        sourceX=canMovePoints.get(i).getX();
                        sourceY=canMovePoints.get(i).getY();
                        destinationX=x;
                        destinationY=y;
                        break;
                    }
                }
            }
        }
        if(!chess.equals("king")&&!chess.equals("queen")&&!chess.equals("rook")&&!chess.equals("knight")){
            for(int i=0;i<canMovePoints.size();i++) {
                for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                    int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                    int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                    if(chessComponents[x][y]instanceof BishopChessComponent){
                        chess="bishop";
                        sourceX=canMovePoints.get(i).getX();
                        sourceY=canMovePoints.get(i).getY();
                        destinationX=x;
                        destinationY=y;
                        break;
                    }
                }
            }
        }
        if(!chess.equals("king")&&!chess.equals("queen")&&!chess.equals("rook")&&!chess.equals("knight")&&!chess.equals("bishop")){
            for(int i=0;i<canMovePoints.size();i++) {
                for (int j = 0; j < chessboard.getCanMovePoints(canMovePoints.get(i)).size(); j++) {
                    int x=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getX();
                    int y=chessboard.getCanMovePoints(canMovePoints.get(i)).get(j).getY();
                    if(chessComponents[x][y]instanceof PawnChessComponent){
                        chess="pawn";
                        sourceX=canMovePoints.get(i).getX();
                        sourceY=canMovePoints.get(i).getY();
                        destinationX=x;
                        destinationY=y;
                        break;
                    }
                }
            }
        }
        if(chess.equals("")){
            Random random=new Random();
            int number1=random.nextInt(canMovePoints.size());
            ChessboardPoint selectedPoint=canMovePoints.get(number1);//随机选中一个棋子行棋
            int number2=random.nextInt(chessboard.getCanMovePoints(selectedPoint).size());
            ChessboardPoint destination=chessboard.getCanMovePoints(selectedPoint).get(number2);//随机选中一个位置行棋
            chessboard.swapChessComponents(chessComponents[selectedPoint.getX()][selectedPoint.getY()],chessComponents[destination.getX()][destination.getY()]);
        }
        else {
            chessboard.swapChessComponents(chessComponents[sourceX][sourceY],chessComponents[destinationX][destinationY]);
        }
    }
}
