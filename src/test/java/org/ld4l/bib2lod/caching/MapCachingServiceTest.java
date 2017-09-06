package org.ld4l.bib2lod.caching;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ld4l.bib2lod.caching.CachingService.CachingServiceException;
import org.ld4l.bib2lod.caching.CachingService.MapType;
import org.ld4l.bib2lod.testing.AbstractHfaTest;

public class MapCachingServiceTest extends AbstractHfaTest {
	
	private CachingService cachingService;

    @Before
    public void setUp() {
        cachingService = new MapCachingService();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void validUriPutAndGet() throws CachingServiceException {
    	String name = "someKey";
    	String uri = "http://localhost/someValue";
    	
    	cachingService.putUri(MapType.NAMES_TO_URI, name, uri);
    	Map<String, String> map = cachingService.getMap(MapType.NAMES_TO_URI);
    	Assert.assertNotNull(map);
    	Assert.assertEquals(uri, map.get(name));
    }
    
	@Test
	public void modifyUnmodifiableMap_throwsException() throws Exception {
		exception.expect(UnsupportedOperationException.class);
		Map<String, String> map = cachingService.getMap(MapType.NAMES_TO_URI);
		map.put("someKey", "someValue");
	}
}
