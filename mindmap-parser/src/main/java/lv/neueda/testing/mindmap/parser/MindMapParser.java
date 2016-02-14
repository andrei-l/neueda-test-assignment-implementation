package lv.neueda.testing.mindmap.parser;

import java.io.File;

public interface MindMapParser<MindMapEntry> {
	MindMapEntry parseMindMap(File inputFile);
}
