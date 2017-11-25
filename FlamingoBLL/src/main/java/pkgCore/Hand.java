package pkgCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;

public abstract class Hand {

	private UUID HandID;
	private ArrayList<Card> cards;
	private HandScore HS;
	//deleted boolean bWinner attribute

	public Hand() {
		this.HandID = UUID.randomUUID();
		cards = new ArrayList<Card>();
	}

	public UUID getHandID() {
		return HandID;
	}


	protected ArrayList<Card> getCards() {
		return cards;
	}

	public Card Draw(Deck d) throws DeckException {
		Card c = d.Draw();
		cards.add(c);
		return c;
	}
	
	protected HandScore getHS() {
		return HS;
	}

	protected void setHS(HandScore hS) {
		HS = hS;
	}

	public abstract HandScore ScoreHand() throws HandException;

	protected void AddCard(Card c) {
		cards.add(c);
	}
	
	//deleted bWinner setters and getters	
}
