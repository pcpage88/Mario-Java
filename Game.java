/* Name: Patrick Page
 * Date: September 17, 2020
 * Description: To make a graphical user interface with mouse and keyboard input similar to a game.
 * */

import javax.swing.JFrame;
import java.awt.Toolkit;
import static java.lang.System.out;
import java.lang.System;

public class Game extends JFrame
{
	//Create member variables of type Model, Controller, and View
	Model model;
	Controller controller;
	View view;
	
	//Game Constructor Method
	public Game()
	{	
		//Initialize member variables to new Objects.
		model = new Model();
		controller = new Controller(model); //pass the model variable by reference into the Controller class
		view = new View(controller,model);  //pass the model and controller variables by reference into the View constructor.
		
		//try/catch block to load map
		
		//Set the title of the Game Window to "Turtle attack!" and set the size of window, set the program to close on exiting the screen, set window to visible.
		this.setTitle("Mario Game Test 1");
		this.setSize(800, 500);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//add Mouse Listener to view object but with controller member viariable as a reference parameter.
		view.addMouseListener(controller);
		
//		add a key stroke listener to the view object w/ controller member variable as a reference parameter.
		this.addKeyListener(controller);
		
		try {
			Json j = Json.load("map.json");
			model.unmarshal(j);
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("There was an error unmarshalling!");
			System.exit(0);
		}
		
	}
	
	void setModel(Model m)
	{
		model = m;
	}

	public static void main(String[] args)
	{
		
		//Create new Game Object and Run it;
		out.println("Start Mario Game!");
		Game g = new Game();
		g.run();
		
	}
	
	//public method to run game simulation
	public void run()
	{
		while(true)
		{
			//Update controller(s) such as mouse movement and key strokes and update model and view accordingly.
			controller.update();
			model.update();
			view.repaint(); 					// Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 milliseconds
			try
			{
				Thread.sleep(40);
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}