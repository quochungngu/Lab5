package pkgCore;

import java.util.HashMap;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;

public class cMain {
	public static void main(String  args[]) throws HandException, DeckException {
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
		HD.AddCard(new Card(pkgEnum.eSuit.CLUBS, pkgEnum.eRank.KING));
		
		//adding hands to game
		GamePlayerHand GPH1 = new GamePlayerHand(GPBJ.getGameID(), p1.getPlayerID(), HP1.getHandID());
		//GPBJ.putHandToGame(GPH1, HP1);
		HashMap<GamePlayerHand, Hand> hmGameHands = new HashMap<GamePlayerHand, Hand>();
		hmGameHands.put(GPH1, HP1);
		GPBJ.setHmGameHands(hmGameHands);
		//GPBJ.getHmGamePlayers().replace(GPBJ.getHmGamePlayers().get(p1).getPlayerID(),p1);
		//GamePlayerHand GD = new GamePlayerHand(GPBJ.getGameID(), GPBJ.getpDealer().getPlayerID(), HD.getHandID());
		//GPBJ.putHandToGame(GD, HD);
	
		System.out.println(p1.getPlayerID());
		System.out.println();
	}
}
