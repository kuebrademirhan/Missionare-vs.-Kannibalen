import static org.junit.Assert.*;
import org.junit.*;

public class JungleToursPublicTest {
	// ========== SYSTEM ==========
	protected static final String EX_NAME_JungleTours = "JungleTours";
	// --------------------

	// ========== PUBLIC TEST ==========
	@Test(timeout = 666)
	public void pubTest__solve__small() {
		for (int c = 3; c <= 12; c++) {
			for (int m = c; m <= 12; m++) {
				JungleToursPublicTest.check(c, m, !(m >= 4 && m == c));
			}
		}
	}

	@Test(timeout = 36666)
	public void pubTest__solve__big() {
		for (int c = 32; c <= 60; c++) {
			for (int m = c; m <= 60; m++) {
				JungleToursPublicTest.check(c, m, !(m == c));
			}
		}
	}

	// ========== HELPER ==========
	protected static void check(int c, int m, boolean hasSol) {
		String msg = "Result for (" + c + "," + m + ") contains ";
		int[][] actual = JungleTours.solve(c, m);
		if (actual != null) { // return value claims to be a solution
			int[] lastState = null;
			for (int[] currState : actual) {
				assertNotNull(msg + "state null.", currState);
				assertEquals(msg + "state with wrong length.", 5, currState.length);
				if (lastState == null) { // currState is first entry
					assertArrayEquals(msg + "wrong start state.", new int[]{c, m, 0, 0, 0}, currState);
				} else {
					int deltaCL = currState[0] - lastState[0];
					int deltaML = currState[1] - lastState[1];
					int deltaMR = currState[3] - lastState[3];
					int deltaCR = currState[4] - lastState[4];
					assertEquals(msg + "state with illegal total number of cannibals (drowned or newborn?).", c, currState[0] + currState[4]);
					assertEquals(msg + "state with illegal total number of missionaries (drowned or newborn?).", m, currState[1] + currState[3]);
					assertEquals(msg + "illegal (not alternating) boat position.", 1 - lastState[2], currState[2]);
					if (currState[2] == 0) {
						assertTrue(msg + "state with boat at left shore, but number of people did not increase correctly on that shore (boat under/overflow?).", deltaCL >= 0 && deltaML >= 0 && 0 < deltaCL + deltaML && deltaCL + deltaML <= 2);
					} else {
						assertTrue(msg + "state with boat at right shore, but number of people did not increase correctly on that shore (boat under/overflow?).", deltaMR >= 0 && deltaCR >= 0 && 0 < deltaMR + deltaCR && deltaMR + deltaCR <= 2);
					}
					assertTrue(msg + "state where cannibals eat up the missionaries on the left shore.", currState[1] <= 0 || currState[0] <= currState[1]);
					assertTrue(msg + "state where cannibals eat up the missionaries on the right shore.", currState[3] <= 0 || currState[3] >= currState[4]);
				}
				lastState = currState;
			}
			if (lastState != null) { // boat has been moved ;)
				assertArrayEquals(msg + "wrong end state.", new int[]{0, 0, 1, m, c}, lastState);
			} else {
				assertEquals(msg + "wrong end state.", 0, m);
				assertEquals(msg + "wrong end state.", 0, c);
			}
		} else if (hasSol) {
			fail("Result for (" + c + "," + m + ") is null, although a solution exists.");
		}
	}
}