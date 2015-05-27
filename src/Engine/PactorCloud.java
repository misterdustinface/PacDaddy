package Engine;


import java.util.Iterator;

import datastructures.Table;

class PactorCloud implements Iterable<String> {
	
	volatile private Table<Pactor> pactors;
	
	PactorCloud() {
		pactors = new Table<Pactor>();
	}
	
	public void addPactor(String name, Pactor p) {
		pactors.insert(name, p);
	}
	
	public void removePactor(String name) {
		pactors.remove(name);
	}
	
	public Pactor getPactor(String name) {
		return pactors.get(name);
	}
	
	public String[] getPactorNames() {
		return pactors.getNames().toArray(new String[]{});
	}

	public Iterator<String> iterator() {
		return new PactorCloudIterator();
	}
	
	class PactorCloudIterator implements Iterator<String> {

		private String[] known;
		private int current;
		
		PactorCloudIterator() {
			known   = getPactorNames();
			current = 0;
		}
		
		public boolean hasNext() {
			while (current < known.length && !pactors.contains(known[current])) {
				current++;
			}
			return current < known.length;
		}

		public String next() {
			String name = known[current++];
			return pactors.contains(name) ? name : null;
		}

		public void remove() { }
	};

}