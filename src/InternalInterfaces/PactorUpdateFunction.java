package InternalInterfaces;

import Engine.Pactor;

public interface PactorUpdateFunction {
	void call(Pactor toUpdate);
	
	public final static PactorUpdateFunction EMPTY_FUNCTION = new PactorUpdateFunction() {
		public void call(Pactor toUpdate) {
		}
	};
}
