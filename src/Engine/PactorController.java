package Engine;

import functionpointers.VoidFunctionPointer;


final public class PactorController {
	
	private Pactor pactor;
	
	public PactorController() {
		pactor = new Pactor();
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
	
	public VoidFunctionPointer wrapCommand(final String command) {
		return new VoidFunctionPointer() {
			public void call() {
				pactor.performAction(command);
			}
		};
	}
	
}