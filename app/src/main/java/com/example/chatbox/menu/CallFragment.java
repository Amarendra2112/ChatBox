package com.example.chatbox.menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatbox.R;
import com.example.chatbox.adapter.CallAdapter;
import com.example.chatbox.model.CallList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallFragment newInstance(String param1, String param2) {
        CallFragment fragment = new CallFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        recyclerView = view.findViewById(R.id.CallRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<CallList> list = new ArrayList<>();
        list.add(new CallList("1","Name","01/01/2012","23:10","","receive"));
        list.add(new CallList("2","Name","01/01/2012","23:10","https://www.google.com/search?q=random+images&sxsrf=ALeKk02lg8g4OndY5iU35opjOpcDl9qPig:1595420232062&tbm=isch&source=iu&ictx=1&fir=Sbh_if3SN-39DM%252CBDsDWFZCtC3kDM%252C_&vet=1&usg=AI4_-kRTgdJch2RKw6P2xC705Eu6OkH8Og&sa=X&ved=2ahUKEwipiszB6-DqAhWF63MBHX-oBxIQ9QEwDXoECAoQRg&biw=1918&bih=1009#imgrc=Sbh_if3SN-39DM","missed"));
        list.add(new CallList("3","Name","01/01/2012","23:10","https://www.google.com/search?q=random+images&sxsrf=ALeKk02lg8g4OndY5iU35opjOpcDl9qPig:1595420232062&tbm=isch&source=iu&ictx=1&fir=Sbh_if3SN-39DM%252CBDsDWFZCtC3kDM%252C_&vet=1&usg=AI4_-kRTgdJch2RKw6P2xC705Eu6OkH8Og&sa=X&ved=2ahUKEwipiszB6-DqAhWF63MBHX-oBxIQ9QEwDXoECAoQRg&biw=1918&bih=1009#imgrc=Sbh_if3SN-39DM","attend"));
        recyclerView.setAdapter(new CallAdapter(list,getContext()));
        return view;
    }
}