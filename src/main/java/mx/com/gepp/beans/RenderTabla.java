/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.gepp.beans;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author oscarmondragon
 */
public class RenderTabla extends DefaultTableCellRenderer {
    
    
    public RenderTabla(){
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        String status =  table.getValueAt(row, 2).toString();
         Border b;
         
                     b = BorderFactory.createEtchedBorder();

            
        if (status =="Enviado") {
            setBackground(Color.GREEN);
            setForeground(Color.BLACK);
           
        } 
        else if (status == "Pendiente") {
            setBackground(Color.WHITE);
            setForeground(Color.BLACK);
        }
        if(value instanceof JButton){
            JButton boton = (JButton)value;
            return boton;
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
    }
    
}
