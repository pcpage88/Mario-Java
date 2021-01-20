import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Goomba extends Sprite 
{
	static BufferedImage goomba_image,goomba_fire;
	Model model;
	int direction,speed;
	int prev_x;
	boolean isOnFire,dead;
	int numFramesOnFire;
	int original_x;
	
	//Goomba constructor
	public Goomba(int x,int y, Model m)
	{
		this.x = x;
		this.y = y;
		this.model = m;
		this.type = "goomba";
		w = 45;
		h = 55;
		direction = 1;
		speed = 10;
		isOnFire = false;
		dead = false;
		numFramesOnFire = 0;
		loadGoombaImage();
	}
	
	boolean update()
	{	
		
		//Set direction=1 when it's moving to the right
		//Set direction=-1 when it's moving to the left
		prev_x = x;
		
		if(!isOnFire)
			x += (speed*direction);
		
		if(y < 385)
		{
			y+=10;
		}
		if(y > 385)
		{
			y = 385;
		}
		
		if(isOnFire)
			numFramesOnFire += 5;
		
		if(numFramesOnFire >= 200)
			return false;
		else
			return true;

	}
	
	void getOutOfSprite(Sprite s)
	{
		//in the tube, but we were previously on the left side of tube
		if((x+w) >= s.x && (prev_x+w) <= s.x)
		{
			x = (s.x-w);
			direction *= (-1);  //Change direction when collision (and which way Goomba is Facing)
		}
		//in the tube, but were previously on the right side		
		if(x <= (s.x+s.w) && prev_x >= (s.x+s.w))
		{
			x = (s.x+s.w);
			direction *= (-1);  //Change direction when collision (and which way Goomba is Facing)
		}
	}
	
	// Unmarshaling constructor
  Goomba(Json ob, Model m)
  {
		this.model = m;
		this.type = "goomba";
	    this.x = (int)ob.getLong("x");
	    this.y = (int)ob.getLong("y");
		w = 45;
		h = 55;
		direction = 1;
		speed = 10;
		isOnFire = false;
		numFramesOnFire = 0;
		loadGoombaImage();
  }
	
	// Marshals this object into a JSON DOM
  Json marshal()
  {
      Json ob = Json.newObject();
      ob.add("x",this.x);
      ob.add("y",this.y);
      return ob;
  }	
	
  //load tube image
  void loadGoombaImage()
  {	
	  if(goomba_image == null)
			goomba_image = View.loadImage("goomba.png");
	  if(goomba_fire == null)
		  	goomba_fire = View.loadImage("goomba_fire.png");
  }
  

	
    //Check if mouse clicked where a goomba is already located.
	public boolean GoombaClicked(int mouse_x, int mouse_y)
	{
		//If Goomba clicks another goomba return true;
		if(mouse_x < x) 
		{
			return false;
		}
		if(mouse_x > (x + w))
		{
			return false;
		}
		if(mouse_y < y)
		{
			return false;
		}
		if(mouse_y > (y + h))
		{
			return false;
		}
		else
		{
		return true;
		}
	}
	
	void drawYourself(Graphics g)
	{
		//if Goomba is on fire draw goomba_fire image.
		if(isOnFire)
			g.drawImage(goomba_fire, x - model.mario.x + model.mario.marioOffset, y, null);
		else//if Goomba is not on fire draw goomba whichever way corresponds to current value for flip variable.
			if(direction == -1)
				g.drawImage(goomba_image, x - model.mario.x + model.mario.marioOffset+w, y,-w,h, null);
			else if(direction == 1)
				g.drawImage(goomba_image, x - model.mario.x + model.mario.marioOffset, y,w,h, null);

	}
	
	@Override  //Optional
	boolean isGoomba()
	{
		return true;
	}
}