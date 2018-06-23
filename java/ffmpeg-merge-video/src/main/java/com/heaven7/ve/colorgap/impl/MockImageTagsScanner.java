package com.heaven7.ve.colorgap.impl;

import com.heaven7.ve.Context;
import com.heaven7.ve.MediaResourceItem;
import com.heaven7.ve.colorgap.ImageResourceScanner;
import com.heaven7.ve.colorgap.ResourceInitializer;

/**
 * the image tag scanner
 * @author heaven7
 */
/*public*/ class MockImageTagsScanner extends ImageResourceScanner {

    @Override
    public String scan(Context context, MediaResourceItem item, String srcDir, String filenamePrefix) {
        return ResourceInitializer.getFilePathOTagsForImageItem(item, srcDir, filenamePrefix);
    }
}