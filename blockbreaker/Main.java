package blockbreaker;

import java.applet.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;


public class Main {

    public static void main(String[] args){
        JFrame frame = new JFrame();
        Gameplay gamePlay = new Gameplay();
        frame.setBounds(10, 10, 700, 600);
        frame.setTitle("Breakout");
        frame.add(gamePlay);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
