//Implementation for Set135, a client of List135
//Justin Voo (6767)

public final class Set135<E> {
	private final List135<E> items;
	
	//Default constructor
	public Set135() {
		this.items = new List135<E>();
	}
	
	//Constructor that initializes items
	private Set135(List135<E> e) {
		this.items = e;
	}
	
	//Checks to see if items is empty
	public boolean isEmpty() {
		return items.isEmpty();
	}
	
	//Checks to see if items contains element e
	public boolean contains(E e) {
		return contains(items, e);
	}
	
	//Helper method for contains()
	private boolean contains(List135<E> thisItems, E e) {
		if (thisItems.isEmpty())
			return false;
		else if (thisItems.first() == e)
			return true;
		else
			return contains(thisItems.rest(), e);
	}
	
	//Checks to see if items contains all the elements in s
	public boolean containsAll(Set135<E> s) {
		return containsAll(items, s.items);
	}
	
	//Helper method for containsAll()
	private boolean containsAll(List135<E> thisItems, List135<E> sItems) {
		if (sItems.isEmpty())
			return true;
		else if (contains(thisItems, sItems.first()))
			return containsAll(thisItems, sItems.rest());
		else
			return false;
	}
	
	//Adds element e to items
	public Set135<E> add(E e) {
		if (!this.contains(e))
			return new Set135<E>(items.cons(e));
		else
			return this;
	}
	
	//Adds all the elements in s to items (Union)
	public Set135<E> addAll(Set135<E> s) {
		return new Set135<E>(addAll(items, s.items));
	}
	
	//Helper method for addAll()
	private List135<E> addAll(List135<E> thisItems, List135<E> sItems) {
		if (sItems.isEmpty())
			return thisItems;
		else if (this.contains(sItems.first()))
			return addAll(thisItems, sItems.rest());
		else
			return addAll(thisItems.cons(sItems.first()), sItems.rest());
	}
	
	//Removes element e from items
	public Set135<E> remove(E e) {
		return new Set135<E>(remove(items, e));
	}
	
	//Helper method for remove()
	private List135<E> remove(List135<E> thisItems, E e) {
		if (thisItems.isEmpty())
			return thisItems;
		else if (thisItems.first() == e)
			return thisItems.rest();
		else 
			return remove(thisItems.rest(), e).cons(thisItems.first());
	}
	
	//Removes all the elements in s from items (Difference)
	public Set135<E> removeAll(Set135<E> s) {
		return new Set135<E>(removeAll(items, s.items));
	}
	
	//Helper method for removeAll()
	private List135<E> removeAll(List135<E> thisItems, List135<E> sItems) {
		if (sItems.isEmpty())
			return thisItems;
		else
			return removeAll(remove(thisItems, sItems.first()), sItems.rest());
	}
	
	//Retains all elements in both items and s (Intersection)
	public Set135<E> retainAll(Set135<E> s) {
		return new Set135<E>(retainAll(items, s.items));
	}
	
	//Helper method for retainAll()
	private List135<E> retainAll(List135<E> thisItems, List135<E> sItems) {
		if (sItems.isEmpty())
			return new List135<E>();
		else if (contains(sItems.first()))
			return retainAll(thisItems, sItems.rest()).cons(sItems.first());
		else
			return retainAll(thisItems, sItems.rest());
	}
	
	//Calls the toString method of items
	public String toString() {
		return items.toString();
	}
}
