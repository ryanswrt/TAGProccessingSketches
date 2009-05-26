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
Newscreen ns;

int num, rn, counter, charge;
float size = 40.0;
int bg;
PImage playerImage, enemyImage, bgImage;
PFont fontA;
int escaped;

void setup() 
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
  ns = new Newscreen();
  generator = new Random();
  enem = new Enemy[num][rn];
  ps = new ParticleSystem(1, new PVector(width/2,height/2,0));
  textFont(fontA, 32);
  textAlign(CENTER);
  for (int j = 0; j < rn; j ++) {
  for (int i = 0; i < 10; i++) {      
    enem[i][j] = new Enemy(120 + i*50+int(random(10)),j*40 + int(random(20)));
    s1.fc[i][j] = true;
    }
  }
}

void draw() 
{

  fill(50);
     if(keyPressed) {
    if (key == 'r' || key == 'Q') {
      ns.draw();
        enem = new Enemy[num][rn];
  for (int j = 0; j < rn; j ++) {
  
  for (int i = 0; i < 10; i++) {      
    enem[i][j] = new Enemy(120 + i*50,j*20 + int(random(50)));
    s1.fc[i][j] = true;
    }
  }
      s1.spawn();
    }
   }
  background(bgImage);  
  p1.display();
  w1.create();
  if(random(100)>95){
}
  s1.spawn();
  for (int j = 0; j < rn; j++){
  for (int c = 0; c < 10; c++){ 
    noFill();
    stroke(0);
    if((c<9)&&(j<3)){
    if((enem[c][j].ex == enem[c+1][j+1].ex+5)||(enem[c][j].ex == enem[c+1][j+1].ex-5)){
      enem[c][j].ey = enem[c][j].ey + 10;}}
    if(enem[c][j].ey == 450){
      escaped ++;
      if(escaped> 10){
           text("Game Over", (width/2), (height/2));
    delay(10);
    //exit();
  }
    }
        
    //ellipse(enem[c][j].ex, enem[c][j].ey, 50, 50);
    if((w1.bx >= enem[c][j].ex-20)&&(w1.bx <= enem[c][j].ex+20)&&(w1.by >= enem[c][j].ey-20)&&(w1.by <= enem[c][j].ey+30)){
      s1.fc[c][j] = false;
      for( int ev = 0; ev < 10; ev++){
      ps.addParticle(w1.bx+int(random(4))-2,w1.by+int(random(4))-2,color(255,0,0) );}
      w1.bvy = -(w1.bvy);
      w1.by = w1.by+10;
      w1.bvx = (w1.bx-enem[c][j].ex)/2;
      enem[c][j].ey = 0;
      enem[c][j].ex = width+50;
      counter++;
    }
    }
  }
  if((w1.by >= p1.ay-10) && (w1.by <= p1.ay +10) && (w1.bx >= p1.ax) && (w1.bx <= p1.ax+p1.alen))
  {
    w1.bvy = -((10/w1.bvy)+w1.bvy);
    w1.by = w1.by-10;
    w1.bvx = (w1.bx-p1.cx)/2;
    for( int ev = 0; ev < 10; ev++){
      ps.addParticle(w1.bx+int(random(4))-2,w1.by+int(random(4))-2,color(0,0,250,50) );}
 
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
  int a;
//  str breed;
  
  Enemy(int iex, int iey){
    ex = iex;
    ey = iey;
 //   breed = ibreed;
  }
  void spawn(boolean ia){
    if (ia == true){
    ran = random(10);
    ai = ia;
    if(a == 0){
      if(random(100)>75){
    eyv = 0.4; 
  a++; } else { eyv = 0.1;
a++;}}
    ex = ex + exv;
    ey = ey + eyv;
    
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
  void spawn(){
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
  
  void display(){
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
  void create(){
  bvy = bvy + 0.4;
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
  void reset(){
   //bx = width/2;
   delay(5);
   //by = 10;
   bvy = -20;
   //bvx = 0;
 }
 void magnet(){
   bvx = (bvx+((bx-(p1.ax))*(-1))/250);
   bvy = bvy + 0.1;
 }
}

 
