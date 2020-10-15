import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("main");
    }

    ArrayList<island> islandArrayList = new ArrayList<island>();
    AlmindeligKnap btnRestart;
    AlmindeligKnap btnAddIsland;
    ArrayList<island> taboo = new ArrayList<island>();
    public int w = 120, l = 320, topH = height / 6, downH = height - height / 6;

    @Override
    public void settings() {
        size(1280, 720);

    }

    @Override
    public void setup() {
        for (int i = 0; i < 20; ++i) {
            islandArrayList.add(new island(this, (int) random(0, l), (int) random(0, w), (int) random(5, 10), w, i));
        }

        btnRestart = new AlmindeligKnap(this, width - width / 6 - 20, 10, width / 12, width / 12, "Reset");
        btnAddIsland = new AlmindeligKnap(this, width - width / 12 - 10, 10, width / 12, width / 12, "Add\nIsland");

        //System.out.println(islandArrayList.get(0).closeToShore(islandArrayList, taboo));

        topH = height / 6;
        downH = height - height / 6;
    }

    @Override
    public void draw() {
        clear();
        background(20, 100, 180);
        fill(140, 120, 20);
        rect(0, 0, width, topH);
        fill(50, 40, 20);
        rect(0, downH, width, topH);
        btnRestart.tegnKnap();

        btnAddIsland.tegnKnap();
        for (int i = 0; i < islandArrayList.size(); ++i) {
            island b = islandArrayList.get(i);
            b.draw();
        }

        boolean success = false;
        for (int i = 0; i < islandArrayList.size(); ++i) {

            if(islandArrayList.get(i).y> 30)
                continue;

            taboo.clear();
            success = islandArrayList.get(i).closeToShore(islandArrayList, taboo);
            if (success) {
                break;
            }
        }

        if (success) {
            for (int i = 0; i < taboo.size(); ++i) {
                if (i == 0) {
                    line(taboo.get(i).posX, downH, taboo.get(i).posX, taboo.get(i).posY);
                    continue;
                }
                line(taboo.get(i - 1).posX, taboo.get(i - 1).posY, taboo.get(i).posX, taboo.get(i).posY);
            }
            island last = taboo.get(taboo.size() - 1);
            line(last.posX, last.posY, last.posX, topH);
        }

        if (btnRestart.klikket) {
            islandArrayList.clear();
            setup();
            btnRestart.registrerRelease();
        }

        if (btnAddIsland.klikket) {
            islandArrayList.add(new island(this, (int) random(0, l), (int) random(0, w), (int) random(5, 10), w, taboo.size() + 1));
            Collections.shuffle(islandArrayList);
            btnAddIsland.registrerRelease();
        }
    }

    @Override
    public void mouseClicked() {
        btnRestart.registrerKlik(mouseX, mouseY);
        btnAddIsland.registrerKlik(mouseX, mouseY);
    }
}
