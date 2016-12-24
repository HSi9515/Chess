import java.awt.Component;
import java.awt.Graphics;
import java.lang.*;
public class Pawn extends Piece {

	boolean firstTurn;
	
	public Pawn(){
		super(1, "images2/pawn1.png");
		firstTurn = true;
	}
	
	public Pawn(int player){
		super(player);
		firstTurn = true;
		
		if(player == 1)
			super.setImageIcon("pawn");
		else
			super.setImageIcon("pawn");
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][]b){
		
	
	System.out.println(Math.abs((double) from.row - to.row));	
	
	int distanceJumped = (int) Math.abs((double) from.row - to.row);
	Piece toPiece = b[to.getRow()][to.getColumn()];
	
	switch(super.getPlayer()){
	
		case 1:
		
			if(from.getRow()>to.getRow() && toPiece.getPlayer() != this.getPlayer()){
				if(distanceJumped == 1 && to.getColumn() == from.getColumn() && toPiece.getPlayer() == 3){
					firstTurn = false;
					return true;
				}
				else if(distanceJumped == 1 && to.getColumn() != from.getColumn() && toPiece.getPlayer() == 2){
					firstTurn = false;
					return true;
				}
				else if(distanceJumped == 2 && to.getColumn() == from.getColumn() && firstTurn && b[to.getRow()+1][to.getColumn()].getPlayer() == 3){
					firstTurn = false;
					return true;	
				}
				else
					return false;
			}
		break;
		
		case 2:
			
			if(from.getRow()<to.getRow() && toPiece.getPlayer() != this.getPlayer()){
				if(distanceJumped == 1 && to.getColumn() == from.getColumn() && toPiece.getPlayer() == 3){
					firstTurn = false;
					return true;
				}
				else if(distanceJumped == 1 && to.getColumn() != from.getColumn() && toPiece.getPlayer() == 1){
					firstTurn = false;
					return true;
				}
				else if(distanceJumped == 2  && to.getColumn() == from.getColumn() && firstTurn && b[to.getRow()-1][to.getColumn()].getPlayer() == 3){
					firstTurn = false;
					return true;
				}
				else
					return false;
			}
		break;	
		
		}
		return false;
	}
	
	
	
	
}
	

