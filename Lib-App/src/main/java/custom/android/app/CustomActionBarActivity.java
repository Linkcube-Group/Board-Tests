package custom.android.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import java.io.Serializable;

import custom.android.util.Timber;

public class CustomActionBarActivity extends ActionBarActivity {

    @SuppressWarnings("unchecked")
    protected <V extends Serializable> V getSerializable(final String name) {
        return (V) getIntent().getSerializableExtra(name);
    }

    protected int getIntExtra(final String name) {
        return getIntent().getIntExtra(name, -1);
    }

    protected boolean getBooleanExtra(final String name) {
        return getIntent().getBooleanExtra(name, false);
    }

    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d(((Object) this).getClass().getName() + " onCreate");

    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d(((Object) this).getClass().getName() + " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d(((Object) this).getClass().getName() + " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d(((Object) this).getClass().getName() + " onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.d(((Object) this).getClass().getName() + " onStop");
    }

}
