import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Mario extends Sprite
{
	int prev_x,prev_y;
	int imageNum;
	double vert_velocity;
	int numFramesInAir;
	static BufferedImage[] mario_images;
	boolean flip;
	int marioOffset;
	
	Mario(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.type = "mario";
		w = 60;
		h = 95;
		imageNum = 0;
		vert_velocity = 0;
		numFramesInAir = 0;
		flip = false;
		marioOffset = 100;

	
		//Lazy loading of mario_images array
		mario_images = new BufferedImage[5];
		for(int i = 0; i < mario_images.length;i++) 
		{
			if(mario_images[i]==null)
			{
				mario_images[i] = View.loadImage("mario" + (i+1) + ".png");
			}
		}
	}
	
	void savePreviousCoordinates()
	{
		//sets previous coordinates
		prev_x = x;
		prev_y = y;
	}
	
	void getOutOfSprite(Sprite s)
	{
		
		//in the tube, but we were previously on the left side of tube
		if(x+w >= s.x && this.prev_x+w <= s.x)
			x = s.x - w-1;
		
		//in the tube, but were previously on the right side		
		if(x <= s.x+s.w && this.prev_x >= s.x+s.w)
			x = s.x + s.w+1;
		
		//in the tube, but used to be above the tube
		if(y+h >= s.y && this.prev_y+h <= s.y)
		{
			vert_velocity = 0;
			numFramesInAir = 0;
			y = s.y - h;
		}
		//in the tube, but used to be below the tube
		if(y < s.y+s.h && this.prev_y > s.y+s.h)
			y = s.y + s.h;
	}
	
	boolean update()
	{		
		
		//constantly increase vertical velocity so mario falls. 
		vert_velocity += 3.0;
		y += vert_velocity;
		
		//increment nunmber of frames in air counter
		if(y < 335 && y >= 0)
			numFramesInAir ++;
		
		//if mario goes below ground snap him back to ground level.
		if(y > 335) 
		{
			vert_velocity = 0.0;	//reduce velocity to 0 so stops falling
			y=335;  				// snap back to the ground
			numFramesInAir = 0;		//sets frames in air counter to 0
		}
		
		//keep mario from going past top of screen
		if(y < 1)
		{
			y=0;
			if(y==0)
			{
				numFramesInAir = 8;
				vert_velocity += 3.7;
			}
		}
		
		return true;
	}
	
	//when mario jumps
	void jump()
	{
		//if mario has been in air for less than 10 frames, keep going up.
		if(numFramesInAir < 8)
			vert_velocity -= 7.0;
	}
	
	void updateImage()
	{
		//constantly update images so mario animation works
		imageNum++;
		if(imageNum > 4)
			imageNum = 0;
	}
	
	void drawYourself(Graphics g)
	{
		if(flip) //flip the image
			g.drawImage(mario_images[imageNum],marioOffset+w,y,-w,h,null);
		//Draw Mario (images loaded in Mario Class)
		else
			g.drawImage(mario_images[imageNum],marioOffset,y,w,h,null);
	}
	
	@Override
	boolean isMario()
	{
		return true;
	}
	
}

