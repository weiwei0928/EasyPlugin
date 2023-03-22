package zeus.plugin;

import android.content.Intent;

import androidx.fragment.app.Fragment;

/**
 * Description:
 * Date: 2023/3/17
 * Author: weiwei
 */
public class ZeusBaseFragment extends Fragment {
    @Override
    public void startActivity(Intent intent) {
        PluginManager.startActivity(intent);
    }
}
