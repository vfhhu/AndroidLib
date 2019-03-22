package xyz.vfhhu.lib.android.base_interface;

import android.content.Intent;

/**
 * Created by leo3x on 2019/3/21.
 */

public interface ActivityResultCallback {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
