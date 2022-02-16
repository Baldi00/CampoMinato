/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author Andrea
 */
public class Casella extends JButton{
    
    private int posX, posY;
    private int valore;
    private boolean premuto = false;
    
    public Casella(int posX, int posY){
        valore = 0;
        this.posX = posX;
        this.posY = posY;
        
        setFont(new Font("Arial", Font.BOLD, 20));
        setBackground(Color.green);
        setPreferredSize(new Dimension(45,45));
        setFocusPainted(false);
        setBorder(new LineBorder(Color.BLACK));
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }    
    
    public void premi(){
        premuto = true;
        if(valore==-1){
            setBackground(Color.red);
            setText("X");
        }else{
            if(valore!=0)
                setText(""+valore);
            
            switch (valore) {
                case 1:
                    setForeground(Color.blue);
                    break;
                case 2:
                    setForeground(new Color(0,128,0));
                    break;
                default:
                    if(valore>=3)
                        setForeground(Color.red);
                    else
                        setText("");
                    break;
            }
            
            setBackground(Color.yellow);
        }
        
    }
    
    public boolean isPremuto(){
        return premuto;
    }
}
