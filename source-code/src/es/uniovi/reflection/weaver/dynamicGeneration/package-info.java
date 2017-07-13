/**
 * Classes necessary to create dynamically generated classes. Dynamically
 * generated methods in these classes will substitute old class method
 * references stored in the callsites. Every time a pointcut is declared and
 * created by the Weaver class, one of these dynamically generated classes is
 * generated.
 * 
 * @author Oscar Rodriguez-Prieto Date: 2017/07/11
 * 
 * @version 1.1.0
 *
 */

package es.uniovi.reflection.weaver.dynamicGeneration;