import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Tube extends Sprite 
{
	static BufferedImage tube_image;
	Model model;

	//Tube constructor
	Tube(int x,int y,Model m)
	{
		model = m;
		this.x = x;
		this.y = y;
		this.type = "tube";
		w = 55;
		h = 400;
		loadTubeImage();
	}
	
	boolean update()
	{	
		return true;
	}
	
  // Unmarshaling constructor
  Tube(Json ob, Model m)
  {
	  model = m;
      this.x = (int)ob.getLong("x");
      this.y = (int)ob.getLong("y");
      this.type = "tube";
      w = 55;
      h = 400;
      loadTubeImage(); 
  }
  
  // Marshals this object into a JSON DOM
  Json marshal()
  {
      Json ob = Json.newObject();
      ob.add("x",x);
      ob.add("y",y);
      return ob;
  }
	
  //load tube image
  void loadTubeImage()
  {	
	  if(tube_image == null)
			tube_image = View.loadImage("Tube.png");
  }
  
    //Check if mouse clicked where a tube is already located.
	public boolean TubeClicked(int mouse_x, int mouse_y)
	{
		
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
		g.drawImage(tube_image, x - model.mario.x + model.mario.marioOffset, y, null);	
	}
	
	@Override  //Optional
	boolean isTube()
	{
		return true;
	}
}