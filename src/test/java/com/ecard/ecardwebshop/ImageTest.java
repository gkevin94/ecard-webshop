package com.ecard.ecardwebshop;

import com.ecard.ecardwebshop.image.Image;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ImageTest {
    @Test
    public void getterSetterTest() {
        Image image = new Image(1, new byte[1], null, "pelda", 1);
        image.setId(2);
        image.setFileBytes(new byte[1]);
        image.setMediaType(null);
        image.setFileName("bela");
        image.setProductId(2);
        assertEquals(2, image.getId());
        assertEquals(0, image.getFileBytes()[0]);
        assertNull(image.getMediaType());
        assertEquals("bela", image.getFileName());
        assertEquals(2, image.getProductId());
    }
}
