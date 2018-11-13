import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;



public class GameNim extends Game{
	
	static int coinsLeft = 13;
	  int WinningScore = 10;
    int LosingScore = -10;
    int NeutralScore = 0;
	
	public GameNim() {
    	currentState = new StateNim();
    }
	
	
	public boolean isWinState(State state)
    {
        StateNim tstate = (StateNim) state;
        for (int i = 0; i < tstate.board.length; i++) {
            if(tstate.board[i] == 'O') {
            	return false;
            }
        }
        return true;
    }
	
	// no chance to tie in this game
    public boolean isStuckState(State state) {   	
       return false;
    }
	
	
	public Set<State> getSuccessors(State state)
    {
		if(isWinState(state) || isStuckState(state))
			return null;
		
		Set<State> successors = new HashSet<State>();
        StateNim tstate = (StateNim) state;
        
        StateNim successor_state;
        for (int i = 1; i <= 3; i++) {
        	int counter = i;
            successor_state = new StateNim(tstate);    
            for(int j = successor_state.board.length-1; j >=0; j--){
	          	if(successor_state.board[j] == 'O' && counter != 0) {
	           		successor_state.board[j] = ' ';
	           		counter--;
               	} 
            }
            if(counter == 0){
            	successor_state.player = (state.player==0 ? 1 : 0); 
            	successors.add(successor_state);
            }
            
        }
    
        return successors;
    }	
    
    public double eval(State state) 
    {   
    	if(isWinState(state)) {
    		//player who made last move
    		int previous_player = (state.player==0 ? 1 : 0);
    	
	    	if (previous_player==1)
	            return WinningScore;
	    	else //human wins
	            return LosingScore;
    	}

        return NeutralScore;
    }
    
    
    public static void main(String[] args) throws Exception {
        
        Game game = new GameNim(); 
        Search search = new Search(game);
        int depth = 8;
        
        //needed to get human's move
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
        	
        	StateNim nextState = null;
        	
            switch ( game.currentState.player ) {
              case 1: //Human

            	/*
            	  //get human's move
                  System.out.print("Enter your *valid* move> ");
                  int pos = Integer.parseInt( in.readLine() );
                  while(pos > 3 || pos < 1) {
                	  System.out.print("Invalid move! Enter your *valid* move (hint: 1 or 2 or 3)> ");
                      pos = Integer.parseInt( in.readLine() );
                      
                  }*/
            	  
            	  
            	  
            	  
                  nextState = new StateNim((StateNim)game.currentState);
                  nextState.player = 1;
                  int count = 0;
                  for(int i=0; i< nextState.board.length; i++) {
                	  if(nextState.board[i] == 'O'){
                		  count++;
                	  }
                  }
                  
                  System.out.print("Enter your *valid* move> ");
                  int pos = Integer.parseInt( in.readLine() );
                  while(pos > 3 || pos < 1 || count - pos < 0) {
                	  System.out.print("Invalid move! Enter your *valid* move (hint: 1 or 2 or 3)> ");
                      pos = Integer.parseInt( in.readLine() );
                  }
                  
                  
                  
                  for(int i=0; i< pos; i++){
                	  for(int j = nextState.board.length-1; j >=0; j--){
                		  if(nextState.board[j] == 'O'){
                			  nextState.board[j] = 'X';
                			  break;
                		  }
                	  }
                  }
                  coinsLeft -= pos;
                  System.out.println("");
                  System.out.println("Human made his move. " + coinsLeft + " coins left.");
                  System.out.println("Human: \n" + nextState);
                  break;
                  
              case 0: //Computer
            	  
            	  nextState = (StateNim)search.bestSuccessorState(depth);
            	  nextState.player = 0;
            	  int counter = 0;
            	  for(int i = 0; i < nextState.board.length; i++){
            		  if(nextState.board[i] == 'O') {
            			  counter++;
            		  }
            	  }
            	  coinsLeft = counter;
            	  
            	  System.out.println("");
                  System.out.println("Computer made his move. " + coinsLeft + " coins left.");
            	  System.out.println("Computer: \n" + nextState);
                  break;
            }
                        
            game.currentState = nextState;
            //change player
            game.currentState.player = (game.currentState.player==0 ? 1 : 0);
            
            //Who wins?
            if ( game.isWinState(game.currentState) ) {
            
            	if (game.currentState.player == 0) 
            		System.out.println("Computer wins!");
            	else
            		System.out.println("You win!");
            	
            	break;
            }
            
            
            // useless
            if ( game.isStuckState(game.currentState) ) { 
            	System.out.println("Cat's game!");
            	break;
            }
        }
    }
}
