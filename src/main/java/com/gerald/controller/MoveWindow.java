package com.gerald.controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveWindow extends MouseAdapter {
    private Component component;
    private int x, y;
    private Dimension screeSize;
    private double w;
    private double h;
    private double Width;
    private double Height;

    public MoveWindow(Component component) {
        this.component = component;
        x = component.getX();
        y = component.getY();
        screeSize = Toolkit.getDefaultToolkit().getScreenSize();

        w = screeSize.getWidth();
        h = screeSize.getHeight();
        Width = component.getWidth();
        Height = component.getHeight();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int nx = component.getX() + e.getX() - x;
        int ny = component.getY() + e.getY() - y;
        if (nx < 0) {
            nx = 0;
        }
        if (nx + Width > w) {
            nx = (int) (w - Width);
        }
        if (ny < 0) {
            ny = 0;
        }
        if (ny + Height > h) {
            ny = (int) (h - Height);
        }
        component.setLocation(nx, ny);
    }
}
