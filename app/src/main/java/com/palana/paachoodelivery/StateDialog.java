package com.palana.paachoodelivery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.palana.paachoodelivery.SharedPrefs.Keys.TOKEN;

public class StateDialog extends DialogFragment {
    private RecyclerView rvOrders;
    private StateDialogListener stateDialogListener;
    private Context context;
    private List<Data> dataList;
    private ProgressDialogFragment progressDialogFragment;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_delivey_boys, null);
        rvOrders = view.findViewById(R.id.rv_boys);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
        dataList = new ArrayList<>();
        getDeliveryBoysList();
        builder.setView(view);
        return builder.create();

    }

    private void getDeliveryBoysList(){
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getChildFragmentManager(), "progress_dialog");
        ApiClient.getApiInterface().deliveryBoysList("Bearer "+ SharedPrefs.getString(TOKEN,"")).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                try {
                    if (progressDialogFragment != null)
                        progressDialogFragment.dismiss();
                    com.palana.paachoodelivery.Response jsonObject = response.body();
                    if (response.isSuccessful()) {
                        dataList.clear();
                        if (jsonObject.getData()!=null && jsonObject.getData().size()>0){
                            dataList.addAll(jsonObject.getData());
                            DeliveryBoysListAdapter deliveryBoysListAdapter = new DeliveryBoysListAdapter(getContext(),dataList, new ItemClick() {
                                @Override
                                public void onItemClick(int position) {
                                    stateDialogListener.applyState(dataList.get(position).getId());
                                    dismiss();
                                }
                            });
                            rvOrders.setAdapter(deliveryBoysListAdapter);
//                            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listState);
//                            statelist.setAdapter(adapter);
                        }else {

                        }
                    }else {

                    }
                }catch (Exception e){

                    if (e!=null){

                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        try {
            stateDialogListener = (StateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OfferDialogListener");
        }
    }

//    @Override
//    public void onState(List<State> stateList) {
//        if (stateList != null) {
//            for (int i = 0; i < stateList.size(); i++) {
//                listState.add(stateList.get(i).getName());
//                sateidlist.add(stateList.get(i).getId());
//            }
//
//            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listState);
//            statelist.setAdapter(adapter);
//        }
//    }


    public interface StateDialogListener {
        void applyState(int state);
    }

}
