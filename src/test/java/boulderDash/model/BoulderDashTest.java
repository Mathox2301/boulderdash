package boulderDash.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author lejeu
 */
public class BoulderDashTest {

    private Façade bd;

    @BeforeEach
    public void setUp() {
        bd = new Façade();
    }

    @Test
    public void testPosPlayer() {
        Position pos = new Position(2, 3);
        assertEquals(pos, bd.posPlayer());
    }

    @Test
    public void testMovePlayerOk() {
        Position pos = bd.posPlayer();
        bd.movePlayer(Direction.NORTH);
        assertNotEquals(pos, bd.posPlayer());
    }

    @Test
    public void testMovePlayerNotOk() {
        Position pos = bd.posPlayer();
        bd.movePlayer(Direction.WEST);
        assertEquals(pos, bd.posPlayer());
    }

    @Test
    public void testMovePlayerAndRock() {
        Position pos = bd.posPlayer();

        bd.movePlayer(Direction.NORTH);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.SOUTH);
        bd.movePlayer(Direction.WEST);
        assertTrue(bd.getTypeCase(pos).equals(TypeCase.ROCK));
    }

    @Test
    public void testMovePlayerAndDiamond() {
        bd.movePlayer(Direction.NORTH);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        bd.movePlayer(Direction.EAST);
        assertTrue(bd.getTypeCase(new Position(1, 10)).equals(TypeCase.PLAYER));
    }

    @Test
    public void testIsGameNotWin() {
        assertFalse(bd.isGameWin());
    }

    @Test
    public void testIsGameNotFinished() {
        assertFalse(bd.isGameFinished());
    }

    @Test
    public void testUndoRedo() {
        bd.movePlayer(Direction.NORTH);
        bd.movePlayer(Direction.EAST);
        Position pos = bd.posPlayer();
        bd.undo();
        bd.undo();
        bd.redo();
        bd.redo();
        assertEquals(pos, bd.posPlayer());
    }

    @Test
    public void testnewLvlNotSame() {
        Case[][] old = bd.getLvl().getBoard();
        bd.newLvl(2);
        boolean isSame = true;
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                Position pos = new Position(i, j);
                if (!old[i][j].getTypeCase().equals(this.bd.getTypeCase(pos))) {
                    isSame = false;
                }
            }
        }
        assertFalse(isSame);
    }

    @Test
    public void testnewLvlSame() {
        Case[][] old = bd.getLvl().getBoard();
        bd.newLvl(1);
        boolean isSame = true;
        for (int i = 0; i < old.length; i++) {
            for (int j = 0; j < old[0].length; j++) {
                Position pos = new Position(i, j);
                if (!old[i][j].getTypeCase().equals(this.bd.getTypeCase(pos))) {
                    isSame = false;
                }
            }
        }
        assertTrue(isSame);
    }

    @Test
    public void testFallRocks() {
        bd.newLvl(2);
        List<Position> oldRocks = bd.getLvl().allRockCases();
        bd.fallRocks();
        List<Position> rocks = bd.getLvl().allRockCases();
        boolean isChanged = false;
        for (int i = 0; i < oldRocks.size(); i++) {
            if (!oldRocks.get(i).equals(rocks.get(i))) {
                isChanged = true;
            }
        }
        assertTrue(isChanged);
    }

    @Test
    public void testGiveUp() {
        bd.giveUp();
        assertTrue(bd.isGameFinished());
    }

    @Test
    public void testSlipRocks() {
        bd.movePlayer(Direction.SOUTH);
        bd.movePlayer(Direction.WEST);
        bd.movePlayer(Direction.WEST);
        Position pos = new Position(bd.posPlayer().getX()+Direction.EAST.getDeltaX(),bd.posPlayer().getY()+Direction.EAST.getDeltaY());
        bd.fallRocks();
        System.out.println(bd.getTypeCase(pos));
        bd.movePlayer(Direction.EAST);
        bd.fallRocks();
        boolean isFinish=false;
        do{
        isFinish=bd.slipRocks();
        }while(isFinish==false);
        assertTrue(bd.getTypeCase(new Position(5,4)).equals(TypeCase.ROCK));
        
    }
}
