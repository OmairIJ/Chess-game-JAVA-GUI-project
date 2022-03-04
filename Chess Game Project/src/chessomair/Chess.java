
package chessomair;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;


public class Chess {
    Tiles[][] butArray = new Tiles[8][8];
    int size = 8;
    
    
    public void initialize() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                //Creat a new JButton
                butArray[r][c] = new Tiles();
                //Add buttons to the panel
                gamePAN.add(butArray[r][c]);
                butArray[r][c].piece = 0;
            }
        }
        resetBackground();
        setImagesValues();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                System.out.println(r+""+c+" : "+butArray[r][c].piece);
            }
        }
    }
    public void resetBackground(){
        //Create black & white colors
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if(r%2==0){
                    if(c%2==0)
                        butArray[r][c].setBackground(new Color(110, 122, 140));
                    else
                        butArray[r][c].setBackground(new Color(255, 255, 255));
                }
                else{
                    if(c%2==1)
                        butArray[r][c].setBackground(new Color(110, 122, 140));
                    else
                        butArray[r][c].setBackground(new Color(255, 255, 255));
                }
            }
        }
    }
    public void setImagesValues(){
        int serialB = 11;
        int serialW = 21;
        for(int i=0; i<size; i++,serialB++, serialW++){
            //set Black pieces
            butArray[0][i].setIcon(new ImageIcon("images/"+serialB+".png"));butArray[0][0].piece = 11;
            butArray[1][i].setIcon(new ImageIcon("images/BP.png"));butArray[1][i].piece = 10;
            //set White pieces
            butArray[7][i].setIcon(new ImageIcon("images/"+serialW+".png"));butArray[7][0].piece = 21;
            butArray[6][i].setIcon(new ImageIcon("images/WP.png"));butArray[7][i].piece = 20;
            }   
    }
    public void possibleMoves(int piece, int r, int c){
        if(piece==10){
            if(butArray[r+1][c].piece==0)
                butArray[r+1][c].setBackground(Color.red);
        }
        else if(piece==20){
            if(butArray[r-1][c].piece==0)
                butArray[r-1][c].setBackground(Color.red);
        }
    };
    public void actionPerformed(ActionEvent ae) {
        for(int r = 0; r < size; r++)
            for(int c = 0; c < size; c++){
                if(ae.getSource() == butArray[r][c]){
                    if(butArray[r][c].piece!=0)
                        possibleMoves(butArray[r][c].piece,r,c);
                    butArray[r][c].setBackground(Color.red);
                }
            }
    }
}
