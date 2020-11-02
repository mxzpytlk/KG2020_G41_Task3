package rybas;

import rybas.linedrawers.LineDrawer;
import rybas.linedrawers.WooLineDrawer;
import rybas.models.Line;
import rybas.models.SpiralMarkers;
import rybas.models.SpiralParametersException;
import rybas.models.SquareSpiral;
import rybas.pixeldrawers.BufferedImagePixelDrawer;
import rybas.pixeldrawers.PixelDrawer;
import rybas.points.RealPoint;
import rybas.points.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    private final ArrayList<SpiralMarkers> spirals = new ArrayList<>();
    private final ScreenConvertor sc = new ScreenConvertor(
            -2, 2, 4, 4, 800, 600
    );

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D bi_g = bi.createGraphics();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new WooLineDrawer(pd);
        sc.setScreenH(getHeight());
        sc.setScreenW(getWidth());
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.setColor(Color.black);

        for (SpiralMarkers ss : spirals) {
            ss.draw(ld, sc, bi_g);
        }

        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private ScreenPoint prevDrag;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            spirals.add(new SpiralMarkers(sc.s2r(new ScreenPoint(e.getX(), e.getY())),
                    sc.getW() / sc.getScreenW()));
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            for (SpiralMarkers sm : spirals) {
                if (sm.isPointInSpiral(sc.s2r(new ScreenPoint(e.getX(), e.getY())))) {
                    sm.changeMarkersVisibility();
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());

        for (SpiralMarkers sp : spirals) {
            if (sp.isPointInCenter(sc.s2r(current)) && sp.isShowMarkers()) {
                changeSpiralPosition(sp, current);
                return;
            }
        }

        moveScreen(current);
        repaint();
    }

    private void changeSpiralPosition(SquareSpiral spiral, ScreenPoint newPos) {
        spiral.setCentre(sc.s2r(newPos));
        repaint();
    }

    private void moveScreen(ScreenPoint current) {
        ScreenPoint delta;

        if (prevDrag != null) {
            delta = new ScreenPoint(
                    current.getX() - prevDrag.getX(),
                    current.getY() - prevDrag.getY());


            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(
                    deltaReal.getX() - zeroReal.getX(),
                    deltaReal.getY() - zeroReal.getY()
            );
            sc.setX(sc.getX() - vector.getX());
            sc.setY(sc.getY() - vector.getY());
            prevDrag = current;
        }
    }

    private boolean mousePressed = false;
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = new ScreenPoint(e.getX(), e.getY());
            repaint();
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            mousePressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevDrag = null;
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();

        for (SpiralMarkers sp : spirals) {
            if (sp.isPointInSpiral(sc.s2r(new ScreenPoint(e.getX(), e.getY()))) && sp.isShowMarkers()) {
                changeAmountOfLinesInSpiral(clicks, sp);
                return;
            }
        }

        changeImageSize(clicks);
    }

    private void changeImageSize(int clicks) {
        double scale = 1;
        double coef = clicks > 0 ?  1.1 : 0.9;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        repaint();
    }

    private void changeAmountOfLinesInSpiral(int clicks, SquareSpiral sp) {
        try {
            sp.setTurnAmount(sp.getTurnAmount() - clicks);
        } catch (SpiralParametersException spiralParametersException) {
            spiralParametersException.printStackTrace();
        }
        repaint();
    }
}
