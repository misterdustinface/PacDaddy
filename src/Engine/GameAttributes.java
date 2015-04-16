package Engine;

import InternalInterfaces.AttributeSetter;
import PacDaddyApplicationInterfaces.PacDaddyAttributeReader;
import datastructures.Table;

public class GameAttributes implements PacDaddyAttributeReader, AttributeSetter {
	final private Table<Object> attributes;
	
	public GameAttributes() {
		attributes = new Table<Object>();
	}
	
	public void setAttribute(String name, Object value) {
		attributes.insert(name, value);
	}
	
	public Object getValueOf(String attributeName) {
		return attributes.get(attributeName);
	}
	
	public String[] getAttributes() {
		return (String[]) attributes.getNames().toArray(new String[]{});
	}
	
}