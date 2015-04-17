package Engine;


final public class PactorController {
	
	private Pactor pactor;
	
	public PactorController() {
		
	}
	
	public void setPactor(Pactor pactor) {
		this.pactor = pactor;
	}
	
	public void sendCommandToPactor(String command) {
		pactor.performAction(command);
	}
	
	public String[] getPactorCommands() {
		return (String[]) pactor.getActions().toArray(new String[]{});
	}
	
}