package com.palana.paachoodelivery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;

import static com.palana.paachoodelivery.SharedPrefs.Keys.TOKEN;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialogFragment progressDialogFragment;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String orderStatus;
    private RecyclerView rvOrders;
    private TextView tvEmpty;
    StateDialog stateDialog;
    public OrdersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersListFragment newInstance(String param1, String param2) {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderStatus = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        rvOrders = view.findViewById(R.id.rv_orders);
        tvEmpty = view.findViewById(R.id.tv_empty);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOrders.setLayoutManager(layoutManager);
        rvOrders.setHasFixedSize(true);
//        getOrdersList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrdersList();
    }

    private void getOrdersList(){
        progressDialogFragment = ProgressDialogFragment.newInstance();
        progressDialogFragment.show(getChildFragmentManager(), "progress_dialog");
        ApiClient.getApiInterface().ordersList("Bearer "+ SharedPrefs.getString(TOKEN,""),orderStatus).enqueue(new Callback<com.palana.paachoodelivery.Response>() {
            @Override
            public void onResponse(Call<com.palana.paachoodelivery.Response> call, Response<com.palana.paachoodelivery.Response> response) {
                try {
                    if (progressDialogFragment != null)
                        progressDialogFragment.dismiss();
                    com.palana.paachoodelivery.Response jsonObject = response.body();
                    if (response.isSuccessful()) {
                        if (jsonObject.getData()!=null && jsonObject.getData().size()>0){
                            rvOrders.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                            OrdersListAdapter ordersListAdapter = new OrdersListAdapter(getContext(),jsonObject.getData(),orderStatus);
                            rvOrders.setAdapter(ordersListAdapter);
                        }else {
                            rvOrders.setVisibility(View.GONE);
                            tvEmpty.setVisibility(View.VISIBLE);
                        }
                    }else {
                        rvOrders.setVisibility(View.GONE);
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<com.palana.paachoodelivery.Response> call, Throwable t) {
                try {
                    if (progressDialogFragment != null )
                        progressDialogFragment.dismiss();
                    rvOrders.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }catch (Exception e){

                }

            }
        });
    }
}
