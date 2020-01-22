package com.gerald.model;

import com.gerald.view.MyWindow;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MemeService {
    private ArrayList<ImageIcon> imageIcons;
    private ArrayList<Meme> memes;
    private MyWindow myWindow;
    private int index;

    public MemeService(MyWindow myWindow) {
        this.myWindow = myWindow;
        imageIcons = new ArrayList<>(3);
        memes = new ArrayList<>(3);
        index = 1;
    }

    public void OpenMeme(){

    }

    public void next(){

    }

    public void previous(){

    }

    private void setMemeToMyWindow(Meme meme){

    }

    private Meme requestMeme(){
        Meme meme = new Meme();
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.get("https://meme-api.herokuapp.com/gimme")
                    .asString();
            meme = new Gson().fromJson(response.getBody(), Meme.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return meme;
    }

    private ImageIcon downloadImage(String url){
        Image image = null;
        try {
            image = ImageIO.read(new URL(url));
            //se escala si es necesario
            int w = myWindow.getOutputMemeDimension().width, h = myWindow.getOutputMemeDimension().height;
            int actuallW = image.getWidth(null), actuallH = image.getHeight(null);
            boolean change = false;
            if(actuallW > w){
                actuallW = w;
                change = true;
            }
            if(actuallH > h){
                actuallH = h;
                change = true;
            }
            if (change)image = image.getScaledInstance(actuallW, actuallH, BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(image);
    }
}
