/**
 * Translate. 
 * 
 * The translate() function allows objects to be moved
 * to any location within the window. The first parameter
 * sets the x-axis offset and the second parameter sets the
 * y-axis offset. 
 */
 
float alen, ax, ex, ey, bx, by, bvx, bvy;
float size = 40.0;
int bg;

void setup() 
{
  
  size(500,500);
  noStroke();
  frameRate(30);
  bx = width/2;
  by = 0;
  bvy = 10;
  bvx = 0;
  bg = 102;
}

void draw() 
{
  background(bg);  
  bvy = bvy + 0.4;
  alen = 100;
  ax = mouseX - (alen/2);
  bx = bx + bvx;
  by = by + bvy;
  //if (x > width + size) {
    //x = -size;
  //} 
  
  //translate(x, height/2-size/2);
  fill(255);
  rect(ax, 400, 10 + alen, 20);
  
  if((by > 390) && (bx >= ax) && (bx <= ax+alen))
  {
    bvy = -bvy;
    by = by-10;
    bvx = (bx-mouseX)/2;
  }
  if((bx+10>=width)||(bx-10<=0)){
    bvx = -bvx;
  } else if(by > 400){
 //   exit;
   bg=990000;
  }
  
  fill(0);
  ellipse(bx, by, 10, 10);
}
