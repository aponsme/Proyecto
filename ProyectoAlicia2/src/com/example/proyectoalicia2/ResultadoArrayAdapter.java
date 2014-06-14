package com.example.proyectoalicia2;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ResultadoArrayAdapter extends ArrayAdapter<Valores> {
	private LayoutInflater layoutInflater;

	public ResultadoArrayAdapter(Context context, List<Valores> objects) {
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
			holder.setT5((TextView) convertView.findViewById(R.id.t5));
			holder.setCheckBox((CheckBox) convertView.findViewById(R.id.checkBox));
			convertView.setTag(holder);
		} else {
			holder = (Hold) convertView.getTag();
		}
		
		final Valores val = getItem(position);
		holder.getCheckBox().setTag(val.getEmpresa());
		holder.getCheckBox().setChecked(val.isChecked());
		final View fila = convertView;
		changeBackground(getContext(), fila, val.isChecked());

		holder.getCheckBox().setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton view,
							boolean isChecked) {
						// asegura que se modifica la Row originalmente asociado
						// a este checkbox
						// para evitar que al reciclar la vista se reinicie el
						// row que antes se mostraba en esta
						// fila. Es imprescindible tagear el Row antes de
						// establecer el valor del checkbox
						if (val.getEmpresa().equals(view.getTag().toString())) {
							val.setChecked(isChecked);changeBackground(ResultadoArrayAdapter.this.getContext(), fila,isChecked);
						}
					}
				});
		
		holder.getT1().setText(val.getEmpresa());
		holder.getT2().setText(val.getActual());
		holder.getT3().setText(val.getCompra());
		holder.getT4().setText(val.getPorcentaje());
		holder.getT5().setText(val.getGanancia());
		holder.getT4().setTextColor(Color.GREEN);
		
		
		return convertView;
	}
	/**
	 * Set the background of a row based on the value of its checkbox value.
	 * Checkbox has its own style.
	 */
	@SuppressWarnings("deprecation")
	private void changeBackground(Context context, View row, boolean checked) {
		if (checked) {
			row.setBackgroundDrawable((context.getResources()
					.getDrawable(R.drawable.listview_selector_checked)));
		} else {
			row.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.listview_selector));
		}
	}

}

class Hold {
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	CheckBox checkBox;

	public TextView getT1() {
		return t1;
	}

	public void setT1(TextView arg) {
		this.t1 = arg;
	}

	public TextView getT2() {
		return t2;
	}

	public void setT2(TextView arg) {
		this.t2 = arg;
	}

	public TextView getT3() {
		return t3;
	}

	public void setT3(TextView arg) {
		this.t3 = arg;
	}

	public TextView getT4() {
		return t4;
	}

	public void setT4(TextView arg) {
		this.t4 = arg;
	}
	public TextView getT5() {
		return t5;
	}

	public void setT5(TextView arg) {
		this.t5 = arg;
	}
	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

}