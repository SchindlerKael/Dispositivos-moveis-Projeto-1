package com.example.cafecompao.fragment;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafecompao.R;
import com.example.cafecompao.adapter.AdapterInsumo;
import com.example.cafecompao.db.InsumoDAO;
import com.example.cafecompao.model.Insumo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaInsumosFragment extends Fragment {

    private static final String KEY_PARAM = "key_param";

    private RecyclerView recyclerView;
    private List<Insumo> listaInsumos;
    private String insumoType;
    private InsumoDAO dao;
    private Context context;
    OnDataPass dataPasser;

    public interface OnDataPass {
        public void onDataPass(List<Insumo> data, String insumoType);
    }

    public ListaInsumosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    public static ListaInsumosFragment newInstance(String param) {
        ListaInsumosFragment f = new ListaInsumosFragment();
        f.setArguments(arguments(param));
        return f;
    }

    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_insumos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerView);

        dao = new InsumoDAO(context);

        insumoType = getArguments().getString(KEY_PARAM);

        listaInsumos = dao.retornarPorTipo(insumoType);

        // Configurar o AdapterInsumo
        AdapterInsumo insumoAdapter = new AdapterInsumo(this.listaInsumos);

        insumoAdapter.setCallback(new AdapterInsumo.Callback() {
            @Override
            public void onSetList(List<Insumo> lista) {
                passData(lista);
            }
        });

        // Configurar RecyclerView layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayout.VERTICAL));
        recyclerView.setAdapter(insumoAdapter);
    }

    public void passData(List<Insumo> data) {
        dataPasser.onDataPass(data, insumoType);
    }

}
