package weaver.javaAgent;

public class PackageGroupTransformer extends InstrumentorTransformer {
	public static PackageGroupTransformer CLASS_TRANSFORMER;
	private String[] packageNames;

	public PackageGroupTransformer(String[] packageNames) {
		super();
		this.packageNames = packageNames;
		CLASS_TRANSFORMER = this;
	}

	@Override
	protected boolean transformClass(String className) {

		for (String name : packageNames) {
			if (className.contains(name))
				return true;
		}
		return false;
	}
}
