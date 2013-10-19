package au.org.paperminer.ws;

import org.apache.lucene.store.RAMDirectory;

public class RamDirectory {

	public RAMDirectory init()
	{
		return new RAMDirectory();
	}
}
