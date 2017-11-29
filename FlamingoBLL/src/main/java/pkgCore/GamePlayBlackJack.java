package pkgCore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();
	
	
	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {
	
		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);	
		
		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.putHandToGame(GPH, h);
		}
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());
			
			h.AddCard(c);
			
			this.putHandToGame(GPH, h);

		}
		return c;
	}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		HandBlackJack h = (HandBlackJack) this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack)h.ScoreHand();
		
		if (HSB.getNumericScores().getFirst() <= 21) { // first element is the smallest score
			bCanPlayerDraw = true;
		}
		// Logically a player wouldn't draw if they have 21, but it's technically legal
		// for them to draw as long as they're not busted.

		return bCanPlayerDraw;
	}
	
	
	
	public boolean bDoesDealerHaveToDraw() throws HandException
	{
		boolean bDoesDealerHaveToDraw = true;
		
		
		HandScoreBlackJack HSB = (HandScoreBlackJack)hDealer.ScoreHand();
		
		if (HSB.getNumericScores().getLast() >= 17) { //last element is largest score
			bDoesDealerHaveToDraw = false;
		}
		
		return bDoesDealerHaveToDraw;
	}
	
	public void ScoreGame(GamePlayerHand GPH) throws HandException {

		Hand playerHand = this.gethmGameHand(GPH);

		if (!bCanPlayerDraw(GPH)) { //Player can't draw, player bust, automatic loss for player
			//player is loser
		}
		else if (bIsDealerBusted()) { //Dealer busts
			playerHand.setbWinner();
		}
		else if (findHighestScore(playerHand) > findHighestScore(hDealer)) {
			playerHand.setbWinner();
		}
		else if (findHighestScore(playerHand) < findHighestScore(hDealer)) {
			//lose
		}
		else {
			// Only possibility left is that playerHand and hDealer are equal
		}
		
		this.putHandToGame(GPH, playerHand);

	}
	
	private boolean bIsDealerBusted() throws HandException {
		boolean isDealerBusted = true;

		HandScoreBlackJack dealerScore = (HandScoreBlackJack) hDealer.ScoreHand();
		if (dealerScore.getNumericScores().getFirst() <=21) {
			// first element is always lowest score
			isDealerBusted = false;
		}
		
		return isDealerBusted;
	}

	private int findHighestScore(Hand hand) throws HandException{
		HandScoreBlackJack HSB = (HandScoreBlackJack) hand.ScoreHand();
		
		int highestScore = HSB.getNumericScores().getFirst();
		
		for(int score:HSB.getNumericScores()) {
			if (score <= 21) { //NumericScores sorted from lowest to highest scores
				highestScore = score;
			}
		}
		
		return highestScore;
	}
	
	
	public Player getpDealer() {
		return pDealer;
	}
}
