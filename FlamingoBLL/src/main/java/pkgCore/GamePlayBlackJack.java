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

		Hand h = this.gethmGameHand(GPH);

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
		//		Dealer must draw unless they are bust and they don't have a 
		//		numeric score between 17-21.[Prof. seems to have made a typo, dealer must draw if they have don't have a 17-21]
		//		5-5 = 10, they must draw
		//		A-6 = 7 or 17, they must stay (because they have 17)
		//		J-J-J = 30, they are busted, they can't draw
		
		return bDoesDealerHaveToDraw;
	}
	
	
	
	
	
	
	
	public void ScoreGame(HashMap<GamePlayerHand, Hand> hmGameHands) throws HandException {
		boolean bIsHandWinner = false; // I don't think this is useful. -Hung
		
		Iterator it = hmGameHands.entrySet().iterator();
				
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			GamePlayerHand GPH = (GamePlayerHand) pair.getKey();
			HandBlackJack playerHand = (HandBlackJack) this.gethmGameHand(GPH);
			
			if (!bCanPlayerDraw(GPH)) { //Player busts; player loses even if dealer busts
				playerHand.setBlackJackResult(eBlackJackResult.LOSE);
				continue;
			}
			
			if (bIsDealerBusted()) { //Dealer busts
				playerHand.setBlackJackResult(eBlackJackResult.WIN);
				continue;
			}
			
			if (findHighestScore(playerHand) > findHighestScore(hDealer)) {
				playerHand.setBlackJackResult(eBlackJackResult.WIN);
				continue;
			}
			
			if (findHighestScore(playerHand) < findHighestScore(hDealer)) {
				playerHand.setBlackJackResult(eBlackJackResult.LOSE);
				continue;
			}
			
			if (findHighestScore(playerHand) == findHighestScore(hDealer)) {
				playerHand.setBlackJackResult(eBlackJackResult.TIE);
				continue;
			}
			//Determine if player is a winner
			
			// Find the Dealer's hand
			// Score Dealer's hand
			
			// Find Player's hand
			//Score Player's hand
			
			//If Player's hand > Dealer's hand and <= 21, then eBlackJackResult = WIN
			//			If Player's hand < Dealer's hand and Dealer didn't bust = LOSE
			//			If Player's hand == Dealer's hand and both didn't bust = TIE
			
		}
	}
	
	public boolean bIsDealerBusted() throws HandException {
		boolean isDealerBusted = true;

		HandScoreBlackJack dealerScore = (HandScoreBlackJack) hDealer.ScoreHand();
		if (dealerScore.getNumericScores().get(0) <=21) {
			isDealerBusted = false;
		}
		
		return isDealerBusted;
	}

	public int findHighestScore(Hand hand) throws HandException{
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
