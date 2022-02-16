/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Andrea
 */
public class Finestra extends JFrame implements ActionListener, MouseListener{
    
    private int dimensione = 20;
    private int mine = 20;
    
    private int [][] valori;
    private boolean [][] visitate;
    
    private JPanel pannelloPrincipale, pannelloTools;
    private Casella[][] campo;
    
    private JButton nuovaPartitaButton, ricominciaButton;
    private JLabel numMineLabel, dimensioneLabel;
    private JTextField numMineTextField, dimensioneTextField;
    
    public Finestra(){
        pannelloTools = new JPanel();
        nuovaPartitaButton = new JButton("Nuova partita");
        nuovaPartitaButton.setName("Nuova partita");
        nuovaPartitaButton.addActionListener(this);
        ricominciaButton = new JButton("Ricomincia");
        ricominciaButton.setName("Ricomincia");
        ricominciaButton.addActionListener(this);
        numMineLabel = new JLabel("Mine (1-200): ");
        dimensioneLabel = new JLabel("Dimensione (10-50): ");
        numMineTextField = new JTextField(""+mine);
        numMineTextField.setPreferredSize(new Dimension(100, 25));
        dimensioneTextField = new JTextField(""+dimensione);
        dimensioneTextField.setPreferredSize(new Dimension(100, 25));
        
        dimensioneTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(Integer.valueOf(dimensioneTextField.getText())<10)
                    dimensioneTextField.setText("10");
                if(Integer.valueOf(dimensioneTextField.getText())>50)
                    dimensioneTextField.setText("50");
                numMineLabel.setText("Mine (1-" + Integer.valueOf(dimensioneTextField.getText())*Integer.valueOf(dimensioneTextField.getText())/2 + "): ");
            }
        });
        
        pannelloTools.add(nuovaPartitaButton);
        pannelloTools.add(dimensioneLabel);
        pannelloTools.add(dimensioneTextField);
        pannelloTools.add(numMineLabel);
        pannelloTools.add(numMineTextField);
        pannelloTools.add(ricominciaButton);
        
        pannelloPrincipale = new JPanel(new GridLayout(dimensione, dimensione, 0, 0));
        
        setTitle("Campo Minato");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        inizializza();
        
        add("North",pannelloTools);
        add(pannelloPrincipale);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void inizializza(){
        valori = new int[dimensione][dimensione];
        visitate = new boolean[dimensione][dimensione];
        campo = new Casella[dimensione][dimensione];
        
        pannelloPrincipale.removeAll();
        remove(pannelloPrincipale);
        pannelloPrincipale = new JPanel(new GridLayout(dimensione, dimensione, 0, 0));
        
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                campo[i][j] = new Casella(i,j);
                campo[i][j].addActionListener(this);
                campo[i][j].addMouseListener(this);
                pannelloPrincipale.add(campo[i][j]);
            }
        }
        
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                valori[i][j]=0;
                campo[i][j].setValore(0);
            }
        }
        
        for(int i=0;i<mine;i++){
            int xTemp = (int)(Math.random()*dimensione), yTemp = (int)(Math.random()*dimensione);
            if(valori[xTemp][yTemp]!=-1)
                valori[xTemp][yTemp]=-1;
            else
                i--;
        }
        
        for(int i=1;i<dimensione-1;i++){         //Interno
            for(int j=1;j<dimensione-1;j++){
                int cont=0;
                if(valori[i][j]!=-1){
                    if(valori[i-1][j-1]==-1)cont++;
                    if(valori[i][j-1]==-1)cont++;
                    if(valori[i+1][j-1]==-1)cont++;
                    if(valori[i-1][j]==-1)cont++;
                    if(valori[i+1][j]==-1)cont++;
                    if(valori[i-1][j+1]==-1)cont++;
                    if(valori[i][j+1]==-1)cont++;
                    if(valori[i+1][j+1]==-1)cont++;
                    valori[i][j] = cont;
                }
            }
        }
        
        for(int i=1;i<dimensione-1;i++){         //Prima colonna
                int cont=0;
                if(valori[i][0]!=-1){
                    if(valori[i-1][0]==-1)cont++;
                    if(valori[i+1][0]==-1)cont++;
                    if(valori[i-1][1]==-1)cont++;
                    if(valori[i][1]==-1)cont++;
                    if(valori[i+1][1]==-1)cont++;
                    valori[i][0] = cont;
                }
        }
        
        for(int i=1;i<dimensione-1;i++){         //Ultima colonna
                int cont=0;
                if(valori[i][dimensione-1]!=-1){
                    if(valori[i-1][dimensione-2]==-1)cont++;
                    if(valori[i][dimensione-2]==-1)cont++;
                    if(valori[i+1][dimensione-2]==-1)cont++;
                    if(valori[i-1][dimensione-1]==-1)cont++;
                    if(valori[i+1][dimensione-1]==-1)cont++;
                    valori[i][dimensione-1] = cont;
                }
        }
        
        for(int j=1;j<dimensione-1;j++){         //Prima riga
                int cont=0;
                if(valori[0][j]!=-1){
                    if(valori[0][j-1]==-1)cont++;
                    if(valori[1][j-1]==-1)cont++;
                    if(valori[1][j]==-1)cont++;
                    if(valori[0][j+1]==-1)cont++;
                    if(valori[1][j+1]==-1)cont++;
                    valori[0][j] = cont;
                }
        }
        
        for(int j=1;j<dimensione-1;j++){         //Ultima riga
                int cont=0;
                if(valori[dimensione-1][j]!=-1){
                    if(valori[dimensione-2][j-1]==-1)cont++;
                    if(valori[dimensione-1][j-1]==-1)cont++;
                    if(valori[dimensione-2][j]==-1)cont++;
                    if(valori[dimensione-2][j+1]==-1)cont++;
                    if(valori[dimensione-1][j+1]==-1)cont++;
                    valori[dimensione-1][j] = cont;
                }
        }
        
        //Angoli
        if(valori[0][0]!=-1){
            int cont=0;
            if(valori[0][1]==-1)cont++;
            if(valori[1][1]==-1)cont++;
            if(valori[1][0]==-1)cont++;
            valori[0][0] = cont;
        }
        if(valori[0][dimensione-1]!=-1){
            int cont=0;
            if(valori[0][dimensione-2]==-1)cont++;
            if(valori[1][dimensione-2]==-1)cont++;
            if(valori[1][dimensione-1]==-1)cont++;
            valori[0][dimensione-1] = cont;
        }
        if(valori[dimensione-1][0]!=-1){
            int cont=0;
            if(valori[dimensione-2][0]==-1)cont++;
            if(valori[dimensione-2][1]==-1)cont++;
            if(valori[dimensione-1][1]==-1)cont++;
            valori[dimensione-1][0] = cont;
        }
        if(valori[dimensione-1][dimensione-1]!=-1){
            int cont=0;
            if(valori[dimensione-1][dimensione-2]==-1)cont++;
            if(valori[dimensione-2][dimensione-2]==-1)cont++;
            if(valori[dimensione-2][dimensione-1]==-1)cont++;
            valori[dimensione-1][dimensione-1] = cont;
        }
        
        for(int i=0;i<dimensione;i++){
            for(int j=0;j<dimensione;j++){
                campo[i][j].setValore(valori[i][j]);
                if(valori[i][j]!=0 && valori[i][j]!=-1){
                    visitate[i][j] = true;
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() instanceof Casella){
            Casella premuta = (Casella)ae.getSource();
            int valore = premuta.getValore();

            switch (valore) {
                case -1:
                    for(int i=0;i<dimensione;i++){
                        for(int j=0;j<dimensione;j++){
                            campo[i][j].setEnabled(false);
                            if(campo[i][j].getValore()==-1){
                                campo[i][j].setText("X");
                                campo[i][j].setBackground(Color.red);
                            }
                        }
                    }   JOptionPane.showMessageDialog(this,"Hai perso");
                    break;
                case 0:
                    espandi(premuta.getPosX(),premuta.getPosY());
                    break;
                default:
                    premuta.premi();
                    break;
            }
            
            int cont = 0;
            for(int i=0;i<dimensione;i++){
                for(int j=0;j<dimensione;j++){
                    if(campo[i][j].isPremuto())
                        cont++;
                }
            }
            
            if(cont==dimensione*dimensione-mine){
                for(int i=0;i<dimensione;i++){
                    for(int j=0;j<dimensione;j++){
                        campo[i][j].setEnabled(false);
                        campo[i][j].setBackground(new Color(0, 255, 255));
                        if(campo[i][j].getValore()!=0){
                            campo[i][j].setText(""+campo[i][j].getValore());
                        }
                        if(campo[i][j].getValore()==-1){
                            campo[i][j].setText("X");
                            campo[i][j].setBackground(Color.blue);
                        }
                    }
                }
                JOptionPane.showMessageDialog(this,"HAI VINTO");

            }
                
            
        }else if(ae.getSource() instanceof JButton){
            JButton premuto = (JButton)ae.getSource();
            boolean aggiornamentoConSuccesso = false;
            if(premuto.getName().equals("Nuova partita")){
                try{
                    if(!numMineTextField.getText().equals("") && !dimensioneTextField.getText().equals("") && Integer.parseInt(dimensioneTextField.getText())>=10 && Integer.parseInt(dimensioneTextField.getText())<=50 && Integer.parseInt(numMineTextField.getText())>0 && Integer.parseInt(numMineTextField.getText())<=Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2){
                        mine = Integer.parseInt(numMineTextField.getText());
                        dimensione = Integer.parseInt(dimensioneTextField.getText());
                        aggiornamentoConSuccesso = true;
                    }else{
                        JOptionPane.showMessageDialog(this,"Dati inseriti non validi\nDimensione: 10-50\nMine: 1-"+(Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2<50 || Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2>1250 ? 50 : Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2));
                    }
                }catch(NumberFormatException e){
                    try{
                        JOptionPane.showMessageDialog(this,"Dati inseriti non validi\nDimensione: 10-50\nMine: 1-"+(Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2<50 || Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2>1250 ? 50 : Integer.parseInt(dimensioneTextField.getText())*Integer.parseInt(dimensioneTextField.getText())/2));
                    }catch(NumberFormatException e2){
                        JOptionPane.showMessageDialog(this,"Dati inseriti non validi");
                    }
                }
                
                if(aggiornamentoConSuccesso){
                    inizializza();
                    add(pannelloPrincipale);
                    for(int i=0;i<dimensione;i++){
                        for(int j=0;j<dimensione;j++){
                            campo[i][j].setEnabled(true);
                            campo[i][j].setText("");
                            campo[i][j].setBackground(Color.green);
                        }
                    }
                    setVisible(false);
                    setVisible(true);
                }
            }else{
                for(int i=0;i<dimensione;i++){
                    for(int j=0;j<dimensione;j++){
                        campo[i][j].setEnabled(true);
                        campo[i][j].setText("");
                        campo[i][j].setBackground(Color.green);
                        visitate[i][j] = false;
                    }
                }
            }
            
        }
    }
    
    public void espandi(int i, int j){
        
        boolean [] cambiati = new boolean [8];
        
        campo[i][j].premi();
        visitate[i][j] = true;
        
        if(i>0 && valori[i-1][j]==0 && !visitate[i-1][j]){
            campo[i-1][j].premi();
            visitate[i-1][j] = true;
            cambiati[0] = true;
        }else if(i>0){
            campo[i-1][j].premi();
        }
        
        if(j>0 && valori[i][j-1]==0 && !visitate[i][j-1]){
            campo[i][j-1].premi();
            visitate[i][j-1] = true;
            cambiati[1] = true;
        }else if(j>0){
            campo[i][j-1].premi();
        }
        
        if(i<dimensione-1 && valori[i+1][j]==0 && !visitate[i+1][j]){
            campo[i+1][j].premi();
            visitate[i+1][j] = true;
            cambiati[2] = true;
        }else if(i<dimensione-1){
            campo[i+1][j].premi();
        }
        
        if(j<dimensione-1 && valori[i][j+1]==0 && !visitate[i][j+1]){
            campo[i][j+1].premi();
            visitate[i][j+1] = true;
            cambiati[3] = true;
        }else if(j<dimensione-1){
            campo[i][j+1].premi();
        }
        
        if(i>0 && j>0 && (valori[i][j-1]==0 || valori[i-1][j]==0) && valori[i-1][j-1]==0 && !visitate[i-1][j-1]){
            campo[i-1][j-1].premi();
            visitate[i-1][j-1] = true;
            cambiati[4] = true;
        }else if(i>0 && j>0 && (valori[i][j-1]==0 || valori[i-1][j]==0)){
            campo[i-1][j-1].premi();
        }
        
        if(i>0 && j<dimensione-1 && (valori[i][j+1]==0 || valori[i-1][j]==0) && valori[i-1][j+1]==0 && !visitate[i-1][j+1]){
            campo[i-1][j+1].premi();
            visitate[i-1][j+1] = true;
            cambiati[5] = true;
        }else if(i>0 && j<dimensione-1 && (valori[i][j+1]==0 || valori[i-1][j]==0)){
            campo[i-1][j+1].premi();
        }
        
        if(i<dimensione-1 && j>0 && (valori[i][j-1]==0 || valori[i+1][j]==0) && valori[i+1][j-1]==0 && !visitate[i+1][j-1]){
            campo[i+1][j-1].premi();
            visitate[i+1][j-1] = true;
            cambiati[6] = true;
        }else if(i<dimensione-1 && j>0 && (valori[i][j-1]==0 || valori[i+1][j]==0)){
            campo[i+1][j-1].premi();
        }
        
        if(i<dimensione-1 && j<dimensione-1 && (valori[i][j+1]==0 || valori[i+1][j]==0) && valori[i+1][j+1]==0 && !visitate[i+1][j+1]){
            campo[i+1][j+1].premi();
            visitate[i+1][j+1] = true;
            cambiati[7] = true;
        }else if(i<dimensione-1 && j<dimensione-1 && (valori[i][j+1]==0 || valori[i+1][j]==0)){
            campo[i+1][j+1].premi();
        }
        
        if(cambiati[0]) espandi(i-1,j);
        if(cambiati[1]) espandi(i,j-1);
        if(cambiati[2]) espandi(i+1,j);
        if(cambiati[3]) espandi(i,j+1);
        if(cambiati[4]) espandi(i-1,j-1);
        if(cambiati[5]) espandi(i-1,j+1);
        if(cambiati[6]) espandi(i+1,j-1);
        if(cambiati[7]) espandi(i+1,j+1);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if(SwingUtilities.isRightMouseButton(me)){
            Casella source = (Casella)me.getSource();
            if(!source.isPremuto()){
                if(source.getText().equals("!"))
                    source.setText("");
                else{
                    source.setText("!");
                    source.setForeground(Color.red);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
