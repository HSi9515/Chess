//1/7/16
//Wrote at Kickoff!

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PieceIcon {
	private String piece;
	private ImageIcon image1;
	private ImageIcon image2;
	private Point coordinate;
	
	public PieceIcon(){
		this("queen", new Point(-100, -100));
	}
	
	public PieceIcon(String piece, Point coordinate){
		this.piece = piece;
		
		ClassLoader cldr = this.getClass().getClassLoader();
		image1 = new ImageIcon(cldr.getResource("images/" + piece + "1.png"));
		image2 = new ImageIcon(cldr.getResource("images/" + piece + "2.png"));
		
		this.coordinate = coordinate;
	}
	
	public String getPieceName(){
		return piece;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(coordinate.x, coordinate.y, image1.getIconWidth(), image1.getIconHeight());
	}
	
	public void draw(Component c, Graphics g, int player){
		if (player == 1)
			image1.paintIcon(c, g, coordinate.x, coordinate.y);
		else if (player == 2)
			image2.paintIcon(c, g, coordinate.x, coordinate.y);
	}
	
	public Piece getNewPiece(int player){
		/**IS THERE A WAY TO NOT HARD CODE THIS? SOME FANCY OPERATION WITH STRINGS AND CLASS NAMES...?**/
		
		if (piece.equals("queen"))
			return new Queen(player);
		else if (piece.equals("bishop"))
			return new Bishop(player);
		else if (piece.equals("rook"))
			return new Rook(player);
		else if (piece.equals("knight"))
			return new Knight(player);
		
		return new Piece();
	}
	
}
