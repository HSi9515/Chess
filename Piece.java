// Class: Piece
// Written by: Mr. Swope
// Date: 10/28/15
// Description: This class implements a Piece.  This Piece will be drawn onto a graphics panel. 
// 
// If you modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Component;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;

public class Piece {
	private ImageIcon image;			// The ImageIcon will be used to hold the Character's png.
										// This png must be saved in the images folder and will be loaded 
										// in the constructor.
	
	private int player;					// This int will represent which team the piece is, 1 for white team, 
	private boolean firstTurn;								    // 2 for black team. 
	
	// method: Default constructor - see packed constructors comments for a description of parameters.
	public Piece(){
		this(1);
	}
		
	// method: Character's packed constructor
	// description: Initialize a new Character object.
	// parameters: int player - should be either 1 or 2. 1 for white team, 2 for black team.
	public Piece(int player){
		this.setPlayer(player);			
		setImageIcon("pawn");
		firstTurn = true;
		
	}
	
	// method: Character's packed constructor
	// description: Initialize a new Character object.
	// parameters: int player - should be either 1 or 2. 1 for white team, 2 for black team.
	public Piece(int player, String imagePath){
		this.setPlayer(player);
		setImageIcon(imagePath);
	}
	
	
	protected void setImageIcon(String imagePath){
		ClassLoader cldr = this.getClass().getClassLoader();	
		URL imageURL;
		
		if (player == 1 || player == 2)
			imageURL = cldr.getResource("images2/" + imagePath + player + ".png");	
		else
			imageURL = cldr.getResource("images2/space.png");	

        image = new ImageIcon(imageURL);
	}
	
	
	//
	//
	public boolean isFirstTurn(){
		return firstTurn;
	}
	
	
	// method: isValidMove
	// description: This method checks to see if a move is valid.
	// Returns whether or not the attempted move is valid.
	// @param - Location from - the location that the piece will be moved from
	// @param - Location to - the location that the piece will be moved to
	// @param - Piece[][]b - the chess board.  a two dimensional array of pieces.
	// return - boolean - true if the move is valid 
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		return false;
	}
	//January 11 2017
	//This method runs through every space on the board and determines which moves are legal and returns them in an array
	//is only called after first click
	public void setPossibleMoves(Piece[][] b, Location from, ArrayList<Location> returnArray){
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				
				if(isValidMove(from, new Location(j,i), b)){
					
					returnArray.add(new Location(j,i));
				}
			}
				
		}
		
		
		for(Location e: returnArray)
			System.out.println(e.getRow() + " " + e.getColumn());
	}
	
	
	
	
	
	
	
	
	
	//Determines whether the piece can move at all, otherwise it will not allow the piece to be selected
	public boolean stuck(Piece[][] b, Location from){
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
				System.out.println("Here we are, stuck again!");
				if(isValidMove(from, new Location(j,i), b)){
					System.out.println("This piece can move to row " + j + " and column " + i );
					return false;
					
				}
			}
				
		}
		return true;
	}
	
	
	// method: draw
	// description: This method is used to draw the image onto the GraphicsPanel.  You shouldn't need to 
	//				modify this method.
	// parameters: Graphics g - this object draw's the image.
	//			   Component c - this is the component that the image will be drawn onto.
	//			   Location l - a Location that determines where to draw the piece.
	public void draw(Graphics g, Component c, Location l) {
        image.paintIcon(c, g, l.column*90 + 5, l.row*90 + 5); // you'll need to update the last two parameters so that it will 
        											  // correctly draw the piece in the right location.
    }

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public void setFirstTurn(boolean b) {
		firstTurn = b;
		
	}
}

