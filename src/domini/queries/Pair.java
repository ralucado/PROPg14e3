package domini.queries;

public class Pair<T1,T2> {
	public T1 first;
	public T2 second;
	
	/**
	 * @post first = f, second = s
	 */
	public Pair(T1 f, T2 s){
		first = f;
		second = s;
	}
	public String toString(){
		return first.toString()+": "+second.toString();
	}
}