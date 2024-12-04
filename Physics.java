package physics;

/*  THE KINEMATICS EQUATIONS
 *  
 * New Position
 *  r = ro + vo  t + 1/2 a t^2
 * 
 * New Velocity
 *  V = Vo + at
 * 
 *  THIS CLASS REFERENCES AppTest FOR ITS MAIN CLASS
 */
import java.awt.*;

import AudioANDfailScreen.AudioPF;
import AudioANDfailScreen.FailedPassScreen;
import levelDesign.*;


public class Physics {
    private static final double G = -9.8; // gravity = 9.81 m/s
    private static final double deltaT = 0.005; // how often cacluation is made.
    private static final double deltaTraj = 0.5; // dealtaT for trajectory test
    private static final int levelWidth = 800;
    private static final int levelHeight = 400;
    private static final int dotSize = 7;

    public static double inputVelocity;
    public static double inputAngle;
    private static double rx, ry;
    private static double Vx, Vy;

    public static DrawingPanel workingPanel;
    public static Graphics workingGraphics;

    public static int currentLevel;
    public static boolean levelCompletion;
    
        public static boolean play() {
            FailedPassScreen f = new FailedPassScreen();
            AudioPF a = new AudioPF();

            // pluh!!
            a.ball();

            double angleRAD = inputAngle * Math.PI / 180;
            rx = 0; // reset x position
            ry = 0; // reset y position
            Vx = inputVelocity * Math.cos(angleRAD); // initial x velocity
            Vy = inputVelocity * Math.sin(angleRAD); // initial y velocity
    
            workingGraphics.setColor(Color.CYAN);
        
            boolean inbound = true;
    
            // boolean flags
            boolean levelFailed = false;
            boolean levelClear = false;
    
            // instances

    
            while (inbound) {
                // workingGraphics.drawString(String.valueOf(Vx) + ", " + String.valueOf(Vy), 0, 100);
                workingPanel.sleep(1);
                workingGraphics.fillOval((int)rx, levelHeight - (int)ry, dotSize, dotSize);
                
                
    
                rx += Vx * deltaT;
                ry += Vy * deltaT + (G * Math.pow(deltaT, 2)) / 2;
                Vy += G * deltaT; // update vertical velocity do to G
                
                // check for collision (dumb method)
                chooseLevelC();
                
                inbound = (rx > 0 && ry < levelHeight && ry > 0);
                if (!inbound) {
                    // set a levelFailed flag to true
                    levelFailed = true;
                    if(levelFailed){
                        f.fail(); // screen
                        a.fail(); // sfx
                    }
                    levelCompletion = false; //var for savedata
                    return levelCompletion;
            }
            if (rx > levelWidth) {
                // set a levelClear flag to true

                levelClear = true;
                if(levelClear){
                    f.clear(); // screen
                    a.pass(); // sfx
                }
                levelCompletion = true; //var for savedata
                return levelCompletion;
            }
        }

        return levelCompletion;
    }

    public static void tradjectTest() {
        double angleRAD = inputAngle * Math.PI / 180;
        rx = 0; // reset x position
        ry = 0; // reset y position
        Vx = inputVelocity * Math.cos(angleRAD); // initial x velocity
        Vy = inputVelocity * Math.sin(angleRAD); // initial y velocity
        
        for (byte i = 0; i < 10; i++) {
            workingGraphics.fillOval((int)rx, levelHeight - (int)ry, dotSize, dotSize);

            rx += Vx * deltaTraj;
            ry += Vy * deltaTraj + (G * Math.pow(deltaT, 2)) / 2;
            Vy += G * deltaTraj; // update vertical velocity do to G
        }
    }

    // give a 5 pixel buffer
    private static void level1Collision() {
        // hits left side of block
        if ((rx > 350 && levelHeight - ry > 200) && (rx < 355)) Vx = -Vx;

        // hits top of block
        if ((rx > 350 && levelHeight - ry > 200) && (rx < 450) && levelHeight - ry < 205) Vy = -Vy;

        // there shouldn't be any way to hit the right side of the block
    }

    private static void level2Collision() {
        // rectangle 1
        // hits left side of bottom block
        if ((rx > 350 && levelHeight - ry > 200) && (rx < 353)) Vx = -Vx;
        // hits top of bottom block
        if ((rx > 350 && levelHeight - ry > 200) && (rx < 450) && levelHeight - ry < 205) Vy = -Vy;
        // hits right of bottom block
        if ((rx < 450 && levelHeight - ry > 200) && (rx > 445)) Vx = -Vx;

        // rectangle 2
        // hits left side of top block
        if ((rx > 550 && levelHeight - ry < 200) && (rx < 555)) Vx = -Vx;
        // hits bottom of top block, shouldn't be possible so i stopped trying
        // if ((rx > 550 && levelHeight - ry < 200) && (rx < 650) && levelHeight - ry < 205) Vy = -Vy;

    }

    private static void level3Collision() {

    }

    private static void level4Collision() {

    }

    private static void chooseLevelC() { // a stupid way to choose level collision method
        if (currentLevel == 1) level1Collision();
        else if (currentLevel == 2) level2Collision();
        else if (currentLevel == 3) level3Collision();
        else if (currentLevel == 4) level4Collision();
    }
}