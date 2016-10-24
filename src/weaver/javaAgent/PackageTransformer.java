package weaver.javaAgent;

public class PackageTransformer extends InstrumentorTransformer {

	private String packageName;

	public PackageTransformer(String packageName) {
		super();
		this.packageName = packageName;
	}

	@Override
	protected boolean transformClass(String className) {
		return className.contains(packageName);
	}
}
