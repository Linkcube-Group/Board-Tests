package me.linkcube.skeatest;


import custom.android.util.PreferenceUtils;
import custom.android.util.Timber;
import android.app.Application;

public class TakuApplication extends Application {

	public TakuApplication() {
		super();
	}

	@Override
	public void onCreate() {
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		} else {
			Timber.plant(new Timber.HollowTree());
		}
		//PreferenceUtils.initDataShare(getApplicationContext());
	}
	
	@Override
    public void onTerminate() {
        super.onTerminate();
    }

    public final class BuildConfig {
        public final static boolean DEBUG = true;
    }
}
