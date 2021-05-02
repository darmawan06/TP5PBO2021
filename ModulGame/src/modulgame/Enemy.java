/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulgame;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Dikdik Darmawan
 */
public class Enemy extends GameObject {
    int speed;
    String berjalany = "Keatas";
    String berjalanx = "Kekiri";
    public Enemy(int x, int y, ID id, String LevelNow){
        super(x, y, id);
        setSpeed(LevelNow);
        //speed = 1;
    }
    
    public void setSpeed(String Level){
        if("Easy".equals(Level)){
            speed = 5;
        }else if("Medium".equals(Level)){
            speed = 10;
        }else if("Hard".equals(Level)){
            speed = 15;
        }
    }
    
    @Override
    public void tick() {
            if("Keatas".equals(berjalany)){
                y -= speed;
                if(y <= -5){
                    int value =(int)(Math.random()*5);
                    if(value % 2 == 0){
                        berjalany = "Kebawah";
                    }else{
                        berjalany = "Keatas";
                    }
                }
            }else if("Kebawah".equals(berjalany)){
                y += speed;
                if(y >= 500){
                    int value =(int)(Math.random()*5);
                    if(value % 2 == 0){
                        berjalany = "Kebawah";
                    }else{
                        berjalany = "Keatas";
                    }
                }
            }
            
            if("Kekiri".equals(berjalanx)){
                x -= speed;
                if(x <= 5){
                    int value =(int)(Math.random()*100);
                    if(value % 2 == 0){
                        berjalanx = "Kekanan";
                    }else{
                        berjalanx = "Kekiri";
                    }
                }
            }else if("Kekanan".equals(berjalanx)){
                x += speed;
                if(x >= 730){
                    int value =(int)(Math.random()*100);
                    if(value % 2 == 0){
                        berjalanx = "Kekanan";
                    }else{
                        berjalanx = "Kekiri";
                    }
                }
            }
            
//        System.out.println(x);
//        System.out.println(y);
        x = Game.clamp(x, 0, Game.WIDTH - 60);
        y = Game.clamp(y, 0, Game.HEIGHT - 80);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.decode("#00ffff"));
        g.fillRect(x, y, 50, 50);
    }
    
}
