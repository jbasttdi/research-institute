package net.thdev.mediacodecexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.heaven7.core.util.Logger;
import com.heaven7.core.util.PermissionHelper;

public abstract class BasePermissionListActivity extends BaseListAcitivty {

    private final PermissionHelper mHelper = new PermissionHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new BasePermissionActivity.SdcardPermissionProvider());
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * start request permissions
     * @param provider the permission provider
     */
    public final void requestPermissions(BasePermissionActivity.PermissionProvider provider) {
        final String tag = getClass().getSimpleName();
        mHelper.startRequestPermission(provider.getRequestPermissions(), provider.getRequestPermissionCodes(),
                (requestPermission, requestCode, success) -> {
                    Logger.w(tag, "onRequestPermissionResult",
                            "success = " + success + " ,permission = " + requestPermission);
                    onRequestPermissionEnd(requestPermission, requestCode, success);
                });
    }

    /**
     * called on request permission end
     * @param permission the permission
     * @param requestCode the request code
     * @param success true if request permission success
     */
    protected abstract void onRequestPermissionEnd(String permission, int requestCode, boolean success);

}
