package pkgCore;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;

import pkgException.DeckException;
import pkgException.HandException;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWinning() throws HandException, DeckException {
		//creating a game of blackjack
		Deck d = new Deck();
		Player p1 = new Player("p1", 1);
		HashMap<UUID, Player> players = new HashMap<UUID,Player>();
		players.put(p1.getPlayerID(), p1);
		GamePlayBlackJack GPBJ = new GamePlayBlackJack(players, d);
		
		//creating hands
		HandBlackJack HP1 = new HandBlackJack();
		HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.ACE));
		HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
		
		HandBlackJack HD = new HandBlackJack();
		HD.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.SEVEN));
		HD.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
		
		
		//adding hands to game
		GamePlayerHand GPH1 = new GamePlayerHand(GPBJ.getGameID(), p1.getPlayerID(), HP1.getHandID());
		GPBJ.putHandToGame(GPH1, HP1);
		GamePlayerHand GD = new GamePlayerHand(GPBJ.getGameID(), GPBJ.getpDealer().getPlayerID(), HD.getHandID());
		GPBJ.putHandToGame(GD, HD);
		
		GPBJ.ScoreGame(GPH1);
		assertTrue(GPBJ.gethmGameHand(GPH1).getbWinner());
		
	}

	@Test
	public void TestPlayerLosing() throws HandException {
		//creating a game of blackjack
				Deck d = new Deck();
				Player p1 = new Player("p1", 1);
				HashMap<UUID, Player> players = new HashMap<UUID,Player>();
				players.put(p1.getPlayerID(), p1);
				GamePlayBlackJack GPBJ = new GamePlayBlackJack(players, d);
				
				//creating hands
				HandBlackJack HP1 = new HandBlackJack();
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.SEVEN));
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.TWO));
				
				HandBlackJack HD = new HandBlackJack();
				HD.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.ACE));
				HD.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
				
				
				//adding hands to game
				GamePlayerHand GPH1 = new GamePlayerHand(GPBJ.getGameID(), p1.getPlayerID(), HP1.getHandID());
				GPBJ.putHandToGame(GPH1, HP1);
				GamePlayerHand GD = new GamePlayerHand(GPBJ.getGameID(), GPBJ.getpDealer().getPlayerID(), HD.getHandID());
				GPBJ.putHandToGame(GD, HD);
				
				GPBJ.ScoreGame(GPH1);
				assertFalse(GPBJ.gethmGameHand(GPH1).getbWinner());
	}
	
	@Test
	public void TestPlayerTie() throws HandException {
		//creating a game of blackjack
				Deck d = new Deck();
				Player p1 = new Player("p1", 1);
				HashMap<UUID, Player> players = new HashMap<UUID,Player>();
				players.put(p1.getPlayerID(), p1);
				GamePlayBlackJack GPBJ = new GamePlayBlackJack(players, d);
				
				//creating hands
				HandBlackJack HP1 = new HandBlackJack();
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.ACE));
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
				
				HandBlackJack HD = new HandBlackJack();
				HD.AddCard(new Card(pkgEnum.eSuit.SPADES, pkgEnum.eRank.ACE));
				HD.AddCard(new Card(pkgEnum.eSuit.SPADES, pkgEnum.eRank.KING));
				
				
				//adding hands to game
				GamePlayerHand GPH1 = new GamePlayerHand(GPBJ.getGameID(), p1.getPlayerID(), HP1.getHandID());
				GPBJ.putHandToGame(GPH1, HP1);
				GamePlayerHand GD = new GamePlayerHand(GPBJ.getGameID(), GPBJ.getpDealer().getPlayerID(), HD.getHandID());
				GPBJ.putHandToGame(GD, HD);
				
				GPBJ.ScoreGame(GPH1);
				assertFalse(GPBJ.gethmGameHand(GPH1).getbWinner());
	}
	
	@Test
	public void TestTwoPlayersWinning() throws HandException
	{
		//creating a game of blackjack
				Deck d = new Deck();
				Player p1 = new Player("p1", 1);
				Player p2 = new Player("p2", 2);
				HashMap<UUID, Player> players = new HashMap<UUID,Player>();
				players.put(p1.getPlayerID(), p1);
				players.put(p2.getPlayerID(),p2);
				
				//creating hands
				HandBlackJack HP1 = new HandBlackJack();
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.ACE));
				HP1.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
				
				HandBlackJack HP2 = new HandBlackJack();
				HP1.AddCard(new Card(pkgEnum.eSuit.SPADES, pkgEnum.eRank.ACE));
				HP1.AddCard(new Card(pkgEnum.eSuit.SPADES, pkgEnum.eRank.KING));
				
				HandBlackJack HD = new HandBlackJack();
				HD.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.SEVEN));
				HD.AddCard(new Card(pkgEnum.eSuit.HEARTS, pkgEnum.eRank.KING));
				

				GamePlayBlackJack GPBJ = new GamePlayBlackJack(players,d);
				//adding hands to game
				GamePlayerHand GPH1 = new GamePlayerHand(GPBJ.getGameID(), p1.getPlayerID(), HP1.getHandID());
				GPBJ.putHandToGame(GPH1, HP1);
				GamePlayerHand GPH2 = new GamePlayerHand(GPBJ.getGameID(), p2.getPlayerID(),HP2.getHandID());
				GPBJ.putHandToGame(GPH2, HP2);
				GamePlayerHand GD = new GamePlayerHand(GPBJ.getGameID(), GPBJ.getpDealer().getPlayerID(), HD.getHandID());
				GPBJ.putHandToGame(GD, HD);
				
				GPBJ.ScoreGame(GPH1);
				assertTrue(GPBJ.gethmGameHand(GPH1).getbWinner());
				assertTrue(GPBJ.gethmGameHand(GPH2).getbWinner());
	}
}
