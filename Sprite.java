import java.awt.Graphics;

public abstract class Sprite 
{
	int x, y;
	int w, h;
	String type;
	
//	BufferedImage image;
	
	abstract boolean update();
	abstract void drawYourself(Graphics g);
	
	boolean isTube() { return false; }
	boolean isMario() { return false; }
	boolean isGoomba() { return false; }
	boolean isFireball() { return false; }
	boolean isBrick() { return false; }

    //Check if mouse clicked where a tube is already located.
	public boolean SpriteClicked(int mouse_x, int mouse_y)
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
	
}
