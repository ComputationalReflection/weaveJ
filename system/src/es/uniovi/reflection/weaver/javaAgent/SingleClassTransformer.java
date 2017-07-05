package es.uniovi.reflection.weaver.javaAgent;

public class SingleClassTransformer extends InstrumentorTransformer {

	private String nameForTransform;

	public SingleClassTransformer(String nameForTransform) {
		super();
		this.nameForTransform = nameForTransform;
	}

	@Override
	protected boolean transformClass(String className) {
		return className.contains(nameForTransform);
	}	
	

}
