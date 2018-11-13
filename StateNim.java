

public class StateNim extends State{
	
	public char board[] = new char [13];
    
    public StateNim() {
    	
        for (int i=0; i< board.length; i++) 
            board[i] = 'O';
        
        player = 1;
    }
    
    public StateNim(StateNim state) {
    	
        for(int i=0; i<board.length; i++)
            this.board[i] = state.board[i]; 
        
        player = state.player;
    }
    
    public String toString() {
    	
    	String ret = "";
    	
    	for(int i=0; i<board.length; i++) {
    		if(board[i] == 'O'){
    			ret += board[i] + " ";
    		}
    	}
    	return ret;
    }
}
