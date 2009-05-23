import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class bounceobject extends PApplet {

/**
 * Commundo Minigame prototype
 * 
 * Breakout meets Invaders meets a Shmup
 */
 
Player p1;
Weapon w1;
Spawn s1;
Enemy[][] enem;
ParticleSystem ps;
Random generator;

int num, rn, counter, charge;
float size = 40.0f;
int bg;
PImage playerImage, enemyImage, bgImage;
PFont fontA;
public void setup() 
{
  
  size(700,700);
  noStroke();
  frameRate(30);
    colorMode(RGB, 255, 255, 255, 100);
  fontA = loadFont("BrandNew-48.vlw");
  playerImage = loadImage("player.png");
  enemyImage = loadImage("enemy.png");
  bgImage = loadImage("background.png");
  //bx = width/2;
  //by = 0;
  //bvy = 10;
  //bvx = 0;
  num = 11;
  rn=4;
  bg = 102;
  charge = 5;
  p1 = new Player(450,100);
  w1 = new Weapon(width/2, 200);
  s1 = new Spawn(true);
  generator = new Random();
  enem = new Enemy[num][rn];
  ps = new ParticleSystem(1, new PVector(width/2,height/2,0));
  textFont(fontA, 32);
  textAlign(CENTER);
  for (int j = 0; j < rn; j ++) {
  for (int i = 0; i < 10; i++) {      
    enem[i][j] = new Enemy(120 + i*50+PApplet.parseInt(random(10)),j*40 + PApplet.parseInt(random(20)));
    s1.fc[i][j] = true;
    }
  }
}

public void draw() 
{

  fill(50);
     if(keyPressed) {
    if (key == 'r' || key == 'Q') {
        enem = new Enemy[num][rn];
  for (int j = 0; j < rn; j ++) {
  for (int i = 0; i < 10; i++) {      
    enem[i][j] = new Enemy(120 + i*50,j*20 + PApplet.parseInt(random(50)));
    s1.fc[i][j] = true;
    }
  }
      s1.spawn();
    }
   }
  background(bgImage);  
  p1.display();
  w1.create();
  s1.spawn();
  for (int j = 0; j < rn; j++){
  for (int c = 0; c < 10; c++){ 
    noFill();
    stroke(0);
    //ellipse(enem[c][j].ex, enem[c][j].ey, 50, 50);
    if((w1.bx >= enem[c][j].ex-20)&&(w1.bx <= enem[c][j].ex+20)&&(w1.by >= enem[c][j].ey-20)&&(w1.by <= enem[c][j].ey+30)){
      s1.fc[c][j] = false;
      for( int ev = 0; ev < 10; ev++){
      ps.addParticle(w1.bx+PApplet.parseInt(random(4))-2,w1.by+PApplet.parseInt(random(4))-2,color(255,0,0) );}
      w1.bvy = -(w1.bvy);
      w1.by = w1.by+10;
      w1.bvx = (w1.bx-enem[c][j].ex)/2;
      enem[c][j].ey = 1000;
      counter++;
    }
    }
  }
  if((w1.by >= p1.ay-10) && (w1.by <= p1.ay +10) && (w1.bx >= p1.ax) && (w1.bx <= p1.ax+p1.alen))
  {
    w1.bvy = -((10/w1.bvy)+w1.bvy);
    w1.by = w1.by-10;
    w1.bvx = (w1.bx-p1.cx)/2;
  }else if(w1.by > p1.ay +250){ 
  w1.reset();
  charge--;
  if(charge == 0){
    text("Game Over", (width/2), (height/2));
    delay(10);
    exit();
  }
  }
  
  if((w1.bx+10>=width)||(w1.bx-10<=0)){
    w1.bvx = -w1.bvx;
  } 
 

}
class Enemy
{
  //int ex, ey;
  float eyv, exv, ran, ex, ey;
  boolean ai;
//  str breed;
  
  Enemy(int iex, int iey){
    ex = iex;
    ey = iey;
 //   breed = ibreed;
  }
  public void spawn(boolean ia){
    ran = random(10);
    ai = ia;
    eyv = 0.1f;
    ex = ex + exv;
    ey = ey + eyv;
    if (ia == true){
    //rect(ex-enemyImage.width/2, ey, enemyImage.width, 10);    
    if(ran > 9){
    exv = random(4) - 2;
    }else if(ran<3){
      exv = 0;}
    } 
    image(enemyImage, ex-enemyImage.width/2, ey-enemyImage.height/2);
}
}
class Spawn{
  boolean fr;
  int b = 0;
  boolean[][] fc = new boolean[num][rn];
  Spawn(boolean rf){
    fr = rf;
  }
  public void spawn(){
      fill(0);
    text("Score: "+str(counter), width/4, (height/10)*9);
    for (int j = 0; j < rn; j++){    
    for (int c = 0; c < 10; c++) {
      enem[c][j].spawn(fc[c][j]);
    }
    }
  }
}
class Player
{ 
  int ax, ay, alen, cx;
  
  Player(int iay, int ialen){
    ay = iay;
    alen = ialen;
  }
  
  public void display(){
    ax = cx - (alen/2);
    cx = mouseX;
    if(cx < width/10){
      cx=width/10+10;
    }
    if(cx > (width/10)*9){
      cx=((width/10)*9)-10;
    }
    fill(255);
    //rect(ax, ay, 10 + alen, 20);
    image(playerImage, ax, ay);
    ps.addParticle(ax+playerImage.width/2,ay+playerImage.height,color(255,255,0));
    text("q for Magnet, r to Reset",width/2,(height/20)*19);
    fill (0);
    text("Charge: "+str(charge), (width/4)*3, (height/10)*9);
  }
}

class Weapon
{
  float bx, by, bvx, bvy;
  Weapon(float bxi, float byi){
    bx = bxi;
    by = byi;
  }
  public void create(){
  bvy = bvy + 0.4f;
  bx = bx + bvx;
  by = by + bvy;
   if(keyPressed) {
    if (key == 'q' || key == 'Q') {
      this.magnet();
    }
   }
  fill(0);
  ellipse(bx, by, 15, 15);
    ps.run();
  ps.addParticle(bx,by,255);
  
  }
  public void reset(){
   //bx = width/2;
   delay(5);
   //by = 10;
   bvy = -20;
   //bvx = 0;
 }
 public void magnet(){
   bvx = (bvx+((bx-(p1.ax))*(-1))/250);
   bvy = bvy + 0.1f;
 }
}

 
// A simple Particle class

class Particle {
  PVector loc;
  PVector vel;
  PVector acc;
  float r;
  float timer;
  int col;
  
  // Another constructor (the one we are using here)
  Particle(PVector l, int colo) {
    acc = new PVector(0,0.05f,0);
    vel = new PVector(random(-1,1),random(-2,0),0);
    loc = l.get();
    col = colo;
    r = 10.0f;
    timer = 100.0f;
  }

  public void run() {
    update();
    render();
  }

  // Method to update location
  public void update() {
    vel.add(acc);
    loc.add(vel);
    timer -= 2.0f;
  }

  // Method to display
  public void render() {
    ellipseMode(CENTER);
    noStroke();//(255,timer);
    fill(col,timer);
    ellipse(loc.x,loc.y,r,r);
    displayVector(vel,loc.x,loc.y,10);
  }
  
  // Is the particle still useful?
  public boolean dead() {
    if (timer <= 0.0f) {
      return true;
    } else {
      return false;
    }
  }
  
   public void displayVector(PVector v, float x, float y, float scayl) {
    pushMatrix();
    float arrowsize = 4;
    // Translate to location to render vector
    translate(x,y);
    //stroke(255);
    // Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
    rotate(v.heading2D());
    // Calculate length of vector & scale it to be bigger or smaller if necessary
    float len = v.mag()*scayl;
    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
    //line(0,0,len,0);
    //line(len,0,len-arrowsize,+arrowsize/2);
    //line(len,0,len-arrowsize,-arrowsize/2);
    popMatrix();
  } 

}

// A class to describe a group of Particles
// An ArrayList is used to manage the list of Particles 

class ParticleSystem {

  ArrayList particles;    // An arraylist for all the particles
  PVector origin;        // An origin point for where particles are born

  ParticleSystem(int num, PVector v) {
    particles = new ArrayList();              // Initialize the arraylist
    origin = v.get();                        // Store the origin point
    for (int i = 0; i < num; i++) {
      particles.add(new Particle(origin,1));    // Add "num" amount of particles to the arraylist
    }
  }

  public void run() {
    // Cycle through the ArrayList backwards b/c we are deleting
    for (int i = particles.size()-1; i >= 0; i--) {
      Particle p = (Particle) particles.get(i);
      p.run();
      if (p.dead()) {
        particles.remove(i);
      }
    }
  }

  public void addParticle() {
    particles.add(new Particle(origin,1));
  }
  
    public void addParticle(float x, float y, int col) {
    particles.add(new Particle(new PVector(x,y),col));
    fill(col);
  }

  public void addParticle(Particle p) {
    particles.add(p);
  }

  // A method to test if the particle system still has particles
  public boolean dead() {
    if (particles.isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
}




  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#DFDFDF", "bounceobject" });
  }
}
