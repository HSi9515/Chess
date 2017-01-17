// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 12/2/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Dimension;

import java.awt.Font;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Point;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Color;

import java.awt.Component;



import javax.swing.ImageIcon;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements MouseListener{

	

	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.

	

	private Location from;   			    // holds the coordinates of the square that the user would like to move from.

	private Location to;   				    // holds the coordinates of the square that the user would like to move to.

	private Location highlight;				// holds the coordinates of the square that the user would like to move to.

	

	private int click;   						

	private int state;						//0 = playing, 1 = selecting, 2 = game over

	private int player;						//1 = white, 2 = black



	private Piece[][] board; 				// an 8x8 board of 'Pieces'.  Each spot should be filled by one of the chess pieces or a 'space'. 

	private ArrayList<Location> possibleMoves;

	private PieceIcon[] promotionSelections;

	private LogArchive gameLog;
	

	private Font font;

	public static String message;
	
	private int blackSum;
	private int whiteSum;

	

	private Location risingPawn;

	

	public GraphicsPanel()

	{

		setPreferredSize(new Dimension(SQUARE_WIDTH*8+2+(300),SQUARE_WIDTH*8+34));      

        this.setFocusable(true);					 // for keylistener

		this.addMouseListener(this);

		

		//FILLING THE BOARD

		board = new Piece[8][8];
		
		possibleMoves = new ArrayList<Location>();
		
		gameLog = new LogArchive();

		

		for(int i = 0; i<8; i++){

			for(int j = 0; j<board[i].length; j++){

				board[i][j] = new Filler();

			}

		}

		

		for(int i = 0; i<8; i++){

			board[6][i] = new Pawn(1); 

			board[1][i] = new Pawn(2); 

		}

		

		board[7][3] = new Queen();

		board[7][2] = new Bishop();

		board[7][5] = new Bishop();

		board[7][0] = new Rook();

		board[7][7] = new Rook();

		board[7][4] = new King();

		board[7][1] = new Knight();

		board[7][6] = new Knight();



		board[0][3] = new Queen(2);

		board[0][2] = new Bishop(2);

		board[0][5] = new Bishop(2);

		board[0][0] = new Rook(2);

		board[0][7] = new Rook(2);

		board[0][4] = new King(2);

		board[0][1] = new Knight(2);

		board[0][6] = new Knight(2);

		

		

		highlight = new Location(-99,0);

				

		click = 1;

		state = 0;

		player = 1;

		

		font = new Font("Optima", Font.PLAIN, 30);

		message = " ";

		

		promotionSelections = new PieceIcon[4];

		promotionSelections[0] = new PieceIcon("queen", new Point(185, 300));

		promotionSelections[1] = new PieceIcon("bishop", new Point(275, 300));

		promotionSelections[2] = new PieceIcon("rook", new Point(365, 300));

		promotionSelections[3] = new PieceIcon("knight", new Point(455, 300));



	}

	

	// method: paintComponent

	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is

	//   			first rendered.  It can also be called by this.repaint()

	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.

	public void paintComponent(Graphics g)

	{

		Graphics2D g2 = (Graphics2D) g;

		

		drawBoard(g2);

		

		drawHighlight(g2);

		

		drawPieces(g2);

		

		drawConsole(g2);
		
		
		drawPossibleMoves(g2);
		
		
		drawLog(g2);

		

		if (state == 1)

			drawSelectionPanel(g2);



	}



	

	public void mouseClicked(MouseEvent e) {

		Location clicked = new Location((int)e.getY()/90, (int)e.getX()/90);

	

		if (state == 0){

			System.out.println("Click "+ click);

			System.out.println("    c = " + clicked.column);

			System.out.println("    r = " + clicked.row);

			

			System.out.println("    "+ state);

			

			

			if(click == 1){

				if(board[clicked.row][clicked.column].getPlayer() == player){

					from = clicked;
					
					board[clicked.row][clicked.column].setPossibleMoves(board, new Location(clicked.row, clicked.column), possibleMoves);

					highlight = new Location(clicked.row, clicked.column);

					System.out.println("    " + board[clicked.row][clicked.column]);

					

					if(!board[from.getRow()][from.getColumn()].stuck(board, from)){

						click = 2;
						
						message = " ";

					}

					else{

						click = 1;	 

						message = "Stuck, try again";

					}

				}

			}

			

			else if(click == 2){

				to = clicked;

				

				if (board[from.row][from.column].isValidMove(from, to, board)){

					highlight.setRow(-99);
					
					possibleMoves.clear();  

					message = " ";

					System.out.println("    Valid move");

					

					if (board[to.row][to.column] instanceof King) {

						this.move(from, to);
						

						state = 2;

					}

					

					else{

						this.move(from, to);

						click = 1;

						

						if (board[to.row][to.column] instanceof Pawn && (to.row == 0 || to.row == 7)){

							risingPawn = new Location(to.getRow(), to.getColumn());

							state = 1;

							message = "Promote your pawn";

						}

						else if (player == 1)

							player++;		

						else if (player == 2)

							player--;



					}

				}

				

				else

					message = "Invalid move"; 

				//edit this to be able to show things like "Check, try again". Tricky though, since we might want do that through

				//another class

			}

		}

		

		

		else if (state == 1){

			for (PieceIcon p : promotionSelections){

				if (p.getBounds().contains(e.getPoint())){

					board[risingPawn.row][risingPawn.column] = p.getNewPiece(player);
					
					

					state = 0;

					message = " ";

					

					if (player == 1)

						player++;		

					else if (player == 2)

						player--;

				}

			}	

		}

		

		this.repaint();

	}

	


	
	

	public void move(Location f, Location t){

		Piece p = board[f.getRow()][f.getColumn()];
		board[f.getRow()][f.getColumn()] = new Filler();
		board[t.getRow()][t.getColumn()] = p;
		p.setFirstTurn(false);
		
		gameLog.getLog().add(gameLog.determineColor(p) + gameLog.determinePiece(p) 
		+ " " + gameLog.convertColumn(t.getColumn()) + gameLog.convertRow(t.getRow()));
		
	
		
		//Castling Code
		if(p instanceof King){
			King p2 = (King) p;
			if(p.getPlayer() == 1){
			
				
				if(p2.castleStatus().equals("left")){
					move(new Location(7,0),new Location(7,3));
					board[7][7].setFirstTurn(false);
					
				}
				else if(p2.castleStatus().equals("right"))
					move(new Location(7,7), new Location(7,5));
					board[7][0].setFirstTurn(false);
			}
			else if(p.getPlayer() == 2){
				if(p2.castleStatus().equals("left")){
					move(new Location(0,0),new Location(0,3));
					board[0][7].setFirstTurn(false);
				}
				else if(p2.castleStatus().equals("right"))
					move(new Location(0,7), new Location(0,5));
					board[0][0].setFirstTurn(false);
			}
		}

	}

	

	public void drawBoard(Graphics g2){

		g2.setColor(Color.getHSBColor(0, 200, 1));
		g2.fillRect(0, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.setColor(Color.BLACK);
		g2.drawLine(SQUARE_WIDTH*8, 0, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, SQUARE_WIDTH*8, SQUARE_WIDTH*8, SQUARE_WIDTH*8);
		g2.drawLine(0, 0, SQUARE_WIDTH*8, 0);
		g2.drawLine(0, 0, 0, SQUARE_WIDTH*8);

		

		for(int i = 0; i <8; i+=2)

			for (int j = 1; j<8; j+=2)

			{

				g2.setColor(Color.gray.darker());

				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);

			}

		

		for(int i = 1; i <8; i+=2)

			for (int j = 0; j<8; j+=2)

			{

				g2.setColor(Color.gray.darker());

				g2.fillRect(i*SQUARE_WIDTH,j*SQUARE_WIDTH,SQUARE_WIDTH,SQUARE_WIDTH);

			}

		

		ClassLoader cldr = this.getClass().getClassLoader();	

		ImageIcon woodGrain = new ImageIcon(cldr.getResource("images2/wood overlay.png"));

		woodGrain.paintIcon(this, g2, 0, 0);
		
		
		
		
		

	}

	

	public void drawHighlight(Graphics g2){

		if(highlight.getRow() >= 0 && highlight.getRow() <= 8){

			g2.setColor(Color.YELLOW);

			for (int i = 0; i < 4; i++)

				g2.drawRect(highlight.column*SQUARE_WIDTH+i, highlight.row*SQUARE_WIDTH+i, SQUARE_WIDTH-2*i, SQUARE_WIDTH-2*i);

		}

	}
	public void drawPieces(Graphics g2){

		for(int i = 0; i<board.length;  i++)
			for(int j = 0; j<board[i].length; j++)
				board[i][j].draw(g2, this, new Location(i,j));

	}
	public void drawConsole(Graphics g2){

		g2.setColor(Color.GREEN.darker().darker().darker());
		g2.fillRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);		
		g2.setColor(Color.BLACK);
		g2.drawRect(0, SQUARE_WIDTH*8+2, SQUARE_WIDTH*8, 30);
		g2.setFont(font);
		g2.setColor(Color.WHITE);

		

		if (state == 2 && player == 1)
			g2.drawString("WHITE WINS!", 260, 747);

		else if (state == 2 && player == 2)
			g2.drawString("BLACK WINS!", 260, 747);

		else{

			if (player == 1)
				g2.drawString("PLAYER: WHITE", 10, 747);
			else if (player == 2)
				g2.drawString("PLAYER: BLACK", 10, 747);
			
			
			
			
			g2.setColor(Color.getHSBColor(0, 200, 1));
			g2.setFont(font.deriveFont(Font.PLAIN, 25));
			g2.drawString(message, (int) (695-message.length()*11.3), 746);
			
			
			
			

		}
	}
	public void drawSelectionPanel(Graphics g2){

		g2.setColor(Color.GREEN.darker());
		g2.fillRect(160, 240, 400, 200);
		g2.setColor(Color.BLACK);
		g2.drawRect(160, 240, 400, 200);

		for (PieceIcon p : promotionSelections)
			p.draw(this, g2, player);

	}
	
	public void drawPossibleMoves(Graphics g2){
		for(int i = 0; i<board.length;  i++){ 			
			for(int j = 0; j<board[i].length; j++){
				for(Location e: possibleMoves)
					if(e.getRow() == i && e.getColumn() == j){
						g2.setColor(Color.CYAN);
					
						g2.fill3DRect(j*SQUARE_WIDTH+35,i*SQUARE_WIDTH+35,SQUARE_WIDTH-70,SQUARE_WIDTH-70, true);
					}
						
				
			}
		}
	}
	public void drawLog(Graphics g2){
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(725, 0, 300, 1000);
		
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Optima", Font.PLAIN, 40));
		g2.drawString("Log", 850, 45);
		
		int ypos = 75;
		int xpos = 0;
		g2.setFont(new Font("Optima", Font.PLAIN, 12));
		
		
		for(int i = 0; i<gameLog.getLog().size(); i++){
			
			if(i%4 == 0 && i != 0){
				ypos+=15;
				xpos = 0;
				
			}
			g2.drawString(gameLog.getLog().get(i), (xpos*75)+725, ypos);
			xpos++;
		}
		
		materialAdvantage();
		
		g2.setFont(new Font("Optima", Font.PLAIN, 15));
		g2.drawString("Black Material: " + blackSum, 890, 747);
		g2.drawString("White Material: " + whiteSum, 730, 747);
		
		gameLog.refresh();
		
	}
	public void materialAdvantage(){
		 whiteSum = 0;
		 blackSum = 0;
		
		int val = 0;
		
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8; j++){
			
				if(board[i][j] instanceof Pawn)
					val = 1;
				else if(board[i][j] instanceof Rook)
					val = 3;
				else if(board[i][j] instanceof Knight)
					val = 2;
				else if(board[i][j] instanceof Bishop)
					val = 2;
				else if(board[i][j] instanceof Queen)
					val = 4;
				else
					val = 0;
				
				if(board[i][j].getPlayer() == 1)
					whiteSum+=val;
				else if(board[i][j].getPlayer() == 2)
					blackSum+=val;
				
			}
		}
		
		
	}
	


	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
	

}
