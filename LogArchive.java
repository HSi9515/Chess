import java.util.ArrayList;

public class LogArchive {
	
	private ArrayList<String> moves;
	private ArrayList<ArrayList<String>> largeDataStorage;
	
	public LogArchive(){
		moves = new ArrayList<String>();
		largeDataStorage = new ArrayList<ArrayList<String>>();
	}
	
	public ArrayList<String> getLog(){
		return moves;
	}
	public String convertColumn(int a){
		return "abcdefgh".substring(a, a+1);
	}
	public String convertRow(int a){
		
		a = Math.abs(a-7);
		
		return ""+(a+1);
		
	}
	public String determinePiece(Piece p){
		if(p instanceof King)
			return "King";
		else if(p instanceof Queen)
			return "Queen";
		else if(p instanceof Pawn)
			return "Pawn";
		else if(p instanceof Rook)
			return "Rook";
		else if(p instanceof Knight)
			return "Knight";
		else
			return "Bish";
				
	}
	public String determineColor(Piece p){
		if(p.getPlayer() ==1)
			return "W_";
		else
			return "B_";
	}
	public void refresh(){
		if(moves.size()>80){
			largeDataStorage.add(moves);
			moves.clear();
		}
			
			
	}
	
	
	
}
