package rybas.linedrawers;


import rybas.pixeldrawers.PixelDrawer;
import rybas.points.ScreenPoint;

import java.awt.*;

import static java.lang.Math.abs;


public class WooLineDrawer implements LineDrawer {
    private final PixelDrawer pd;

    public WooLineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();

        boolean steep = abs(y1 - y2) > abs(x1 - x2);
        if (steep) {
            int val = x1;
            x1 = y1;
            y1 = val;
            val = x2;
            x2 = y2;
            y2 = val;
        }

        if (x1 > x2) {
            int val = x1;
            x1 = x2;
            x2 = val;
            val = y1;
            y1 = y2;
            y2 = val;
        }

        double dx = x2 - x1;
        double dy = y2 - y1;

        double gradient = dy / dx;
        double y = y1 + gradient;
        for (int x = x1 + 1; x < x2; ++x) {
            int intY = (int) y;
            if (steep) {
                pd.setPixel(intY + 1, x, new Color(0, 0, 0, (float) abs(y - intY)));
                pd.setPixel(intY, x, new Color(0, 0, 0, (float) (1 - abs(y - intY))));
            } else {
                pd.setPixel(x, intY + 1, new Color(0, 0, 0, (float) abs(y - intY)));
                pd.setPixel(x, intY, new Color(0, 0, 0, (float) (1 - abs(y - intY))));
            }
            y += gradient;
        }
    }
}
