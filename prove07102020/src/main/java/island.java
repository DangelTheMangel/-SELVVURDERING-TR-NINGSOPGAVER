import processing.core.PApplet;

import java.util.ArrayList;

public class island {
    PApplet p;
    int x,y,s, w, id;

    ArrayList<island> visitedList = new ArrayList<island>();
    ArrayList<island> tabooList = new ArrayList<island>();
    int shoreHeight, widthPX, pixToMeter, posX,posY;
    island(PApplet p, int x, int y, int s, int w,int id){
        this.p = p;
        this.x = x;
        this.y = y;
        this.s=s;
        this.w=w;
        this.id = id;

        visitedList.add(this);

        shoreHeight = p.height/6;
        widthPX = p.height -(shoreHeight)*2;
        pixToMeter = widthPX/w;

        posX =  (x * pixToMeter);
        posY = p.height - (y* pixToMeter + (shoreHeight));
    }

    void draw() {
       // System.out.println("x: " + posX + "  y: " + posY);
        p.text("(" + x + ";" + y + ")",posX+s,posY +s);
        p.ellipse(posX,posY,s,s);
    }


    boolean closeToShore(ArrayList<island> islandArrayList, ArrayList<island> tabooList) {
        tabooList.add(this);
        if(y >= w- 30){
            return true;
        } else {
            for (int i = 0 ; i < islandArrayList.size(); ++i) {
                island conc = islandArrayList.get(i);
                if (tabooList.contains(conc) || visitedList.contains(conc))
                    continue;
                else {
                    float dist = p.sqrt(p.pow(conc.x - x, 2) + p.pow(conc.y - y, 2));
                    if (dist > 30) {
                        visitedList.add(conc);
                        continue;
                    } else {
                        boolean success = conc.closeToShore(islandArrayList, tabooList);
                        if (!success)
                            tabooList.remove(conc);
                        else
                            return success;
                    }
                }
            }
            return  false;
        }
    }
}
