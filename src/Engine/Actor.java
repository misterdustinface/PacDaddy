package Engine;

import java.util.Set;

import datastructures.Table;
import functionpointers.VoidFunctionPointer;

public class Actor {
	
	final private Table<VoidFunctionPointer> actions;
	
	Actor() {
		actions = new Table<VoidFunctionPointer>();
	}
	
	final public void learnAction(String action, VoidFunctionPointer implementation) {
		actions.insert(action, implementation);
	}
	
	final public void forgetAction(String action) {
		actions.remove(action);
	}
	
	final public void performAction(String action) {
		if (actions.contains(action)) {
			actions.get(action).call();
		} else {
			throw new RuntimeException(action + " is not a valid action for this actor." );
		}
	}
	
	final public Set<String> getActions() {
		return actions.getNames();
	}
	
}