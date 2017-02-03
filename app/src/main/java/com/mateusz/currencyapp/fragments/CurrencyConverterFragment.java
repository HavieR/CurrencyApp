package com.mateusz.currencyapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.mateusz.currencyapp.CurrencyApplication;
import com.mateusz.currencyapp.R;
import com.mateusz.currencyapp.database.CurrencyDao;
import com.mateusz.currencyapp.models.Rate;

import java.util.List;

/**
 * Created by Mateusz on 02.02.2017.
 */
public class CurrencyConverterFragment extends Fragment {
    public static final String TAG=CurrencyConverterFragment.class.getSimpleName();
    private List<Rate> rates;
    private CurrencyDao currencyDao;
    private Double mid;
    private boolean foreignToPln=true;
    private TextView firstCurrencyTv,secondCurrencyTv,resultTv;
    private ImageButton swapBtn;
    private EditText chosenCurrencyEt;
    private Spinner listCurrencySpinner;
    private final String PLN="PLN polski złoty";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG,"Method onCreateView");
        currencyDao=((CurrencyApplication)getActivity().getApplication()).getCurrencyDao();
        rates=currencyDao.getRates();
        mid=rates.get(0).getMid();
        return inflater.inflate(R.layout.converter_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listCurrencySpinner= (Spinner) view.findViewById(R.id.spinner_currency_first);
        firstCurrencyTv= (TextView) view.findViewById(R.id.first_currency_tv);
        secondCurrencyTv= (TextView) view.findViewById(R.id.second_currency_tv);
        resultTv= (TextView) view.findViewById(R.id.result_tv);
        chosenCurrencyEt= (EditText) view.findViewById(R.id.chosen_currency_et);
        swapBtn= (ImageButton) view.findViewById(R.id.swap_imgbtn);

        mid=rates.get(0).getMid();
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,rates);
        listCurrencySpinner.setAdapter(adapter);
        firstCurrencyTv.setText(rates.get(0)+"");
        secondCurrencyTv.setText(PLN);
        resultTv.setText("0");
        listCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(foreignToPln) {
                    firstCurrencyTv.setText(rates.get(position).toString());
                    secondCurrencyTv.setText(PLN);
                }
                else{
                    firstCurrencyTv.setText(PLN);
                    secondCurrencyTv.setText(rates.get(position).toString());
                }
                mid=rates.get(position).getMid();
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chosenCurrencyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                convertCurrency();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        swapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foreignToPln=!foreignToPln;
                String buffor=firstCurrencyTv.getText().toString();
                firstCurrencyTv.setText(secondCurrencyTv.getText().toString());
                secondCurrencyTv.setText(buffor);
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        String strValue=chosenCurrencyEt.getText().toString();
        if(strValue.equals("")) {
            resultTv.setText("0");
        }else {
            Double doubValue=Double.parseDouble(strValue);
            if (foreignToPln) {
                resultTv.setText(doubValue * mid + "");
            } else {
                resultTv.setText(doubValue / mid + "");
            }
        }
    }
}
