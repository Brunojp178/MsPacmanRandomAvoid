package pacman.controllers.examples;
import java.util.ArrayList;
import java.util.Random;
import pacman.controllers.Controller;
import pacman.game.Game;
import static pacman.game.Constants.*;


public class RandomAvoid extends Controller<MOVE>{
    private static final int MIN_DISTANCE=20;	//if a ghost is this close, run away
    private Random rnd=new Random();
    
	public MOVE getMove(Game game,long timeDue)
	{			
		int current=game.getPacmanCurrentNodeIndex(); // Current position of pacman
		
		//Strategy : if any non-edible ghost is too close (less than MIN_DISTANCE), run away
                // Choose a random direction
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
				if(game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost))<MIN_DISTANCE)
                                        // TODO put random.
//					return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost),DM.PATH);
                                        // Method that we create :D - Att Bruno e Clevinho
                                        return game.getNextMoveAwayRandom(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost), DM.PATH);
		
		//Strategy 2: find the nearest edible ghost and go after them 
		int minDistance=Integer.MAX_VALUE;
		GHOST minGhost=null;		
		
		for(GHOST ghost : GHOST.values())
			if(game.getGhostEdibleTime(ghost) > 5)
			{
				int distance=game.getShortestPathDistance(current,game.getGhostCurrentNodeIndex(ghost));
				
				if(distance<minDistance)
				{
					minDistance=distance;
					minGhost=ghost;
				}
			}
		
		if(minGhost!=null)	//we found an edible ghost
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(minGhost),DM.PATH);

                MOVE[] possibleMoves=game.getPossibleMoves(game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade()); //set flag as false to prevent reversals	
		
		return possibleMoves[rnd.nextInt(possibleMoves.length)];
	}
}
