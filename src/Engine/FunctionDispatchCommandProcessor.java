package Engine;

import datastructures.Table;
import functionpointers.VoidFunctionPointer;
import PacDaddyApplicationInterfaces.PacDaddyInput;

public class FunctionDispatchCommandProcessor implements PacDaddyInput {

	final private Table<VoidFunctionPointer> inputFunctions;
	
	public FunctionDispatchCommandProcessor() {
		inputFunctions = new Table<VoidFunctionPointer>();
	}
	
	public void addCommand(String command, VoidFunctionPointer implementation) {
		inputFunctions.insert(command, implementation);
	}
	
	public void sendCommand(String command) {
		if (inputFunctions.contains(command)) {
			inputFunctions.get(command).call();
		} else {
			throw new RuntimeException(command + " is not a valid input command." );
		}
	}

	public String[] getCommands() {
		return (String[]) inputFunctions.getNames().toArray(new String[]{});
	}

}
