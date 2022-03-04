/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessomair;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Omair
 */
public class ChessOmairFram extends javax.swing.JFrame implements ActionListener{
    int milliseconds, seconds, minutes, hours;
    boolean gameState = false;
    ButtonArray[][] square = new ButtonArray[8][8];
    int size = 8;
    int oldR, oldC;
    int oldTurn, newTurn;
    boolean clicked = false;
    public ChessOmairFram() {
        initComponents();
        initialize();
    }
    
    private void initialize() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                //Create a new JButton
                square[r][c] = new ButtonArray();
                square[r][c].addActionListener(this);
                //Add buttons to the panel
                gamePAN.add(square[r][c]);
            }
        }
        resetBackground();
        setImagesValues();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.println(r+""+c+" : "+square[r][c].piece+" "+square[r][c].eatable);
            }
        }
    }
    public void resetBackground(){//Creates black & white squares
        for (int r = 0; r < size; r++) {
            int count = r;
            for (int c = 0; c < size; c++) {
                square[r][c].eatable = false;
                if(count%2==1)
                    square[r][c].setBackground(new Color(110, 122, 140));
                else
                    square[r][c].setBackground(new Color(255, 255, 255));
                count++;
            }
        }
    }
    
    
    public void setImagesValues(){
        int black = 11;
        int white = 21;
        for(int i=0; i<size; i++,black++, white++){
            //set Black pieces
            square[0][i].setIcon(new ImageIcon("images/"+black+".png"));square[0][i].piece = black;
            square[1][i].setIcon(new ImageIcon("images/10.png"));square[1][i].piece = 10;
            //set White pieces
            square[6][i].setIcon(new ImageIcon("images/20.png"));square[6][i].piece = 20;
            square[7][i].setIcon(new ImageIcon("images/"+white+".png"));square[7][i].piece = white;
            }   
    }
    

      
    void moveit(int newR, int newC){
        if(square[newR][newC].eatable == true){
            square[newR][newC].setIcon(new ImageIcon("images/"+square[oldR][oldC].piece+".png"));
            square[oldR][oldC].setIcon(null);
            square[newR][newC].piece = square[oldR][oldC].piece;
            square[oldR][oldC].piece = 0;
            resetBackground();
            oldTurn=newTurn;
            if(oldTurn==1){
                turnShow.setText("   White's Turn");
            }else turnShow.setText("   Black's Turn");
        }
        else
            resetBackground();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        for(int r = 0; r < size; r++)
            for(int c = 0; c < size; c++){
                if(ae.getSource() == square[r][c]){
                    if(gameState == false){    
                        gameState = true;
                        timer();
                    }
                    if(square[r][c].piece!=0 && clicked==false){
                        if(square[r][c].piece<19){newTurn=1;}
                        else{newTurn=2;}
                    
                        if(newTurn!=oldTurn){
                            oldR=r;
                            oldC=c;
                            if(square[r][c].piece==15 || square[r][c].piece==25){
                                checkmate(square[r][c].piece);
                            }
                            possibleMoves(r,c);
                            clicked=true;
                        }
                        else
                            System.out.println("Wrong color");
                    }
                    else if(clicked==true){
                        System.out.println(r+""+c+" : "+square[r][c].piece+" "+square[r][c].eatable);
                        moveit(r,c);
                        clicked=false;
                    }
                }
            }
    }
        
    public void possibleMoves( int r, int c){//Shows possible moves in red
        int piece=square[r][c].piece;
        int Rr,Rc; //Right possible moves
        int Lr,Lc; //Left possible moves
        int Dr,Dc; //Down possible moves
        int Ur,Uc; //Up possible moves
        Rr = Lr = Dr = Ur = r;
        Rc = Lc = Dc = Uc = c;
        square[r][c].setBackground(new Color(247, 96, 96));
        //Black Pawn
        try{
        if(piece==10){
            try{
            if(square[Dr+1][Dc].piece==0){
                square[Dr+1][Dc].setBackground(Color.red);
                square[Dr+1][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Dr+1][Dc+1].piece>19){
                square[Dr+1][Dc+1].setBackground(Color.red);
                square[Dr+1][Dc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Dr+1][Dc-1].piece>19){
                square[Dr+1][Dc-1].setBackground(Color.red);
                square[Dr+1][Dc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        //White Pawn
        else if(piece==20){
            try{
            if(square[Ur-1][Uc].piece==0){
                square[Ur-1][Uc].setBackground(Color.red);
                square[Ur-1][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Dr-1][Dc+1].piece<20 && square[Dr-1][Dc+1].piece>0){
                square[Dr-1][Dc+1].setBackground(Color.red);
                square[Dr-1][Dc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Dr-1][Dc-1].piece<20 && square[Dr-1][Dc-1].piece>0){
                square[Dr-1][Dc-1].setBackground(Color.red);
                square[Dr-1][Dc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        //Rook
        else if(piece==11 || piece==18 || piece==21 || piece==28){
            try{//DOWN moves
            for(Dr++; square[Dr][Dc].piece==0; Dr++){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }
            if((square[Dr][Dc].piece>19 && square[r][c].piece<19) || (square[Dr][Dc].piece<19 && square[r][c].piece>19)){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//UP moves
            for(Ur--; square[Ur][Uc].piece==0; Ur--){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }
            if((square[Ur][Uc].piece>19 && square[r][c].piece<19) || (square[Ur][Uc].piece<19 && square[r][c].piece>19)){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//RIGHT moves
            for(Rc++; square[Rr][Rc].piece==0;Rc++){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }
            if((square[Rr][Rc].piece>19 && square[r][c].piece<19) || (square[Rr][Rc].piece<19 && square[r][c].piece>19)){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//LEFT moves
            for(Lc--; square[Lr][Lc].piece==0;Lc--){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }
            if((square[Lr][Lc].piece>19 && square[r][c].piece<19) || (square[Lr][Lc].piece<19 && square[r][c].piece>19)){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        //Horse
        else if(piece==12 || piece==17 || piece==27|| piece==22){
            try{
            if(square[Dr+2][Dc-1].piece==0 || (square[Dr+2][Dc-1].piece>19 && square[r][c].piece<19) || (square[Dr+2][Dc-1].piece<19 && square[r][c].piece>19)){
                square[Dr+2][Dc-1].setBackground(Color.red);
                square[Dr+2][Dc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Dr+2][Dc+1].piece==0 || (square[Dr+2][Dc+1].piece>19 && square[r][c].piece<19) || (square[Dr+2][Dc+1].piece<19 && square[r][c].piece>19)){
                square[Dr+2][Dc+1].setBackground(Color.red);
                square[Dr+2][Dc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Ur-2][Uc-1].piece==0 || (square[Ur-2][Uc-1].piece>19 && square[r][c].piece<19) || (square[Ur-2][Uc-1].piece<19 && square[r][c].piece>19)){
                square[Ur-2][Uc-1].setBackground(Color.red);
                square[Ur-2][Uc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Ur-2][Uc+1].piece==0 || (square[Ur-2][Uc+1].piece>19 && square[r][c].piece<19) || (square[Ur-2][Uc+1].piece<19 && square[r][c].piece>19)){
                square[Ur-2][Uc+1].setBackground(Color.red);
                square[Ur-2][Uc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Rr+1][Rc+2].piece==0 || (square[Rr+1][Rc+2].piece>19 && square[r][c].piece<19) || (square[Rr+1][Rc+2].piece<19 && square[r][c].piece>19)){
                square[Rr+1][Rc+2].setBackground(Color.red);
                square[Rr+1][Rc+2].eatable = true;
            }
            }catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Rr-1][Rc+2].piece==0 || (square[Rr-1][Rc+2].piece>19 && square[r][c].piece<19) || (square[Rr-1][Rc+2].piece<19 && square[r][c].piece>19)){
                square[Rr-1][Rc+2].setBackground(Color.red);
                square[Rr-1][Rc+2].eatable = true;
            }
            }catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Lr+1][Lc-2].piece==0 ||  (square[Lr+1][Lc-2].piece>19 && square[r][c].piece<19) || (square[Lr+1][Lc-2].piece<19 && square[r][c].piece>19)){
                square[Lr+1][Lc-2].setBackground(Color.red);
                square[Lr+1][Lc-2].eatable = true;
            }
            }catch(ArrayIndexOutOfBoundsException U){}
            try{
            if(square[Lr-1][Lc-2].piece==0 ||  (square[Lr-1][Lc-2].piece>19 && square[r][c].piece<19) || (square[Lr-1][Lc-2].piece<19 && square[r][c].piece>19)){
                square[Lr-1][Lc-2].setBackground(Color.red);
                square[Lr-1][Lc-2].eatable = true;
            }
            }catch(ArrayIndexOutOfBoundsException U){}
        }
        /*-----BISHOP-----*/
        else if(piece==13 || piece==16 || piece==26|| piece==23){
            //Down-Right moves
            try{
            for(Dr++,Dc++; square[Dr][Dc].piece==0; Dr++,Dc++){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }
            if((square[Dr][Dc].piece>19 && square[r][c].piece<19) || (square[Dr][Dc].piece<19 && square[r][c].piece>19)){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Up-Right moves
            try{
            for(Ur--,Uc++; square[Ur][Uc].piece==0; Ur--,Uc++){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }
            if((square[Ur][Uc].piece>19 && square[r][c].piece<19) || (square[Ur][Uc].piece<19 && square[r][c].piece>19)){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Up-Left moves
            try{
            for(Rr--,Rc--; square[Rr][Rc].piece==0; Rr--,Rc--){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }
            if((square[Rr][Rc].piece>19 && square[r][c].piece<19) || (square[Rr][Rc].piece<19 && square[r][c].piece>19)){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Down-Left moves
            try{
            for(Lr++,Lc-- ; square[Lr][Lc].piece==0 ; Lr++,Lc--){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }
            if((square[Lr][Lc].piece>19 && square[r][c].piece<19) || (square[Lr][Lc].piece<19 && square[r][c].piece>19)){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        /*----QUEEN----*/
        else if(piece==14 || piece==24 ){
            //Vertical & horizontal moves
            try{//DOWN moves
            for(Dr++; square[Dr][Dc].piece==0; Dr++){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }
            if((square[Dr][Dc].piece>19 && square[r][c].piece<19) || (square[Dr][Dc].piece<19 && square[r][c].piece>19)){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//UP moves
            for(Ur--; square[Ur][Uc].piece==0; Ur--){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }
            if((square[Ur][Uc].piece>19 && square[r][c].piece<19) || (square[Ur][Uc].piece<19 && square[r][c].piece>19)){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//RIGHT moves
            for(Rc++; square[Rr][Rc].piece==0;Rc++){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }
            if((square[Rr][Rc].piece>19 && square[r][c].piece<19) || (square[Rr][Rc].piece<19 && square[r][c].piece>19)){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{//LEFT moves
            for(Lc--; square[Lr][Lc].piece==0;Lc--){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }
            if((square[Lr][Lc].piece>19 && square[r][c].piece<19) || (square[Lr][Lc].piece<19 && square[r][c].piece>19)){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Angular moves
            Rr = Lr = Dr = Ur = r;
            Rc = Lc = Dc = Uc = c;
            //Down-Right moves
            try{
            for(Dr++,Dc++; square[Dr][Dc].piece==0; Dr++,Dc++){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }
            if((square[Dr][Dc].piece>19 && square[r][c].piece<19) || (square[Dr][Dc].piece<19 && square[r][c].piece>19)){
                square[Dr][Dc].setBackground(Color.red);
                square[Dr][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Up-Right moves
            try{
            for(Ur--,Uc++; square[Ur][Uc].piece==0; Ur--,Uc++){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }
            if((square[Ur][Uc].piece>19 && square[r][c].piece<19) || (square[Ur][Uc].piece<19 && square[r][c].piece>19)){
                square[Ur][Uc].setBackground(Color.red);
                square[Ur][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Up-Left moves
            try{
            for(Rr--,Rc--; square[Rr][Rc].piece==0; Rr--,Rc--){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }
            if((square[Rr][Rc].piece>19 && square[r][c].piece<19) || (square[Rr][Rc].piece<19 && square[r][c].piece>19)){
                square[Rr][Rc].setBackground(Color.red);
                square[Rr][Rc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //Down-Left moves
            try{
            for(Lr++,Lc-- ; square[Lr][Lc].piece==0 ; Lr++,Lc--){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }
            if((square[Lr][Lc].piece>19 && square[r][c].piece<19) || (square[Lr][Lc].piece<19 && square[r][c].piece>19)){
                square[Lr][Lc].setBackground(Color.red);
                square[Lr][Lc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        //King
        else if(piece==15 || piece==25){
            //VERTICAL & Horizontal move
            try{
            if((square[Dr+1][Dc].piece>19 && square[r][c].piece<19) || (square[Dr+1][Dc].piece<19 && square[r][c].piece>19) || square[Dr+1][Dc].piece==0){
                square[Dr+1][Dc].setBackground(Color.red);
                square[Dr+1][Dc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Ur-1][Uc].piece>19 && square[r][c].piece<19) || (square[Ur-1][Uc].piece<19 && square[r][c].piece>19)|| (square[Ur-1][Uc].piece==0)){
                square[Ur-1][Uc].setBackground(Color.red);
                square[Ur-1][Uc].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Rr][Rc+1].piece>19 && square[r][c].piece<19) || (square[Rr][Rc+1].piece<19 && square[r][c].piece>19) || (square[Rr][Rc+1].piece==0)){
                square[Rr][Rc+1].setBackground(Color.red);
                square[Rr][Rc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Lr][Lc-1].piece>19 && square[r][c].piece<19) || (square[Lr][Lc-1].piece<19 && square[r][c].piece>19) || (square[Lr][Lc-1].piece==0)){
                square[Lr][Lc-1].setBackground(Color.red);
                square[Lr][Lc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            //ANGULAR moves
            Rr = Lr = Dr = Ur = r;
            Rc = Lc = Dc = Uc = c;
            try{
            if((square[Dr+1][Dc+1].piece>19 && square[r][c].piece<19) || (square[Dr+1][Dc+1].piece<19 && square[r][c].piece>19) || square[Dr+1][Dc+1].piece==0){
                square[Dr+1][Dc+1].setBackground(Color.red);
                square[Dr+1][Dc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Ur-1][Uc+1].piece>19 && square[r][c].piece<19) || (square[Ur-1][Uc+1].piece<19 && square[r][c].piece>19)|| (square[Ur-1][Uc+1].piece==0)){
                square[Ur-1][Uc+1].setBackground(Color.red);
                square[Ur-1][Uc+1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Rr-1][Rc-1].piece>19 && square[r][c].piece<19) || (square[Rr-1][Rc-1].piece<19 && square[r][c].piece>19) || (square[Rr-1][Rc-1].piece==0)){
                square[Rr-1][Rc-1].setBackground(Color.red);
                square[Rr-1][Rc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
            try{
            if((square[Lr+1][Lc-1].piece>19 && square[r][c].piece<19) || (square[Lr+1][Lc-1].piece<19 && square[r][c].piece>19) || (square[Lr+1][Lc-1].piece==0)){
                square[Lr+1][Lc-1].setBackground(Color.red);
                square[Lr+1][Lc-1].eatable = true;
            }}catch(ArrayIndexOutOfBoundsException U){}
        }
        }catch(ArrayIndexOutOfBoundsException U){}
        finally
        {
            
            System.out.println("Button"+r+""+c);
            for (r = 0; r < size; r++) {
                for (c = 0; c < size; c++) {
                    if(square[r][c].eatable)
                    System.out.println(r+""+c+" : "+square[r][c].piece+" "+square[r][c].eatable);
                }
            }
        }
    }
    
    public void checkmate(int king){
        if(king==15){
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    possibleMoves(r,c);
                }
            }
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                   if(square[r][c].eatable==true){
                       square[r][c].checkable=true;
                   }
                resetBackground();
                
                }
            }
        }
    }
    
    public void timer(){
        Thread th = new Thread(){
            public void run(){
                for(;;){
                    try{
                        sleep(1000);
                        
                        if(seconds>59){
                            seconds=0;
                            minutes++;
                        }
                        if(minutes>60){
                            minutes=0;
                            hours++;
                        }
                        timeLbl.setText(hours+":"+minutes+":"+seconds);
                        seconds++;
                    }catch(Exception e){}
                }
            }
        };
        th.start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePAN = new javax.swing.JPanel();
        turnShow = new javax.swing.JTextField();
        lblWhiteTime = new javax.swing.JLabel();
        timeLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chess");

        gamePAN.setPreferredSize(new java.awt.Dimension(68, 417));
        gamePAN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                gamePANMouseReleased(evt);
            }
        });
        gamePAN.setLayout(new java.awt.GridLayout(8, 8, 2, 2));

        turnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turnShowActionPerformed(evt);
            }
        });

        lblWhiteTime.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblWhiteTime.setText("Time:");

        timeLbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        timeLbl.setText("00:00:00");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamePAN, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblWhiteTime)
                .addGap(18, 18, 18)
                .addComponent(timeLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(turnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gamePAN, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turnShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWhiteTime)
                    .addComponent(timeLbl))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gamePANMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_gamePANMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_gamePANMouseReleased

    private void turnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turnShowActionPerformed
        
    }//GEN-LAST:event_turnShowActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChessOmairFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChessOmairFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChessOmairFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChessOmairFram.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChessOmairFram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel gamePAN;
    private javax.swing.JLabel lblWhiteTime;
    private javax.swing.JLabel timeLbl;
    private javax.swing.JTextField turnShow;
    // End of variables declaration//GEN-END:variables
}
