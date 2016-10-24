package weaver;

import java.lang.invoke.MethodHandle;

import util.GenericSet;
import weaver.methods.Method;

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
