package com.app.finlit.ui.settings;

import com.app.finlit.data.models.UserModel;
import com.app.finlit.data.shared.DataResponse;
import com.app.finlit.data.shared.PageResponse;
import com.app.finlit.ui.chat.SingleChatActivity;
;

/**
 * Created by SOLANKI-PC on 01/03/2019.
 */

public class BlockPresenterImpl implements BlockContract.Presenter, BlockContract.OnCompleteListener {

    private BlockContract.Intractor intractor;
    private BlockContract.View view;


    public BlockPresenterImpl(BlockContract.View view){
        this.view = view;
        intractor = new BlockIntractorImpl();
    }


    @Override
    public void getBlockedUsers() {
        intractor.getBlockUsers(this);
    }

    @Override
    public void blockUser(UserModel model) {
        intractor.blockUser(model, this);
    }

    @Override
    public void unblockUser(UserModel model) {
        intractor.unblockUser(model, this);
    }

    @Override
    public void onSuccessUsers(PageResponse<UserModel> response) {
        view.onSuccessUsers(response);
    }

    @Override
    public void onSuccessBlock(DataResponse response) {
        view.onSuccessBlock(response);
    }

    @Override
    public void onSuccessUnblock(DataResponse response) {
        view.onSuccessUnblock(response);
    }

    @Override
    public void onFailure(String message) {
        view.onFailure(message);
    }
}
