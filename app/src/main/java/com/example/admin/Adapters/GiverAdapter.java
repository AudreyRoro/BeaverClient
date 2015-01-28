package com.example.admin.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.beaver.R;
import com.example.admin.model.Concerned;
import com.example.admin.model.Debt;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Marianne on 27/01/15.
 */
public class GiverAdapter extends ArrayAdapter<Debt> {

    private List<Debt> debtList;

    public List<Debt> getDebtList() {
        return debtList;
    }

    public void setDebtList(List<Debt> debtList) {
        this.debtList = debtList;
    }

    private final static Logger log = Logger.getLogger(Concerned.class);

    LayoutInflater inflater;
    Context context;

    public GiverAdapter(Context context, int resource, List<Debt> debtList) {
        super(context, resource, debtList);
        this.debtList = debtList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    private class ViewHolder{
        TextView infos_debt_name;
        TextView infos_debt_amount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        log.info("ConcertView : " + position);

        if(convertView == null)
        {
            convertView = inflater.inflate (R.layout.custom_debt_layout, null);
            holder = new ViewHolder();
            holder.infos_debt_name = (TextView) convertView.findViewById(R.id.textView_name);
            holder.infos_debt_amount = (TextView) convertView.findViewById(R.id.textView_amount);
            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Debt debt = debtList.get(position);
        holder.infos_debt_name.setText(debt.getGiver().getUser().getuPseudo());
        holder.infos_debt_amount.setText(debt.getdAmount() + " €");
        holder.infos_debt_amount.setTextColor(Color.GREEN);
        return convertView;

    }

}


