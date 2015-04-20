package InternalInterfaces;

import Engine.Pactor;

public interface PactorToTileFunction {
	
	String getTileName(Pactor pactor);
	
	public final static PactorToTileFunction EMPTY_FUNCTION = new PactorToTileFunction() {
		public String getTileName(Pactor pactor) {
			return "FLOOR";
		}
	};
}
