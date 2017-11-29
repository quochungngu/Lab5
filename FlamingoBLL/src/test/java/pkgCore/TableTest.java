package pkgCore;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableTest {

	@Test
	public void TableTest1() {
		
		Player p1 = new Player("p1", 1);
		Player p2 = new Player("p2", 2);
		Table table = new Table();
		table.AddPlayerToTable(p1);
		table.AddPlayerToTable(p2);
		assertEquals(table.GetPlayerFromTable(p1), p1);
		assertEquals(table.GetPlayerFromTable(p2),p2);
		table.RemovePlayerFromTable(p1);
		table.RemovePlayerFromTable(p2);
		assertEquals(table.GetPlayerFromTable(p2), null);
	}

}
