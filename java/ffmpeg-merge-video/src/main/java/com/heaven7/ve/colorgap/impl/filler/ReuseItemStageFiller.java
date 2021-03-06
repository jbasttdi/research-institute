package com.heaven7.ve.colorgap.impl.filler;

import com.heaven7.utils.Context;
import com.heaven7.ve.colorgap.MediaPartItem;
import com.heaven7.ve.cross_os.IPlaidInfo;
import com.heaven7.ve.gap.GapManager;

import java.util.List;

/**
 * @author heaven7
 */
public class ReuseItemStageFiller extends MatchStageFiller{

    @Override
    public void fillImpl(Context context, List<IPlaidInfo> plaids, List<MediaPartItem> items, GapManager.GapCallback callback) {
        new GapManager(plaids, items).fill(callback, true, false);
    }
}
