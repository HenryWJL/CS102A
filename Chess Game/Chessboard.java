package model;


import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];//棋盘对应的数组
    private ChessColor currentColor = ChessColor.BLACK;//行棋方
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    Color darkRed=new Color(220,20,60);
    Color violet=new Color(238,100,238);
    Color purple=new Color(238,70,238);
    Color skyBlue=new Color(70,70,250);
    Color lightCyan=new Color(150,255,255);
    Color darkCyan=new Color(0,225,225);
    Color Indigo=new Color(50,250,250);
    int steps=0;//步骤计数器
    int backCounts=0;//悔棋计数器
    int cnt=30;//计时器初始值
    int app=1;//皮肤更换器
    int ai=0;//人机选择器
    int i=0;

    boolean result1=true;//!8*8;
    boolean result2=true;//!black and white,6 types;
    boolean result3=true;//!next player;
    boolean result123=true;
    boolean result4=true;//!format;

    JLabel winner=new JLabel("Black wins!");//胜者
    JLabel timer=new JLabel("30");//计时器
    JLabel turns=new JLabel("Black");//行棋方
    ImageIcon background1=new ImageIcon("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\Img\\starsky1.png");
    JLabel frame=new JLabel(background1);
    JButton change=new JButton("Change RK");//王车易位
    JButton button = new JButton("Return");//悔棋

    AudioClip clip;//吃子音效
    AudioClip chosenClip;//背景音乐
    AudioClip endClip;//胜利音效
    File musicFile=new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\music\\bomb.wav");
    File musicFile1=new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\music\\Star Sky.wav");
    File musicFile2=new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\music\\Por Una Cabeza.wav");
    File musicFile3=new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\music\\Introduction.wav");
    File musicFile4=new File("C:\\Users\\86136\\IdeaProjects\\Demo\\src\\model\\music\\Victory.wav");


    //计时器
    ActionListener l=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.setText(""+cnt--);
        }
    };
    Timer t=new Timer(1000,l);
    ActionListener l1=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentColor==ChessColor.BLACK){
                setCurrentColor(ChessColor.WHITE);
                cnt=30;
                turns.setText("White");
            }
            else{
                setCurrentColor(ChessColor.BLACK);
                cnt=30;
                turns.setText("Black");
            }
        }
    };
    Timer t1=new Timer(30000,l1);


    public Chessboard(int width, int height) throws IOException {

        //判定胜负
        winner.setLocation(120,250);
        winner.setSize(600,100);
        winner.setFont(new Font("Broadway", Font.BOLD, 60));
        winner.setForeground(Color.magenta);
        winner.setVisible(false);
        add(winner);
        //行棋方
        turns.setLocation(850/3,600);
        turns.setSize(200,60);
        turns.setFont(new Font("Broadway", Font.BOLD, 40));
        turns.setForeground(Color.MAGENTA);
        turns.setVisible(false);
        add(turns);
        //计时器
        timer.setLocation(560/3, 600);
        timer.setSize(100,60);
        timer.setFont(new Font("Broadway", Font.BOLD, 40));
        timer.setForeground(Color.RED);
        timer.setVisible(false);
        add(timer);
        //王车易位
        change.setLocation(0,620);
        change.setSize(170,40);
        change.setFont(new Font("Algerian", Font.BOLD, 20));
        change.setForeground(Indigo);
        change.setContentAreaFilled(false);
        change.setVisible(false);
        add(change);
        change.addActionListener(e -> {
            if(currentColor==ChessColor.BLACK){
                if(chessComponents[0][0]instanceof RookChessComponent&&chessComponents[0][4]instanceof KingChessComponent&&
                        chessComponents[0][0].getCnt()==1&&chessComponents[0][4].getCnt()==1){
                    int m=0;
                    for(int i=1;i<3;i++){
                        if(!(chessComponents[0][i]instanceof EmptySlotComponent)){
                            m=1;
                            break;
                        }
                    }
                    if(m==0){
                        chessComponents[0][4].swapLocation(chessComponents[0][2]);
                        chessComponents[0][0].swapLocation(chessComponents[0][3]);
                        swapColor();
                        turns.setText("White");
                        cnt=30;
                        t1.stop();
                        t1.start();
                    }
                }
                if(chessComponents[0][7]instanceof RookChessComponent&&chessComponents[0][4]instanceof KingChessComponent&&
                        chessComponents[0][7].getCnt()==1&&chessComponents[0][4].getCnt()==1){
                    int m=0;
                    for(int i=6;i>4;i--){
                        if(!(chessComponents[0][i]instanceof EmptySlotComponent)){
                            m=1;
                            break;
                        }
                    }
                    if(m==0){
                        chessComponents[0][4].swapLocation(chessComponents[0][6]);
                        chessComponents[0][7].swapLocation(chessComponents[0][5]);
                        swapColor();
                        turns.setText("White");
                        cnt=30;
                        t1.stop();
                        t1.start();
                    }
                }
            }
            else{
                if(chessComponents[7][0]instanceof RookChessComponent&&chessComponents[7][4]instanceof KingChessComponent&&
                        chessComponents[7][0].getCnt()==1&&chessComponents[7][4].getCnt()==1){
                    int m=0;
                    for(int i=1;i<3;i++){
                        if(!(chessComponents[7][i]instanceof EmptySlotComponent)){
                            m=1;
                            break;
                        }
                    }
                    if(m==0){
                        chessComponents[7][4].swapLocation(chessComponents[7][2]);
                        chessComponents[7][0].swapLocation(chessComponents[7][3]);
                        swapColor();
                        turns.setText("Black");
                        cnt=30;
                        t1.stop();
                        t1.start();
                    }
                }
                if(chessComponents[7][7]instanceof RookChessComponent&&chessComponents[7][4]instanceof KingChessComponent&&
                        chessComponents[7][7].getCnt()==1&&chessComponents[7][4].getCnt()==1){
                    int m=0;
                    for(int i=6;i>4;i--){
                        if(!(chessComponents[7][i]instanceof EmptySlotComponent)){
                            m=1;
                            break;
                        }
                    }
                    if(m==0){
                        chessComponents[7][4].swapLocation(chessComponents[7][6]);
                        chessComponents[7][7].swapLocation(chessComponents[7][5]);
                        swapColor();
                        turns.setText("Black");
                        cnt=30;
                        t1.stop();
                        t1.start();
                    }
                }
            }
        });
        //初始界面
        frame.setLocation(0, -50);
        frame.setSize(width-276,height);
        frame.setVisible(true);
        add(frame);
        JLabel title=new JLabel("CHESS GAME");
        title.setLocation(150,320);
        title.setSize(500,100);
        title.setForeground(Color.CYAN);
        title.setVisible(true);
        title.setFont(new Font("Broadway", Font.BOLD, 60));
        frame.add(title);
        setLayout(null);
        setSize(width, height);
        CHESS_SIZE = (width-276) / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiate();
        RecordFunction();

        //添加按钮
        addStartButton();
        addResetButton();
        addSaveButton();
        addLoadButton();
        addAIButton();
        addAppearanceButton();
        addMusicButton();
        addBackVideoButton();
        addReturnButton();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public int getAi() {
        return ai;
    }

    //初始化棋盘
    private void initiate() throws IOException {
        initiateEmptyChessboard();
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, 7, ChessColor.BLACK);
        initRookOnBoard(7, 0, ChessColor.WHITE);
        initRookOnBoard(7, 7, ChessColor.WHITE);
        initBishopOnBoard(0,2,ChessColor.BLACK);
        initBishopOnBoard(0,5,ChessColor.BLACK);
        initBishopOnBoard(7,2,ChessColor.WHITE);
        initBishopOnBoard(7,5,ChessColor.WHITE);
        initQueenOnBoard(0,3,ChessColor.BLACK);
        initQueenOnBoard(7,3,ChessColor.WHITE);
        initKingOnBoard(0,4,ChessColor.BLACK);
        initKingOnBoard(7,4,ChessColor.WHITE);
        initKnightOnBoard(0,1,ChessColor.BLACK);
        initKnightOnBoard(0,6,ChessColor.BLACK);
        initKnightOnBoard(7,1,ChessColor.WHITE);
        initKnightOnBoard(7,6,ChessColor.WHITE);


        for(int i=0;i<8;i++){
            initPawnOnBoard(1,i,ChessColor.BLACK);
        }
        for(int i=0;i<8;i++){
            initPawnOnBoard(6,i,ChessColor.WHITE);
        }
    }
    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE,app));
            }
        }
    }
    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE,app);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    //走棋过程
    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        try {//胜利音效
            endClip= Applet.newAudioClip(musicFile4.toURL());
        } catch (OutOfMemoryError | MalformedURLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        if(chess2 instanceof KingChessComponent){//将军
            if(currentColor==ChessColor.BLACK){
                if(app==2){
                    winner.setText("USA wins!");
                    winner.setForeground(Color.YELLOW);
                    winner.setLocation(140,250);
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
                else if(app==1){
                    winner.setText("Black wins!");
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
                else {
                    winner.setText("Twilight wins!");
                    winner.setForeground(Color.CYAN);
                    winner.setLocation(80,250);
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
            }
            else{
                if(app==2){
                    winner.setText("CCCP wins!");
                    winner.setForeground(Color.YELLOW);
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
                else if(app==1){
                    winner.setText("White wins!");
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
                else {
                    winner.setText("Hikigaya wins!");
                    winner.setForeground(Color.CYAN);
                    winner.setLocation(70,250);
                    winner.setVisible(true);
                    t.stop();
                    if(chosenClip!=null){
                        chosenClip.stop();
                    }
                    endClip.play();
                }
            }
        }
        try {//吃子音效
            clip= Applet.newAudioClip(musicFile.toURL());
        } catch (OutOfMemoryError | MalformedURLException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        if (!(chess2 instanceof EmptySlotComponent)) {
            clip.play();
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE,app));
        }


        if(chess1 instanceof PawnChessComponent){//兵底线升变
            if(chess1.getChessColor()==ChessColor.BLACK){
                if(chess2.getChessboardPoint().getX()==7){
                    remove(chess1);
                    Random generator=new Random();
                    int a=generator.nextInt(3);
                    switch (a){
                        case 0:
                            add(chess1=new BishopChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.BLACK,clickController,CHESS_SIZE,app));
                            break;
                        case 1:
                            add(chess1=new KnightChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.BLACK,clickController,CHESS_SIZE,app));
                            break;
                        case 2:
                            add(chess1=new RookChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.BLACK,clickController,CHESS_SIZE,app));
                            break;
                        case 3:
                            add(chess1=new QueenChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.BLACK,clickController,CHESS_SIZE,app));
                            break;
                    }
                }
            }
            else {
                if(chess2.getChessboardPoint().getX()==0){
                    remove(chess1);
                    Random generator=new Random();
                    int a=generator.nextInt(3);
                    switch (a){
                        case 0:
                            add(chess1=new BishopChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.WHITE,clickController,CHESS_SIZE,app));
                            break;
                        case 1:
                            add(chess1=new KnightChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.WHITE,clickController,CHESS_SIZE,app));
                            break;
                        case 2:
                            add(chess1=new RookChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.WHITE,clickController,CHESS_SIZE,app));
                            break;
                        case 3:
                            add(chess1=new QueenChessComponent(chess1.getChessboardPoint(),chess1.getLocation(),ChessColor.WHITE,clickController,CHESS_SIZE,app));
                            break;
                    }
                }
            }
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        t1.stop();
        t1.start();
        chess1.repaint();
        chess2.repaint();

        //步骤记录
        steps++;
        int a=steps;
        setSteps(a);
        System.out.println("steps  "+steps);


        //计时器重置，行棋方重置
        cnt=30;
        if(currentColor==ChessColor.BLACK){
            turns.setText("White");
        }
        else{
            turns.setText("Black");
        }
    }

    public void eatPawn(ChessComponent chess1){
        if(chess1.getC()==2){//吃过路兵
            if(chess1.getChessColor()==ChessColor.BLACK){
                ChessComponent chess2=chessComponents[chess1.getChessboardPoint().getX()+1][chess1.getChessboardPoint().getY()];
                chess1.swapLocation(chess2);
                int row3 = chess1.getChessboardPoint().getX(), col3 = chess1.getChessboardPoint().getY();
                chessComponents[row3][col3] = chess1;
                int row4 = chess2.getChessboardPoint().getX(), col4 = chess2.getChessboardPoint().getY();
                chessComponents[row4][col4] = chess2;
                chess1.setC(1);
            }
            else{
                ChessComponent chess2=chessComponents[chess1.getChessboardPoint().getX()-1][chess1.getChessboardPoint().getY()];
                chess1.swapLocation(chess2);
                int row3 = chess1.getChessboardPoint().getX(), col3 = chess1.getChessboardPoint().getY();
                chessComponents[row3][col3] = chess1;
                int row4 = chess2.getChessboardPoint().getX(), col4 = chess2.getChessboardPoint().getY();
                chessComponents[row4][col4] = chess2;
                chess1.setC(1);
            }
        }
    }
    public void reveal(ChessComponent chessComponent){//显示能移到的位置
        ChessboardPoint destination=new ChessboardPoint(0,0);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                destination.setX(i);
                destination.setY(j);
                if(chessComponent.canMoveTo(chessComponents,destination)&&chessComponents[i][j].getChessColor()!=chessComponent.getChessColor()){
                    if(app==3){
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{Color.cyan, Color.cyan});
                    }
                    else if(app==2){
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{Color.yellow, Color.yellow});
                    }
                    else {
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{violet, violet});
                    }
                    chessComponents[i][j].repaint();
                }
            }
        }
        chessComponent.setCn(chessComponent.getCn()-1);
        if(chessComponent.getC()==2){
            chessComponent.setC(1);
        }
    }
    public void hideReveal(){//取消reveal
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(app==3) {
                    if (Arrays.equals(chessComponents[i][j].getBACKGROUND_COLORS(), new Color[]{Color.cyan, Color.cyan})) {
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{violet, skyBlue});
                    }
                }
                else if(app==2){
                    if(Arrays.equals(chessComponents[i][j].getBACKGROUND_COLORS(), new Color[]{Color.yellow,Color.yellow})){
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{Color.red, Color.blue});
                    }
                }
                else{
                    if(Arrays.equals(chessComponents[i][j].getBACKGROUND_COLORS(), new Color[]{violet,violet})) {
                        chessComponents[i][j].setBACKGROUND_COLORS(new Color[]{Color.cyan, Color.blue});
                    }
                }
                chessComponents[i][j].repaint();
            }
        }
    }
    public ArrayList<ChessboardPoint>getCanMovePoints(ChessboardPoint source){
        ArrayList<ChessboardPoint>list=new ArrayList<>();
        for(int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if(chessComponents[source.getX()][source.getY()].canMoveTo(chessComponents,new ChessboardPoint(i,j))&&chessComponents[i][j].getChessColor()!=currentColor){
                    list.add(new ChessboardPoint(i,j));
                }
            }
        }
        return list;
    }
    public boolean canMove(ChessboardPoint source){
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[source.getX()][source.getY()].canMoveTo(chessComponents, new ChessboardPoint(i, j))&&chessComponents[i][j].getChessColor()!=chessComponents[source.getX()][source.getY()].getChessColor()) {
                    return true;
                }
            }
        }
        return false;
    }

    //交换行棋方
    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
    public void setCurrentColorForSwap(ChessColor currentColor) {
        this.currentColor = currentColor;
    }
    //存储和载入
    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }
    public void loadChessGame(List<String> chessData) {
        //int ListLength = chessData.size();
        //System.out.println("ListLength  "+ListLength);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
                Point point = calculatePoint(i, j);

                if (chessData.get(i).charAt(j) == 'R') {
                    RookChessComponent a = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == 'r') {
                    RookChessComponent a = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'K') {
                    KingChessComponent a = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'k') {
                    KingChessComponent a = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'N') {
                    KnightChessComponent a = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == 'n') {
                    KnightChessComponent a = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'B') {
                    BishopChessComponent a = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'b') {
                    BishopChessComponent a = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
//                        chessComponents[i][j] = a;
                    putChessOnBoard(a);
//                        a.setVisible(true);
                } else if (chessData.get(i).charAt(j) == 'Q') {
                    QueenChessComponent a = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == 'q') {
                    QueenChessComponent a = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == 'P') {
                    PawnChessComponent a = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == 'p') {
                    PawnChessComponent a = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                } else if (chessData.get(i).charAt(j) == '_') {
                    EmptySlotComponent a = new EmptySlotComponent(chessboardPoint, point, clickController, CHESS_SIZE,app);
                    putChessOnBoard(a);
                }
            }
        }
        System.out.println(getChessboardGraph());
    }

    public boolean getResult2(List<String> chessData){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessboardPoint chessboardPoint = new ChessboardPoint(i, j);
                Point point = calculatePoint(i, j);

                if (chessData.get(i).charAt(j) == 'R') {
                    RookChessComponent a = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'r') {
                    RookChessComponent a = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'K') {
                    KingChessComponent a = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'k') {
                    KingChessComponent a = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'N') {
                    KnightChessComponent a = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'n') {
                    KnightChessComponent a = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'B') {
                    BishopChessComponent a = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'b') {
                    BishopChessComponent a = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'Q') {
                    QueenChessComponent a = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'q') {
                    QueenChessComponent a = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'P') {
                    PawnChessComponent a = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == 'p') {
                    PawnChessComponent a = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE,app);
                } else if (chessData.get(i).charAt(j) == '_') {
                    EmptySlotComponent a = new EmptySlotComponent(chessboardPoint, point, clickController, CHESS_SIZE,app);
                } else {
                    result2 = false;
                    break;
                }
            }
        }
        return result2;
    }

    public boolean getResult1(List<String> chessData) {
        int ListLength=chessData.size();
        if (chessData.get(ListLength-1).length()==1 && ListLength != 9) {
            result1 = false;
        }
        else if(chessData.get(ListLength-1).length()==1 && ListLength==9) {
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    result1 = false;
                    break;
                }
            }
        }
        return result1;
    }

    public boolean getResult3(List<String> chessData) {
        int ListLength=chessData.size();
        if (chessData.get(ListLength-1).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
        }
        else if (chessData.get(ListLength-1).charAt(0) == 'b') {
            currentColor = ChessColor.BLACK;
        }
        else {
            //System.out.println("缺少下一步行棋方");
            result3=false;
            //JOptionPane.showMessageDialog(null, "解析异常，缺少下一步行棋方", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        }
        return result3;
    }
    public String[] getStringArr(){
        String[] strings=new String[65];
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="K";
                }
                else if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="k";
                }
                else if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="Q";
                }
                else if (chessComponents[i][j] instanceof QueenChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="q";
                }
                else if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="B";
                }
                else if (chessComponents[i][j] instanceof BishopChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="b";
                }
                else if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="N";
                }
                else if (chessComponents[i][j] instanceof KnightChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="n";
                }
                else if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="R";
                }
                else if (chessComponents[i][j] instanceof RookChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="r";
                }
                else if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                    strings[i*8+j]="P";
                }
                else if (chessComponents[i][j] instanceof PawnChessComponent && chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                    strings[i*8+j]="p";
                }
                else if(chessComponents[i][j] instanceof EmptySlotComponent){
                    strings[i*8+j]="_";
                }
            }
        }
        if(getCurrentColor()==ChessColor.WHITE){
            strings[64]="w";
        }
        if(getCurrentColor()==ChessColor.BLACK){
            strings[64]="b";
        }
        return strings;
    }
    public String getChessboardGraph() {
        String[] strings=getStringArr();
        StringBuilder row1=new StringBuilder();
        StringBuilder row2=new StringBuilder();
        StringBuilder row3=new StringBuilder();
        StringBuilder row4=new StringBuilder();
        StringBuilder row5=new StringBuilder();
        StringBuilder row6=new StringBuilder();
        StringBuilder row7=new StringBuilder();
        StringBuilder row8=new StringBuilder();
        String row9=null;
        for (int k=0;k<8;k++){
            row1.append(strings[k]);
        }
        for (int k=8;k<16;k++){
            row2.append(strings[k]);
        }
        for (int k=16;k<24;k++){
            row3.append(strings[k]);
        }
        for (int k=24;k<32;k++){
            row4.append(strings[k]);
        }
        for (int k=32;k<40;k++){
            row5.append(strings[k]);
        }
        for (int k=40;k<48;k++){
            row6.append(strings[k]);
        }
        for (int k=48;k<56;k++){
            row7.append(strings[k]);
        }
        for (int k=56;k<64;k++){
            row8.append(strings[k]);
        }
        if(currentColor==ChessColor.BLACK) {
            row9 = "b";
        }
        if(currentColor==ChessColor.WHITE) {
            row9 = "w";
        }
        return row1+"\n"+row2+"\n"+row3+"\n"+row4+"\n"+row5+"\n"+row6+"\n"+row7+"\n"+row8+"\n"+row9+"\n";
    }

    public void RecordFunction() {
        System.out.println("Click record(Record for return)");
        //String path = JOptionPane.showInputDialog(this ,"Input Path here");//D:\存档\game1.txt;
        String filePathStep = "C:\\Users\\86136\\savefile\\game0.txt";//D:\\存档\\game1.txt;
        String content;//C:\Users\Lenovo\IdeaProjects\model\cundang\game0.txt
        FileOutputStream fos = null;

        try {
            // true代表叠加写入，没有则代表清空一次写一次
            fos = new FileOutputStream(filePathStep, true);
            //fosStep = new FileOutputStream(filePathStep, true);
            content = getChessboardGraph();
            byte[] bytes = content.getBytes();
            //fosStep.write(bytes);
            fos.write(bytes);
        }
        catch (IOException s) {
            s.printStackTrace();
        }
        finally {
            try {
                fos.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }//棋子被选中，clickController如何变化；



    private void addStartButton(){
        JButton start = new JButton("Start");
        start.setLocation(680, 0);
        start.setSize(200, 60);
        start.setFont(new Font("Algerian", Font.BOLD, 40));
        start.setForeground(lightCyan);
        start.setContentAreaFilled(false);
        add(start);
        start.addActionListener(e -> {
            frame.setVisible(false);
            turns.setVisible(true);
            timer.setVisible(true);
            change.setVisible(true);
            button.setVisible(true);
            t.start();
            t1.start();
        });
    }
    private void addResetButton(){
        JButton reset = new JButton("Reset");
        reset.setLocation(680, 70);
        reset.setSize(200, 60);
        reset.setFont(new Font("Algerian", Font.BOLD, 40));
        reset.setForeground(violet);
        reset.setContentAreaFilled(false);
        add(reset);
        reset.addActionListener(e -> {
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    remove(chessComponents[i][j]);
                }
            }
            try {
                initiate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            winner.setVisible(false);
            t.start();
            t1.stop();
            t1.start();
        });
    }

    private void addSaveButton(){
        JButton save=new JButton("Save");
        save.setLocation(680,140);
        save.setSize(200,60);
        save.setFont(new Font("Algerian", Font.BOLD, 40));
        save.setForeground(Color.cyan);
        save.setContentAreaFilled(false);
        add(save);
        save.addActionListener(e -> {
            /*System.out.println("Click load(Save)");
            String path = JOptionPane.showInputDialog(this ,"Input Path here");//D:\存档\game1.txt;
            //String filePathStep =  "D:\\存档\\"+ path + ".txt";//D:\\存档\\game1.txt;
            String filePathSave = "D:\\存档\\"+ path;//D:\\存档\\game1.txt;+ ".txt"*/

            JFileChooser jc=new JFileChooser("C:\\");
            jc.showSaveDialog(null);
            String filePathSave=jc.getSelectedFile().toString();
            String content;//C:\Users\Lenovo\IdeaProjects\model\cundang\game0.txt
            FileOutputStream fos = null;

            try {
                // true代表叠加写入，没有则代表清空一次写一次
                fos = new FileOutputStream(filePathSave, true);
                //fosStep = new FileOutputStream(filePathStep, true);
                content = getChessboardGraph();
                byte[] bytes = content.getBytes();
                //fosStep.write(bytes);
                fos.write(bytes);
            }
            catch (IOException s) {
                s.printStackTrace();
            }
            finally {
                try {
                    fos.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(680, 210);
        button.setSize(200, 60);
        button.setFont(new Font("Algerian", Font.BOLD, 40));
        button.setForeground(darkCyan);
        button.setContentAreaFilled(false);
        add(button);
        button.addActionListener(e -> {
            JFileChooser jc=new JFileChooser("C:\\");
            jc.showOpenDialog(null);
            String filePath=jc.getSelectedFile().toString();
            if (!filePath.endsWith(".txt")) {
                System.out.println("文件格式错误！(非txt)!");
                result4 = false;
                JOptionPane.showMessageDialog(null, "文件格式错误！(非txt)", "WARNING", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                result4 = true;
            }
            result1=true;result2=true;result3=true;
            List<String> a = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
//构造一个BufferedReader类来读取文件
                String s;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    a.add(s);
                }
                br.close();
            } catch (Exception b) {
                b.printStackTrace();
            }

            if(result4) {
                if (!getResult3(a)) {
                    System.out.println("解析异常，缺少下一步行棋方");
                    JOptionPane.showMessageDialog(null, "解析异常，缺少下一步行棋方", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                }

                if (!getResult1(a)) {
                    System.out.println("棋盘并非 8*8");
                    JOptionPane.showMessageDialog(null, "棋盘并非 8*8", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                }

                if (!getResult2(a)) {
                    System.out.println("棋子并非六种之一，棋子并非黑白棋子");
                    JOptionPane.showMessageDialog(null, "棋子并非六种之一，棋子并非黑白棋子", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                }

                if (getResult1(a) && getResult2(a) && getResult3(a)) {
                    loadChessGame(a);
                    repaint();
                }
            }
            cnt=30;
            t1.stop();
            t1.start();
            if(currentColor==ChessColor.BLACK){
                turns.setText("Black");
            }
            else {
                turns.setText("White");
            }
        });
    }
    private void addAIButton(){
        JButton ai1=new JButton("/");
        JButton ai2=new JButton("e");
        JButton ai3=new JButton("n");
        ai1.setLocation(620,260);
        ai2.setLocation(620,290);
        ai3.setLocation(620,320);
        ai1.setSize(50,20);
        ai2.setSize(50,20);
        ai3.setSize(50,20);
        ai1.setFont(new Font("Algerian", Font.BOLD, 15));
        ai2.setFont(new Font("Algerian", Font.BOLD, 15));
        ai3.setFont(new Font("Algerian", Font.BOLD, 15));
        ai1.setForeground(Color.MAGENTA);
        ai2.setForeground(Color.CYAN);
        ai3.setForeground(Color.YELLOW);
        ai1.setContentAreaFilled(false);
        ai2.setContentAreaFilled(false);
        ai3.setContentAreaFilled(false);
        ai1.setVisible(false);
        ai2.setVisible(false);
        ai3.setVisible(false);
        add(ai1);
        add(ai2);
        add(ai3);
        JButton AI=new JButton("AI");
        AI.setLocation(680,280);
        AI.setSize(200,60);
        AI.setFont(new Font("Algerian", Font.BOLD, 50));
        AI.setForeground(Color.cyan);
        AI.setContentAreaFilled(false);
        add(AI);
        AI.addActionListener(e -> {
            ai1.setVisible(true);
            ai2.setVisible(true);
            ai3.setVisible(true);
        });
        ai1.addActionListener(e -> {
            ai=0;
            ai1.setVisible(false);
            ai2.setVisible(false);
            ai3.setVisible(false);
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            t.start();
            t1.stop();
            t1.start();
        });
        ai2.addActionListener(e -> {
            ai=1;
            ai1.setVisible(false);
            ai2.setVisible(false);
            ai3.setVisible(false);
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            t.start();
            t1.stop();
            t1.start();
        });
        ai3.addActionListener(e -> {
            ai=2;
            ai1.setVisible(false);
            ai2.setVisible(false);
            ai3.setVisible(false);
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            t.start();
            t1.stop();
            t1.start();
        });
    }
    private void addAppearanceButton(){
        JButton app1=new JButton("1");
        JButton app2=new JButton("2");
        JButton app3=new JButton("3");
        app1.setLocation(620,350);
        app2.setLocation(620,380);
        app3.setLocation(620,410);
        app1.setSize(50,20);
        app2.setSize(50,20);
        app3.setSize(50,20);
        app1.setFont(new Font("Algerian", Font.BOLD, 15));
        app2.setFont(new Font("Algerian", Font.BOLD, 15));
        app3.setFont(new Font("Algerian", Font.BOLD, 15));
        app1.setForeground(Color.MAGENTA);
        app2.setForeground(Color.CYAN);
        app3.setForeground(Color.YELLOW);
        app1.setContentAreaFilled(false);
        app2.setContentAreaFilled(false);
        app3.setContentAreaFilled(false);
        app1.setVisible(false);
        app2.setVisible(false);
        app3.setVisible(false);
        add(app1);
        add(app2);
        add(app3);
        JButton appearance=new JButton("Appearance");
        appearance.setLocation(680,350);
        appearance.setSize(200,60);
        appearance.setFont(new Font("Algerian", Font.BOLD, 24));
        appearance.setForeground(Color.magenta);
        appearance.setContentAreaFilled(false);
        add(appearance);
        appearance.addActionListener(e -> {
            app1.setVisible(true);
            app2.setVisible(true);
            app3.setVisible(true);
        });
        app1.addActionListener(e -> {
            app=1;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    remove(chessComponents[i][j]);
                }
            }
            try {
                initiate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            winner.setVisible(false);
            t.start();
            t1.stop();
            t1.start();
            app1.setVisible(false);
            app2.setVisible(false);
            app3.setVisible(false);
        });
        app2.addActionListener(e -> {
            app=2;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    remove(chessComponents[i][j]);
                }
            }
            try {
                initiate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            winner.setVisible(false);
            t.start();
            t1.stop();
            t1.start();
            app1.setVisible(false);
            app2.setVisible(false);
            app3.setVisible(false);
        });
        app3.addActionListener(e -> {
            app=3;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    remove(chessComponents[i][j]);
                }
            }
            try {
                initiate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            repaint();
            setCnt(30);
            turns.setText("Black");
            setCurrentColor(ChessColor.BLACK);
            winner.setVisible(false);
            t.start();
            t1.stop();
            t1.start();
            app1.setVisible(false);
            app2.setVisible(false);
            app3.setVisible(false);
        });
    }

    private void addMusicButton() {
        JButton play1=new JButton("1");
        JButton play2=new JButton("2");
        JButton play3=new JButton("3");
        play1.setLocation(620,440);
        play2.setLocation(620,470);
        play3.setLocation(620,500);
        play1.setSize(50,20);
        play2.setSize(50,20);
        play3.setSize(50,20);
        play1.setFont(new Font("Algerian", Font.BOLD, 15));
        play2.setFont(new Font("Algerian", Font.BOLD, 15));
        play3.setFont(new Font("Algerian", Font.BOLD, 15));
        play1.setForeground(Color.MAGENTA);
        play2.setForeground(Color.CYAN);
        play3.setForeground(Color.yellow);
        play1.setContentAreaFilled(false);
        play2.setContentAreaFilled(false);
        play3.setContentAreaFilled(false);
        play1.setVisible(false);
        play2.setVisible(false);
        play3.setVisible(false);
        add(play1);
        add(play2);
        add(play3);
        JButton play = new JButton("Play music");
        JButton stop = new JButton("Stop music");
        play.setLocation(680, 420);
        stop.setLocation(680, 490);
        play.setSize(200, 60);
        stop.setSize(200, 60);
        play.setFont(new Font("Algerian", Font.BOLD, 25));
        stop.setFont(new Font("Algerian", Font.BOLD, 25));
        play.setForeground(purple);
        stop.setForeground(skyBlue);
        play.setContentAreaFilled(false);
        stop.setContentAreaFilled(false);
        add(play);
        add(stop);

        play.addActionListener(e -> {
            play1.setVisible(true);
            play2.setVisible(true);
            play3.setVisible(true);
        });
        play1.addActionListener(e -> {
            try {
                chosenClip = Applet.newAudioClip(musicFile1.toURL());
            } catch (OutOfMemoryError | MalformedURLException er) {
                System.out.println("error");
                er.printStackTrace();
            }
            chosenClip.play();
            play1.setVisible(false);
            play2.setVisible(false);
            play3.setVisible(false);
        });
        play2.addActionListener(e -> {
            try {
                chosenClip = Applet.newAudioClip(musicFile2.toURL());
            } catch (OutOfMemoryError | MalformedURLException er) {
                System.out.println("error");
                er.printStackTrace();
            }
            chosenClip.play();
            play1.setVisible(false);
            play2.setVisible(false);
            play3.setVisible(false);
        });
        play3.addActionListener(e -> {
            try {
                chosenClip = Applet.newAudioClip(musicFile3.toURL());
            } catch (OutOfMemoryError | MalformedURLException er) {
                System.out.println("error");
                er.printStackTrace();
            }
            chosenClip.play();
            play1.setVisible(false);
            play2.setVisible(false);
            play3.setVisible(false);
        });
        stop.addActionListener(e -> {
            chosenClip.stop();
        });
    }

    private void addBackVideoButton() {
        JButton button = new JButton("BackVideo");
        button.setLocation(680, 560);
        button.setSize(200, 60);
        button.setFont(new Font("Algerian", Font.BOLD, 25));
        button.setForeground(Color.blue);
        button.setContentAreaFilled(false);
        add(button);
        String filePath = "C:\\Users\\86136\\savefile\\game0.txt";//D:\\存档\\game1.txt;"
        int count=0;
        button.addActionListener(e -> {
            System.out.println("Click BackTv");
            List<String> a = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filePath));
                //构造一个BufferedReader类来读取文件
                String s;
                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    a.add(s);
                }
                br.close();
            }
            catch (Exception b) {
                b.printStackTrace();
            }
            int zuShu=a.size()/9;

            ActionListener l=new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(i<zuShu){
                        List<String> listByPass = new ArrayList<>();
                        listByPass.add(a.get(i * 9 ));
                        //arrays[0]=a.get(i * 9 + 1);
                        listByPass.add(a.get(i * 9 + 1));
                        //arrays[1]=a.get(i * 9 + 2);
                        listByPass.add(a.get(i * 9 + 2));
                        //arrays[2]=a.get(i * 9 + 3);
                        listByPass.add(a.get(i * 9 + 3));
                        //arrays[3]=a.get(i * 9 + 4);
                        listByPass.add(a.get(i * 9 + 4));
                        //arrays[4]=a.get(i * 9 + 5);
                        listByPass.add(a.get(i * 9 + 5));
                        //arrays[5]=a.get(i * 9 + 6);
                        listByPass.add(a.get(i * 9 + 6));
                        //arrays[6]=a.get(i * 9 + 7);
                        listByPass.add(a.get(i * 9 + 7));
                        //arrays[7]=a.get(i * 9 + 8);
                        listByPass.add(a.get(i * 9 + 8));
                        //listByPass.add(a.get(i * 9 + 9));
                        //arrays[8]=a.get(i * 9 + 9);
                        //listWanted.add(listTotal.get((wantToBeSteps) * 9));
                        System.out.println(listByPass);
                        loadChessGame(listByPass);
                        repaint();
                        i++;
                    }
                }
            };
            Timer t2=new Timer(1000,l);
            t2.start();
            t.stop();
            t1.stop();
        });
    }
    private void addReturnButton() {
        button.setLocation(450, 620);
        button.setSize(140, 40);
        button.setFont(new Font("Algerian", Font.BOLD, 20));
        button.setForeground(Color.blue);
        button.setContentAreaFilled(false);
        button.setVisible(false);
        add(button);
        List<String> listTotal = new ArrayList<>();
        button.addActionListener(e -> {
            System.out.println("Click Return");
            //String path = JOptionPane.showInputDialog(this, "input path here");//"Input Path here""C:\\Users\\Lenovo\\IdeaProjects\\model\\cundang\\game1.txt"
            String filePath = "C:\\Users\\86136\\savefile\\game0.txt";//D:\\存档\\game1.txt;"
            String[] arrays = new String[8];
            try {
                int wantToBeSteps = getSteps() - backCounts;
                BufferedReader br = new BufferedReader(new FileReader(filePath));
//构造一个BufferedReader类来读取文件
                System.out.println(br.lines());
                String s;

                while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                    listTotal.add(s);
                }
                br.close();
                System.out.println("listTotal  " + listTotal);
                System.out.println(getSteps());
                if (wantToBeSteps<=0) {
                    JOptionPane.showMessageDialog(null, "无效悔棋！", "WARNING", JOptionPane.INFORMATION_MESSAGE);
                }
                else  {
                    List<String> listWanted = new ArrayList<>();
                    System.out.println("wantToBeSteps"+wantToBeSteps);
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 ));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 1));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 2));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 3));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 4));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 5));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 6));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 7));
                    listWanted.add(listTotal.get((wantToBeSteps - 1) * 9 + 8));
                    loadChessGame(listWanted);
                    repaint();
                    if(currentColor==ChessColor.BLACK){
                        currentColor=ChessColor.WHITE;
                        turns.setText("White");
                    }
                    else {
                        currentColor=ChessColor.BLACK;
                        turns.setText("Black");
                    }
                    cnt=30;
                    t1.stop();
                    t1.start();
                    backCounts++;
                }
            }catch (Exception b) {
                b.printStackTrace();
            }
        });
    }
}

