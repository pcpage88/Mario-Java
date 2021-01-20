/* Name: Patrick Page
 * Date: September 17, 2020
 * Description: To make a graphical user interface with mouse and keyboard input similar to a game.
 * */

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.System;

class Controller implements ActionListener, MouseListener, KeyListener
{   
	//Define member variables
	Controller controller;
	View view;
	Model model;
	
	boolean keyUp;
	boolean keyRight;
	boolean keyLeft;
	boolean keyDown;
	boolean spaceBar;
	boolean control;
	boolean addTubesEditor = false;
	boolean addGoombasEditor = false;
	
	
	//sets the view so that the controller is aware what the view is.
	void setView(View v)
	{
		view = v;
	}
	
	//Controller Constructor Method
	Controller(Model m)
	{	
		model = m;
	}

	public void actionPerformed(ActionEvent e)
	{
	}
	
	//public method to set where tubes will be places using X and Y coordinates at which the mouse clicked.
	public void mousePressed(MouseEvent e)
	{		
		if(addTubesEditor == true)
		{
			//sends x and y coordinates for Tubes to the model class
			model.addTube(e.getX()+model.mario.x-model.mario.marioOffset, e.getY());	
		}
		if(addGoombasEditor == true)
		{
			//sends x and y coordinates for goombas to model class
			model.addGoomba(e.getX()+model.mario.x-model.mario.marioOffset, e.getY());
		}
	}

	//Empty methods?
	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) 
	{ 
		if(e.getY() < 100)
		{
			System.out.println("break here");
		}
	}	
	
	//required method for KeyListener to tell when a key is being pressed and held down.
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break; 
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_SPACE:
				spaceBar = true;
				break;
			case KeyEvent.VK_CONTROL:
				control = true;
				break;
		}
	}
	
	//method to recognize when keys are released, and tell what to do when certain keys are pressed
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break; 
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_SPACE:
				spaceBar = false;
				break;
			case KeyEvent.VK_CONTROL:
				control = false;
				if(model.mario.flip == false)
					model.addFireball(model.mario.x+model.mario.w, model.mario.y);  //Shoot fireballs only when ctrl key is released.
				else
					model.addFireball(model.mario.x, model.mario.y); //Shoot fireballs to the left when mario is facing left and ctrl is released. 
				break;
		}
		
		char c = e.getKeyChar();
		if(c == 's')
		{
			model.marshal().save("map.json");
			System.out.println("You have saved map.json");
		}
		if(c == 'l')
		{
			Json j = Json.load("map.json");
			model.unmarshal(j);
			System.out.println("Map Loaded!");
		}
		if(c == 't')
		{
			addGoombasEditor = false;
			addTubesEditor = !addTubesEditor; // swap true and false
			if(addTubesEditor)
				System.out.println("Tube Editor On!");
			else
				System.out.println("Tube Editor Off!");
		}
		if(c == 'g')
		{
			addTubesEditor = false;
			addGoombasEditor = !addGoombasEditor; // swap true and false
			if(addGoombasEditor)
				System.out.println("Goomba Editor On!");
			else
				System.out.println("Goomba Editor Off!");
		}
	}
	
	public void keyTyped(KeyEvent k)
	{
		
	}

	//method to move tubes to left or right (or to move the view) when left/right keys are pressed.
	void update()
	{
		model.mario.savePreviousCoordinates();
		
		if(keyRight)
		{
			model.mario.updateImage();
			model.mario.x += 10;
			model.mario.flip = false;
		}
		if(keyLeft) 
		{
			model.mario.updateImage();
			model.mario.x -= 10;
			model.mario.flip = true;
		}
		if(!keyRight && !keyLeft)
			model.mario.imageNum = 3;
		if(keyUp || spaceBar)
		{
			if(model.mario.numFramesInAir < 8)
				model.mario.jump();
		}

	}
}