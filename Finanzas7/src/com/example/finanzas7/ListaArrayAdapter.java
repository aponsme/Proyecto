package com.example.finanzas7;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ListaArrayAdapter extends ArrayAdapter<Valores> {
	private LayoutInflater layoutInflater;

	public ListaArrayAdapter(Context context, List<Valores> objects) {
		super(context, 0, objects);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// holder pattern
		Hold holder = null;
		if (convertView == null) {
			holder = new Hold();
			convertView = layoutInflater.inflate(R.layout.listvalores, null);
			holder.setT1((TextView) convertView.findViewById(R.id.t1));
			holder.setT2((TextView) convertView.findViewById(R.id.t2));
			holder.setT3((TextView) convertView.findViewById(R.id.t3));
			holder.setT4((TextView) convertView.findViewById(R.id.t4));
			convertView.setTag(holder);
		} else {
			holder = (Hold) convertView.getTag();
		}

		final Valores val = getItem(position);
		holder.getT1().setText(val.getEmpresa());
		holder.getT2().setText(val.getActual());
		holder.getT3().setText(val.getCompra());
		holder.getT4().setText(val.getBeneficio());
		final View fila = convertView;
		return convertView;
	}
}

class Hold {
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;

	public TextView getT1() {
		return t1;
	}

	public void setT1(TextView textViewTitle) {
		this.t1 = textViewTitle;
	}

	public TextView getT2() {
		return t2;
	}

	public void setT2(TextView textViewSubtitle) {
		this.t2 = textViewSubtitle;
	}

	public TextView getT3() {
		return t3;
	}

	public void setT3(TextView textViewSubtitle) {
		this.t3 = textViewSubtitle;
	}

	public TextView getT4() {
		return t4;
	}

	public void setT4(TextView textViewSubtitle) {
		this.t4 = textViewSubtitle;
	}

}