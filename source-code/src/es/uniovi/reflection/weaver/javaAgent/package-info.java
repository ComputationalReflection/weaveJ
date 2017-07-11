/**
Classes necessary to instrument applications at load time.
It also provides the classes necessary for formatting application classes at load time.
This class transformation is fundamental, because we need to modify some opcodes in the Java bytecode. 
More specifically weaveJ works using the invoke-dynamic instruction and it has to replace other bytecode opcodes like invokevirtual, invokespecial, putfield, getfield, etc. 
*/

package es.uniovi.reflection.weaver.javaAgent;