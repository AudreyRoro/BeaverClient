package com.example.admin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.beaver.R;
import com.example.admin.model.Concerned;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Marianne on 26/01/15.
 */
public class ConcernedAdapter extends ArrayAdapter<Concerned> {

    List<Concerned> concernedList;
    private final static Logger log = Logger.getLogger(Concerned.class);
    LayoutInflater inflater;

    Context context;

    public ConcernedAdapter(Context context, int resource, List<Concerned> concernedList) {
        super(context, resource, concernedList);
        this.concernedList = concernedList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    private class ViewHolder{
        TextView code;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;

        log.info("ConcertView : " + position);

        if(convertView == null)
        {
            convertView = inflater.inflate (R.layout.custom_concerned_list_layout, null);
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            convertView.setTag(holder);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Concerned concerned = (Concerned) cb.getTag();
                    Toast.makeText(context,
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    concerned.setChecked(cb.isChecked());
                }
            });

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Concerned concerned  = concernedList.get(position);
        holder.code.setText(" (" +  concerned.getParticipant().getUser().getuPseudo() + ")");
        holder.checkBox.setText(concerned.getParticipant().getUser().getuPseudo());
        holder.checkBox.setChecked(concerned.isChecked());
        holder.checkBox.setTag(concerned);

        return convertView;

    }

}


