package es.uniovi.reflection.weaver;

import java.lang.invoke.MethodHandle;

import es.uniovi.reflection.weaver.methods.Method;
import es.uniovi.reflection.weaver.util.GenericSet;

public class PointcutForObjects extends PointcutImpl{

	private GenericSet set;
	
	public PointcutForObjects(MethodHandle component, Method method) {
		super(component, method);
	}
	public void setSet(GenericSet set){
		this.set=set;
	}
	public void addObjects(Object...objects){
		for(Object o:objects)
			set.add(o);
	}
	public void removeObjects(Object...objects){
		for(Object o:objects)
			set.remove(o);
	}
	
}
