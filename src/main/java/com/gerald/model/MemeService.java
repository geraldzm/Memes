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
import java.util.HashMap;
import java.util.LinkedList;

public class MemeService implements Runnable{
    private HashMap<String, ImageIcon> imageMemes;
    private LinkedList<Meme> memes;
    private int index, amount;
    private MyWindow myWindow;

    public MemeService(MyWindow myWindow) {
        this.myWindow = myWindow;
        imageMemes = new HashMap<>(5);
        memes = new LinkedList<Meme>();
        index = -1;
        addMemes(5);
    }

    public void OpenMeme(){
        try {
            Runtime.getRuntime().exec("google-chrome "+ getMeme().getPostLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next(){
        index++;
        if(index == 4){
            index = 3;
            memes.removeFirst();
            addMemes(2);
            myWindow.setButtonNextEnabled(false);
        }
        if(index == 1){
            myWindow.setButtonPreviousEnabled(true);
        }
        myWindow.setButtonOpenEnabled(true);

        setMemeToMyWindow();
    }

    public void previous(){
        index--;
        if(index == 0){
            myWindow.setButtonPreviousEnabled(false);
        }
        setMemeToMyWindow();
    }

    private void setMemeToMyWindow() {
        Meme meme = getMeme();
        myWindow.setOutputMeme(getMemeImage());
        myWindow.setTitleMeme(meme.getTitle(), meme.getSubreddit());
    }
    public Meme getMeme() {
        return memes.size() < 1 ? null : memes.get(index);
    }

    public ImageIcon getMemeImage() {
        return memes.size() < 1 ? null : imageMemes.get(memes.get(index).getUrl());
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

    private ImageIcon downloadImage(String url) {
        Image image = null;
        try {
            image = ImageIO.read(new URL(url));
            //se escala si es necesario
            int w = 600, h = 500;
            if(myWindow != null){
                w = myWindow.getOutputMemeDimension().width;
                h = myWindow.getOutputMemeDimension().height;
            }
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
            if(change) image = image.getScaledInstance(actuallW, actuallH, BufferedImage.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(image);
    }

    public void addMemes(int amount) {
        this.amount =  amount;
        new Thread(this).start();
    }


    @Override
    public void run() {
        for (int i = 0; i < amount; i++) {
            Meme meme = requestMeme();
            memes.add(meme);
            imageMemes.put(meme.getUrl(), downloadImage(meme.getUrl()));
        }
        if(memes.size() > 0 && imageMemes.size() > 0){
            myWindow.setButtonNextEnabled(true);
        }
    }

}
