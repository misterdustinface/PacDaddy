package PacDaddyApplicationInterfaces;

public interface PacDaddyApplication {
	PacDaddyBoardReader 	getBoardReader();
	PacDaddyAttributeReader getGameAttributeReader();
	PacDaddyInput			getInputProcessor();
}
