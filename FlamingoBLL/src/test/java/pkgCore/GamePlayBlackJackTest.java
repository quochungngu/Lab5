package pkgCore;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import pkgEnum.eBlackJackResult;
import pkgException.DeckException;
import pkgException.HandException;

public class GamePlayBlackJackTest {
	private GamePlayBlackJack GPBJ;
	private GamePlayerHand GPH1;
	private GamePlayerHand GPH2;
	
	@Before
	public void makeBlackJackGamePlay() throws HandException,DeckException{
		// Making GamePlayBlackJack
		Deck d = new Deck();
		HashMap<UUID, Player> hmTablePlayer = new HashMap<UUID,Player>();
		Player p1 = new Player("p1", 1);
		Player p2 = new Player("p2", 1);
		
		hmTablePlayer.put(p1.getPlayerID(), p1);
		hmTablePlayer.put(p2.getPlayerID(), p2);
		
		GPBJ = new GamePlayBlackJack(hmTablePlayer, d);
		
		// Creating artificial hands for players
		HandBlackJack h1 = new HandBlackJack();
		HandBlackJack h2 = new HandBlackJack();
		
		// player 1's h1 always max 18
		h1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.EIGHT));
		h1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
		
		// player 2's h2 always max 21
		h2.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.ACE));
		h2.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
		h2.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
		
		GPH1 = new GamePlayerHand(GPBJ.getGameID(),p1.getPlayerID(),UUID.randomUUID());
		GPH2 = new GamePlayerHand(GPBJ.getGameID(),p1.getPlayerID(),UUID.randomUUID());
		
		HashMap<GamePlayerHand, Hand> hmGameHands = new HashMap<GamePlayerHand, Hand>();
		hmGameHands.put(GPH1, h1);
		hmGameHands.put(GPH2, h2);
		
		// setting artificial hands
		GPBJ.setHmGameHands(hmGameHands);
	}

	@Test
	public void TestPlayerWinning() throws HandException, DeckException {
		HandBlackJack hDealer = new HandBlackJack();
		// Dealer is 17
		hDealer.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.SEVEN));
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		GPBJ.setHDealerForTests(hDealer);
		
		// Score player 1's 18 against dealer
		System.out.println("\nWin player higher");
		GPBJ.ScoreGame(GPH1);
		
		HandBlackJack h1 = (HandBlackJack) GPBJ.gethmGameHand(GPH1);
		assertEquals(h1.getBlackJackResult(),eBlackJackResult.WIN);
		
		// Dealer is now 27; bust for dealer, player wins if not busted
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		// Scoring player 2's 21 against busted dealer
		System.out.println("\nWin dealer bust");
		GPBJ.ScoreGame(GPH2);
		
		HandBlackJack h2 = (HandBlackJack) GPBJ.gethmGameHand(GPH2);
		assertEquals(h2.getBlackJackResult(),eBlackJackResult.WIN);
	}

	@Test
	public void TestPlayerLosing() throws HandException {
		HandBlackJack hDealer = new HandBlackJack();
		// Dealer is 19
		hDealer.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.NINE));
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		GPBJ.setHDealerForTests(hDealer);
		
		// Score player 1's 18 against dealer
		System.out.println("\nLose player lower");
		GPBJ.ScoreGame(GPH1);
		
		HandBlackJack h1 = (HandBlackJack) GPBJ.gethmGameHand(GPH1);
		assertEquals(h1.getBlackJackResult(),eBlackJackResult.LOSE);
		
		// Player 2 is now 29, and busted; automatic loss
		HandBlackJack h2 = (HandBlackJack) GPBJ.gethmGameHand(GPH2);
		h2.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.EIGHT));
		
		GPBJ.putHandToGame(GPH2, h2);

		// Scoring player 2's bust against dealer
		System.out.println("\nLose player bust");
		GPBJ.ScoreGame(GPH2);
		assertEquals(h2.getBlackJackResult(),eBlackJackResult.LOSE);
	}
	
	@Test
	public void TestPlayerTie() throws HandException {
		HandBlackJack hDealer = new HandBlackJack();
		// Dealer is 18
		hDealer.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.EIGHT));
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		GPBJ.setHDealerForTests(hDealer);
		
		// Score player 1's 18 against dealer
		System.out.println("\nTie Test");
		GPBJ.ScoreGame(GPH1);
		
		HandBlackJack h1 = (HandBlackJack) GPBJ.gethmGameHand(GPH1);
		assertEquals(h1.getBlackJackResult(),eBlackJackResult.TIE);
	}
	
	@Test
	public void TestTwoPlayersWinning() throws HandException
	{
		HandBlackJack hDealer = new HandBlackJack();
		// Dealer is 27, bust for dealer; player wins if not busted
		hDealer.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.SEVEN));
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		hDealer.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		GPBJ.setHDealerForTests(hDealer);
		
		// Score player 1's 18 and player 2's 21 against dealer
		System.out.println("\nTwo Winners; dealer bust");
		GPBJ.ScoreGame(GPH1);
		GPBJ.ScoreGame(GPH2);
		
		HandBlackJack h1 = (HandBlackJack) GPBJ.gethmGameHand(GPH1);
		HandBlackJack h2 = (HandBlackJack) GPBJ.gethmGameHand(GPH2);
		
		assertEquals(h1.getBlackJackResult(),eBlackJackResult.WIN);
		assertEquals(h2.getBlackJackResult(),eBlackJackResult.WIN);
	}
}
