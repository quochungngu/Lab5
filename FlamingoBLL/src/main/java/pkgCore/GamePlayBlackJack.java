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

	public boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
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

		if (findHighestScore(hDealer) >= 17 || bIsDealerBusted()) {
			bDoesDealerHaveToDraw = false;
		}
		
		return bDoesDealerHaveToDraw;
	}
	public void ScoreGame(GamePlayerHand GPH) throws HandException {

		HandBlackJack playerHand = (HandBlackJack) this.gethmGameHand(GPH);
		
		if (!bCanPlayerDraw(GPH)) { //Player can't draw, player bust, automatic loss for player
			playerHand.setBlackJackResult(eBlackJackResult.LOSE);
			System.out.println("Player busts.");
		}
		else if (bIsDealerBusted()) { //Dealer busts
			playerHand.setBlackJackResult(eBlackJackResult.WIN);
			System.out.println("Dealer busts.");
		}
		else if (findHighestScore(playerHand) > findHighestScore(hDealer)) {
			playerHand.setBlackJackResult(eBlackJackResult.WIN);
			System.out.println("Player higher.");
		}
		else if (findHighestScore(playerHand) < findHighestScore(hDealer)) {
			playerHand.setBlackJackResult(eBlackJackResult.LOSE);
			System.out.println("Player lower.");
		}
		else {
			// Only possibility left is that playerHand and hDealer are equal
			playerHand.setBlackJackResult(eBlackJackResult.TIE);
			System.out.println("Player tie.");
		}
		
		this.putHandToGame(GPH, playerHand);
	}
	
	private boolean bIsDealerBusted() throws HandException {
		boolean isDealerBusted = true;
		HandScoreBlackJack dealerScore = (HandScoreBlackJack) hDealer.ScoreHand();
		
		if (dealerScore.getNumericScores().getFirst() <= 21) {
			// first element is always lowest score
			isDealerBusted = false;
		}
		
		return isDealerBusted;
	}

	private int findHighestScore(Hand hand) throws HandException{
		// Find highest score 21 and under; or lowest score over 21
		HandScoreBlackJack HSB = (HandScoreBlackJack) hand.ScoreHand();
		int highestScore = -1;
		
		for(int score:HSB.getNumericScores()) {
			if (score > 21) { //NumericScores sorted from lowest to highest scores
				break;
			}
			else {
				highestScore = score;
			}
		}
		
		return highestScore;
	}
	
	
	public Player getpDealer() {
		return pDealer;
	}
	
	protected void setHDealerForTests(HandBlackJack h) { // for testing only
		this.hDealer = h;
	}
}
