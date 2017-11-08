package es.uniovi.reflection.weavej.weaver;

/**
 * 
 * Interface used to encapsulate and manage pointcuts. Every time a pointcut is
 * declared and created using the {@link es.uniovi.reflection.weavej.weaver.Weaver
 * Weaver} class, a Pointcut object is returned. The unweaving operation is the
 * main functionality provided here.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */
public interface Pointcut {

	void unweave() throws Throwable;

}
