package com.heaven7.ve.colorgap.impl;

import com.heaven7.ve.VEContext;
import com.heaven7.ve.MediaResourceItem;
import com.heaven7.ve.colorgap.MediaResourceScanner;
import com.heaven7.ve.colorgap.ResourceInitializer;

/**
 * Created by heaven7 on 2018/4/16 0016.
 */

/*public*/ class MockRectsScanner extends MediaResourceScanner {

    @Override
    public String scan(VEContext context, MediaResourceItem item, String srcDir) {
        return ResourceInitializer.getFilePathOfRects(item, srcDir);
    }
}
