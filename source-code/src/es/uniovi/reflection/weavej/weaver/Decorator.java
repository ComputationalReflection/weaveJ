package es.uniovi.reflection.weavej.weaver;

import java.lang.invoke.MethodHandle;

/**
 * Decorator interface to generate java.lang.invoke.MethodHandle objects referring to the woven
 * method. The signature of this interface is designed to build new
 * MethodHandles from older ones referring to the component method. This
 * interface is only employed when the advice-type Around is used.
 *
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 *
 */

public interface Decorator {

	MethodHandle decorate(MethodHandle componentMethod);
}
