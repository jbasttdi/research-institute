package com.heaven7.ve.colorgap.impl;

import com.heaven7.ve.VEContext;
import com.heaven7.ve.MediaResourceItem;
import com.heaven7.ve.colorgap.ImageResourceScanner;
import com.heaven7.ve.colorgap.ResourceInitializer;

/**
 * the image rect scanner
 * @author heaven7
 */
/*public*/ class MockImageRectsScanner extends ImageResourceScanner {

    @Override
    public String scan(VEContext context, MediaResourceItem item, String srcDir, String filenamePrefix) {
        return ResourceInitializer.getFilePathOfRectsForImageItem(item, srcDir, filenamePrefix);
    }

}
