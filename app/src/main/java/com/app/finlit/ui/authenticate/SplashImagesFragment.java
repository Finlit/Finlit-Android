package com.app.finlit.ui.authenticate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.finlit.R;
import com.app.finlit.data.models.TourModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SplashImagesFragment extends Fragment {

    @BindView(R.id.iv_background)
    public ImageView ivBackground;

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;

    private Unbinder binder;
    private OnFragmentInteractionListener mListener;
    private TourModel tourModel;

    public SplashImagesFragment() {
        // Required empty public constructor
    }

    public static SplashImagesFragment newInstance(TourModel model) {
        SplashImagesFragment fragment = new SplashImagesFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tourModel = (TourModel) getArguments().getSerializable("data");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_images, container, false);
        binder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
