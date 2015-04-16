package Engine;


public class PactorController {
	
	private Pactor pactor;
	
	public PactorController() {
		
	}
	
	final public void setPactor(Pactor pactor) {
		this.pactor = pactor;
	}
	
	final public void sendCommandToPactor(String command) {
		pactor.performAction(command);
	}
	
	final public String[] getPactorCommands() {
		return (String[]) pactor.getActions().toArray(new String[]{});
	}
	
}