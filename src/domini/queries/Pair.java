package domini.queries;

/**
 * Representa una parella d'element
 * @author Cristina Raluca Vijulie
 *
 * @param <T1> primer element parella
 * @param <T2> segon element parella
 */
public class Pair<T1,T2> {
	public T1 first;
	public T2 second;
	
/**
 * Crea un parell amb els objectes f i s
 * @param f objecte que estara a l'argument first
 * @param s objecte que estara a l'argument second
 */
	public Pair(T1 f, T2 s){
		first = f;
		second = s;
	}
	public String toString(){
		return first.toString()+": "+second.toString();
	}
}
