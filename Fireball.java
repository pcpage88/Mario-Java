import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

class Fireball extends Sprite 
{
	static BufferedImage fireball_image;
	Model model;
	double vert_velocity;
	int direction;
	int orig_x;
	
	//Fireball constructor
	Fireball(int x,int y,Model m)
	{
		model = m;
		this.x = x;
		this.y = y;
		this.type = "fireball";
		w = 47;
		h = 47;
		loadFireballImage();
		vert_velocity = 10;
		orig_x = model.mario.x;  //variable to tell where the original x-coord of fireball is
		if(model.mario.flip == false)
			direction = 1;
		if(model.mario.flip == true)
			direction = -1;
	}
	
	boolean update()
	{	
		//fireballs constantly moving to the right
		x+=(10*direction);
			
		vert_velocity += 2.5;
		y+=vert_velocity;
		
		//slow velocity down when fireball begins approaching ground level (mario-level)
		if(y > 365)
		{
			vert_velocity = -20;
		}
		if(y < 0)
			y=0;

		return true;
	}
	
  //load tube image
  void loadFireballImage()
  {	
	  if(fireball_image == null)
			fireball_image = View.loadImage("Fireball.png");
  }
	
	void drawYourself(Graphics g)
	{
		//
		if(direction == 1)
			g.drawImage(fireball_image,this.x - model.mario.x + model.mario.marioOffset, y, -w, h, null);
		if(direction == -1)
			g.drawImage(fireball_image, this.x - model.mario.x + model.mario.marioOffset,y,w,h, null);
	}
	
	@Override  //Optional
	boolean isFireball()
	{
		return true;
	}
}