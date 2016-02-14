package lv.neueda.testing.converter.extractor;


public interface Extractor<CreatedEntity>{
	CreatedEntity extract(Object node, Object expectedEntity);

	boolean isApplicable(Object node);
}
