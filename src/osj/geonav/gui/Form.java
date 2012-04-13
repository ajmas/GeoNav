package osj.geonav.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Form
{
    
    private List<Field> fieldList;
    private Map<String,Field>  fieldMap;
    
    public Form()
    {
        super();
        this.fieldList = new ArrayList<Field>();
        this.fieldMap  = new HashMap<String,Field>();
    }

    
    public void add ( Field field )
    {
        this.fieldList.add(field);
        this.fieldMap.put(field.getName(),field);
    }
    
    public Field getField ( String name )
    {
        return this.fieldMap.get(name);
    }
    
    public JComponent createFormComponent ()
    {
        JComponent comp = new JPanel();
        comp.setLayout(new GridLayout(2,this.fieldList.size()));
        for ( Field field : this.fieldList )
        {
            comp.add(new JLabel(field.getName()));
            comp.add((JComponent)field);
        }
        return comp;
    }

    public static void main ( String[] args )
    {
        JFrame f = new JFrame();
        
        Form form = new Form();
        form.add( new TextField("Myfield","",""));
        form.add( new TextField("Myfield2","",""));   
                
        
//        JPanel spacerN = new JPanel();
//        spacerN.setMinimumSize(new Dimension(30,30));
//        spacerN.setMaximumSize(new Dimension(5000,30));
//        
//        JPanel spacerE = new JPanel();
//        spacerE.setMinimumSize(new Dimension(30,30));
//        spacerE.setMaximumSize(new Dimension(30,5000));
//        
//        JPanel spacerE = new JPanel();
//        spacerE.setMinimumSize(new Dimension(30,30));
//        spacerE.setMaximumSize(new Dimension(30,5000));
//        
        JPanel centerPanel = new JPanel();
        centerPanel.add(form.createFormComponent(),BorderLayout.NORTH);
//        /centerPanel.setBackground(Color.BLUE);
        centerPanel.setBorder(BorderFactory.createLineBorder(centerPanel.getBackground(),15));
//        f.getContentPane().add(spacerN,BorderLayout.NORTH);
        f.getContentPane().add(centerPanel);
//        f.getContentPane().add(spacerE,BorderLayout.EAST);
        f.setVisible(true);
    }
}
