package pl.tajchert.cracowdroidconhackathon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMain extends Fragment {

    @Bind(R.id.statusText)
    TextView statusText;

    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.buttonFind)
    public void onButtonFindClick() {

    }
}
