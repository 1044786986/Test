package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Created by ljh on 2017/7/21.
 */
public class FragmentListView extends Fragment {
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
    private titleSelectInterface selectInterface;

    public interface titleSelectInterface{
            void onTitleSelect(String Title);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mStrings);
        listView = (ListView) getView().findViewById(R.id.lvFragment);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = listView.getItemAtPosition(i)+"";//获取内容
                selectInterface.onTitleSelect(str);
            }
        });
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try{
            selectInterface = (titleSelectInterface) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "必须继承OnArticleSelectedListener");
        }
    }
}
