/* Name: Patrick Page
 * Date: September 17, 2020
 * Description: To make a graphical user interface with mouse and keyboard input similar to a game.
 * */
import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	ArrayList<Sprite> sprites;
	SpriteComparator c;
	Mario mario;
	View v;
	
	//Model constructor
	Model()
	{
		sprites = new ArrayList<Sprite>();
		c = new SpriteComparator();
		mario = new Mario(100,350);
		sprites.add(mario);
	}

	public void update()
	{	
		Iterator<Sprite> spriteIterator = sprites.iterator();

		//Iterate through the sprites arrayList to test for collisions and remove sprites when need be.
		while(spriteIterator.hasNext())
		{
			Sprite s = spriteIterator.next();
			s.update();
			
			//Check for mario and goombas colliding with tubes!
			if(s.isTube())
			{
				Tube t = (Tube)s;
				if(collide(mario,t)==true)
					mario.getOutOfSprite(t);
				
				for(int i=0;i < sprites.size();i++)
				{
					Sprite s1 = sprites.get(i);
					if(s1.isMario())
					{
						Mario m = (Mario)s1;
						if(collide(s1,t))
							m.getOutOfSprite(t);
					}
					if(s1.isGoomba())
					{
						Goomba g = (Goomba)s1;
						if(collide(g,t))
							g.getOutOfSprite(t);
					}
				}
			}
			//Check for if Goomba's are caught on fire or should be removed from sprites ArrayList
			if(s.isGoomba())
			{
				Goomba g = (Goomba)s;
				//Check if Goomba has been on fire for long and if so remove him/it
				if(g.numFramesOnFire >= 200)
				{
					spriteIterator.remove();
					g.numFramesOnFire = 0;
				}
			}
			//Check for when fireballs should be removes from ArrayList
			if(s.isFireball())
			{
				Fireball f = (Fireball)s;
				
				//loop through sprites arrayList to check for Goomba
				for(int i=0;i<sprites.size();i++)
				{
					Sprite s1 = sprites.get(i);
					if(s1.isGoomba())
					{	
						Goomba g = (Goomba)s1;
						//If Goomba collides with fireball, tell Goomba class and start adding numFramesOnFire
						if(!g.isOnFire)
							if(collide(s,s1))
							{
								g.isOnFire = true;
								g.numFramesOnFire += 5;
								spriteIterator.remove();
								break;
							}
					}
				}
				//Tell the Model to remove fireballs using iterator after a certain distance they've travelled.
				if(f.x > f.orig_x +650)
					spriteIterator.remove();
				
			}
		}
	}
	
	//Method to unmarshal (or load) each tube in the Model that was saved.
	void unmarshal(Json ob)
	{
		//Create new sprites ArrayList to unmarshal into.
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);  //add mario back into sprites
		
		//Get Sprites, tubes, and goombas from map.json or whatever file you tell to unmarshal.
		Json jsonList = ob.get("sprites");
		Json tubesList = jsonList.get("tubes");
		Json goombasList = jsonList.get("goombas");
		
		//add tubes back into sprites
		for(int i=0; i<tubesList.size();i++)
		{
			sprites.add(new Tube(tubesList.get(i),this));
		}
		//add goombas back into sprites
		for(int i=0; i<goombasList.size();i++)
		{
			sprites.add(new Goomba(goombasList.get(i),this));
		}
	}
	
	//Method to marshal (or save) each tube and goomba in the model in order of arrayList index.
	Json marshal()
	{
		Json ob = Json.newObject();
		Json spritesOb = Json.newObject();
		Json tempList = Json.newList();
		ob.add("sprites", spritesOb);
		spritesOb.add("tubes", tempList);
		for(int i = 0; i<sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			if(s.isTube())
			{
				Tube t = (Tube)s;
				tempList.add(t.marshal());
			}
		}
		tempList = Json.newList();
		spritesOb.add("goombas", tempList);
		for(int i = 0; i<sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			if(s.isGoomba())
			{
				Goomba g = (Goomba)s;
				tempList.add(g.marshal());
			}
		}
		return ob;
	}

	//method to check if tube is already located at x,y coords. and then add a tube when no tube is already there.
	public void addTube(int x, int y)
	{
		//iterator to iterate through tubes ArrayList
		Iterator<Sprite> spriteIterator = sprites.iterator();
		Sprite s = null;
		
		//loop through all of the tubes in ArrayList
		while(spriteIterator.hasNext())
		{
			Sprite s1 = spriteIterator.next();
			if(s1.isTube())
			{
				//If a tube is clicked while trying to add another tube remove the tube clicked on.
				if(s1.SpriteClicked(x,y)==true)
				{
					if(s1.isTube())
					{
						s = s1;
						spriteIterator.remove();
					}
				}
			}
		}
		if(s == null)
		{	//create new tube
			s = new Tube(x,y,this);
			sprites.add(s);
//		 	sprites.sort(c);
		}	 
	}
	
	//method to check if tube is already located at x,y coords. and then add a tube when no tube is already there.
	public void addGoomba(int x, int y)
	{
		//iterator to iterate through sprites ArrayList
		Iterator<Sprite> spriteIterator = sprites.iterator();
		Sprite s = null;
		
		//loop through all of the sprites in ArrayList
		while(spriteIterator.hasNext())
		{
			Sprite s1 = spriteIterator.next();
			if(s1.SpriteClicked(x,y)==true)
			{
				//if goomba check for another goomba in same spot.
				if(s1.isGoomba())
				{
					s = s1;
					spriteIterator.remove();
				}
			}
		}
		if(s == null)
		{	//create new tube
		 	s = new Goomba(x,y,this);
		 	sprites.add(s);
		 	Goomba g = (Goomba)s;
		 	g.original_x = x;
//		 	sprites.sort(c);
		}	 
	}
	
	public void addFireball(int x, int y)
	{
		//Add fireball to sprites ArrayList
		Sprite f = new Fireball(x,y,this);
		sprites.add(f);
	}
	
	//Check for sprite-on-sprite collision, if collision return true;
	boolean collide(Sprite a,Sprite b)
	{

		if((a.x+a.w) < b.x)
			return false;
		if(a.x > (b.x+b.w))
			return false;
		if(a.y > (b.y+b.h))
			return false;
		if((a.y+a.h) < b.y)
			return false;
		else
			return true;
	}
}