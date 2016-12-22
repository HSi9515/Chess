public class Rook extends Piece
{
public Rook(int x){
		super.setImageIcon("images2/rook2.png");
		super.setPlayer(x);
	}
	
	public boolean isValidMove(Location from, Location to, Piece[][] b){
		if(from.getRow()==to.getRow() && from.getColumn()!=to.getColumn()){
			return true;
		}
		
		if(from.getColumn()==to.getColumn() && from.getRow()!=to.getRow()){
			return true;
		}
		
		else
			return false;
	}
}
