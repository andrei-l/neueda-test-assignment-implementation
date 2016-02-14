package lv.neueda.testing.converter.extractor;


import lv.neueda.testing.mindmap.pojo.xml.Node;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractExtractor<CreatedEntity, ExpectedEntity> implements Extractor<CreatedEntity> {

	@Override
	public CreatedEntity extract(Object node, Object expectedEntity) {
		return doExtract((Node) node, expectedEntity);
	}

	protected abstract CreatedEntity doExtract(Node node, Object expectedEntity);

	protected ExpectedEntity castToExpectedEntity(Object expectedEntity) {
		return (ExpectedEntity) expectedEntity;
	}

	protected boolean isExpectedEntity(Object expectedEntity) {
		return getExpectedEntityClass().isAssignableFrom(expectedEntity.getClass());
	}

	protected IllegalStateException createException(Node node, Object expectedEntity) {
		return new IllegalStateException(String.format("Invalid situation occurred: node: %s, entity: %s", node, expectedEntity));
	}

	protected abstract Class<ExpectedEntity> getExpectedEntityClass();

	protected String extractData(List<Object> nodes, Pattern pattern, int matchingGroupNumber, boolean dropIteratedEntity) {
		for (Iterator<Object> iterator = nodes.iterator(); iterator.hasNext(); ) {
			Object n = iterator.next();
			if (n instanceof Node) {
				Node node = (Node) n;
				Matcher matcher = pattern.matcher(node.getTEXT());
				if (matcher.matches()) {
					if (dropIteratedEntity) {
						iterator.remove();
					}
					return matcher.group(matchingGroupNumber);
				}
			}
		}
		throw new IllegalArgumentException("Failed to find: " + pattern.pattern());
	}
}
