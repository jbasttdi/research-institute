package com.heaven7.ve.colorgap;

import com.heaven7.utils.Context;
import com.heaven7.ve.cross_os.IPlaidInfo;
import com.heaven7.ve.gap.GapManager;

import java.util.List;

/**
 * Created by heaven7 on 2018/3/15 0015.
 */

public interface PlaidFiller {

    List<GapManager.GapItem> fillPlaids(Context mContext, List<IPlaidInfo> infoes, List<MediaPartItem> parts, ColorGapPostProcessor processor);
}
