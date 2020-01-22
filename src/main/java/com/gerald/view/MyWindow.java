package com.gerald.view;

import com.gerald.controller.MoveWindow;
import com.gerald.model.MemeService;

import javax.swing.*;
import java.awt.*;

public class MyWindow extends JFrame {
    private JLabel outputMeme;
    private JButton buttonPrevious;
    private JLabel title;
    private Color color;

    public MyWindow() throws HeadlessException {
        getContentPane().setLayout(new BorderLayout());
        setUndecorated(true);
        setBounds(400,150,840,700);
        MoveWindow moveWindow = new MoveWindow(this);
        addMouseListener(moveWindow);
        addMouseMotionListener(moveWindow);


        color = new Color(125,125,125);
        JPanel northPanel = new JPanel();
        JPanel centralPanel = new JPanel();
        JPanel southPanel = new JPanel();
        MemeService memeService = new MemeService(this);

        //panel norte
        title = new JLabel("Title: ");
        JButton buttonExit = new JButton("Exit");
        buttonExit.addActionListener(e -> System.exit(0));

        northPanel.add(title);
        northPanel.add(buttonExit);

        northPanel.setOpaque(true);
        northPanel.setBackground(color);

        //panel central
        outputMeme = new JLabel();
        centralPanel.setLayout(new BorderLayout());
        centralPanel.add(outputMeme, BorderLayout.CENTER);

        centralPanel.setOpaque(true);
        centralPanel.setBackground(color);

        //panel south
        JButton buttonOpenReddit = new JButton("Open");
        buttonOpenReddit.addActionListener(e -> memeService.OpenMeme());
        buttonPrevious = new JButton("Previous");
        buttonPrevious.addActionListener(e -> memeService.previous());
        JButton buttonNext = new JButton("Next");
        buttonNext.addActionListener(e -> memeService.next());

        southPanel.add(buttonOpenReddit);
        southPanel.add(buttonPrevious);
        southPanel.add(buttonNext);

        southPanel.setOpaque(true);
        southPanel.setBackground(color);

        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(centralPanel, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setTitleMeme(String title, String subreddit) {
        this.title.setText("Title: " + title+"   Subreddit: " + subreddit);
    }

    public void setOutputMeme(ImageIcon imageIcon){
        outputMeme.setIcon(imageIcon);
    }

    public void setButtonPreviousEnabled(boolean enabled){
        buttonPrevious.setEnabled(enabled);
    }

    public Dimension getOutputMemeDimension(){
        return outputMeme.getSize();
    }

}
