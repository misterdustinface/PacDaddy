package PacDaddyApplicationInterfaces;

public interface PacDaddyApplication {
	PacDaddyBoardReader 	getBoardReader();
	PadDaddyAttributeReader getGameAttributeReader();
	PacDaddyInput			getInputProcessor();
}
