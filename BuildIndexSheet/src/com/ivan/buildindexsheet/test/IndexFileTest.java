package com.ivan.buildindexsheet.test;

import org.junit.Assert;
import org.junit.Test;

import com.ivan.buildindexsheet.IndexFile;

public class IndexFileTest {

	@Test
	public void testIndexFile() {
		IndexFile file = new IndexFile("/", 1);
		
		Assert.assertNotNull(file);
		Assert.assertNotNull(file.getIcon());
		Assert.assertNull(file.childFiles());
		
		file = new IndexFile("/", 2);
		
		Assert.assertNotNull(file);
		Assert.assertNotNull(file.getIcon());
		Assert.assertNotNull(file.childFiles());
	}

}
