/* Name: Patrick Page
 * Date: September 17, 2020
 * Description: To make a graphical user interface with mouse and keyboard input similar to a game.
 * */

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
//import java.io.IOException;
import java.io.File;
//import javax.swing.JButton;
import java.awt.Color;
import java.lang.System;

class View extends JPanel
{
	static BufferedImage tube_image;  //create a Buffered image member variable for tube image
	static BufferedImage[] mario_images; //buffered image object

	Model model;				//create a model member variable
	
	
	//View Constructor
	View(Controller c,Model m)
	{	
		//Initialize all member variables
		model = m;
		
		//set the controller to view this window
		c.setView(this);  
	}
	
	//Loads the array of mario images into the game.
	static BufferedImage loadImage(String fileName)
	{
		BufferedImage image = null;
		
		try
		{
			image = ImageIO.read(new File(fileName));
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.out.println("Error when loading " + fileName + " attempted to load!");
			System.exit(1);		}
		return image;
	}
	
	
	//sets the model object parameter equal to the member variable model
	void setModel(Model m)
	{
		model = m;
	}
	
	
	//public function to change the background color of the window and to paint the turtle image onto the game screen.
	public void paintComponent(Graphics g)
	{
		
		g.setColor(new Color(128, 255, 255));//set the background color of the game window
		g.fillRect(0, 0, this.getWidth(), this.getHeight());//fill the entire game window with this color?
		
		g.setColor(Color.green);
		g.drawLine(0, 400, 4000, 400);
		g.fillRect(0, 400, 4000, 200);
		
		for(int i=0; i<model.sprites.size();i++)
		{
			model.sprites.get(i).drawYourself(g);
		}
	}
}