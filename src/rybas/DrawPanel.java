package rybas;

import rybas.linedrawers.DDALineDrawer;
import rybas.linedrawers.LineDrawer;
import rybas.models.Line;
import rybas.pixeldrawers.BufferedImagePixelDrawer;
import rybas.pixeldrawers.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConvertor sc = new ScreenConvertor(
            -2, 2, 4, 4, 800, 600
    );
    private Line xAxis = new Line(-1, 0, 1, 0);
    private Line yAxis = new Line(0, -1, 0, 1);

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bi_g = bi.createGraphics();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        sc.setScreenH(getHeight());
        sc.setScreenW(getWidth());
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.setColor(Color.black);
        drawAll(ld);
        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawAll(LineDrawer ld) {
        ld.drawLine(sc.realToScreen(xAxis.getP1()), sc.realToScreen(xAxis.getP2()));
        ld.drawLine(sc.realToScreen(yAxis.getP1()), sc.realToScreen(yAxis.getP2()));
        for (Line l : lines) {
            ld.drawLine(sc.realToScreen(l.getP1()),
                    sc.realToScreen(l.getP2()));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

//    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {
//        prevDrag
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//
//        sc.setX();
//        sc.setY();
//        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
