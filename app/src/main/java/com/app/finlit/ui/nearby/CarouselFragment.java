package com.app.finlit.ui.nearby;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.data.models.TourModel;
import com.app.finlit.data.models.UserModel;
import com.app.finlit.ui.home.MainActivity;
import com.app.finlit.utils.image.ImageLoaderUtils;
import com.cloudinary.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CarouselFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CarouselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarouselFragment extends Fragment {

    @BindView(R.id.linMain)
    public LinearLayout linearLayout;
    @BindView(R.id.iv_profile)
    public ImageView ivProfile;
    @BindView(R.id.iv_result)
    public ImageView ivResult;

    @BindView(R.id.tv_name)
    public TextView tvName;
    @BindView(R.id.tv_about)
    public TextView tvAbout;
    @BindView(R.id.tv_question)
    public TextView tvQuestion;
    @BindView(R.id.tv_age)
    public TextView tvAge;
    @BindView(R.id.tv_location)
    public TextView tvLocation;

    private OnFragmentInteractionListener mListener;
    private UserModel userModel;
    private int adapterType;
    private Unbinder binder;

    public CarouselFragment() {
        // Required empty public constructor
    }

    public static CarouselFragment newInstance(UserModel userModel, int adapterType) {
        CarouselFragment fragment = new CarouselFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", userModel);
        args.putInt("type", adapterType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userModel = (UserModel) getArguments().getSerializable("data");
            adapterType = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_carousel, container, false);
        binder = ButterKnife.bind(this, view);

        switch (adapterType) {
            case QuickViewActivity.ADAPTER_TYPE_TOP:
                linearLayout.setBackgroundResource(R.drawable.shadow);
                break;
            case QuickViewActivity.ADAPTER_TYPE_BOTTOM:
                linearLayout.setBackgroundResource(0);
                break;
        }

        initFields();

        return view;
    }

    private void initFields() {
            if (userModel != null) {
                tvName.setText(userModel.getName());
                tvAbout.setText(userModel.getAboutUs());
                if (userModel.getLocation() != null)
                    tvLocation.setText(userModel.getLocation().getAddress());
                tvQuestion.setText(StringUtils.join(userModel.getQuestion(), ","));
                tvAge.setText(String.valueOf(userModel.getAgeGroup()));
                if (userModel.getProfileType() != null) {
                    switch (userModel.getProfileType()) {
                        case "novice":
                            ivResult.setBackgroundResource(R.mipmap.result_novice_small);
                            break;
                        case "proficent":
                            ivResult.setBackgroundResource(R.mipmap.result_proficent_small);
                            break;
                        default:
                            ivResult.setBackgroundResource(R.mipmap.result_expert_small);
                            break;
                    }
                }
                if (userModel.getImgUrl() != null) {
                    ImageLoader.getInstance().displayImage(userModel.getImgUrl(), ivProfile,
                            ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS);
                }

            }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
