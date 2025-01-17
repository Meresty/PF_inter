package adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pf_inter.Eliminar;
import com.example.pf_inter.R;

import java.util.List;

import POJO.nota;
import global.info;

public class adaptadorEliminar extends RecyclerView.Adapter<adaptadorEliminar.Mielimina> {
    public Context context;

    public adaptadorEliminar(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public adaptadorEliminar.Mielimina onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(parent.getContext(), R.layout.vista_eliminar, null);
        Mielimina obj = new Mielimina(v);
        return obj;
    }


    @Override
    public void onBindViewHolder(@NonNull adaptadorEliminar.Mielimina mielimina, int i) {
        final int pos = i;

        nota notas = info.lista.get(pos);
        mielimina.titulo.setText(notas.getTitulo());
        mielimina.checkBoxx.setChecked(false);

        mielimina.checkBoxx.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(((CheckBox)view).isChecked()){
                    info.listaEliminar.add(info.lista.get(pos));
                }
                else{
                    info.listaEliminar.remove(info.lista.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size();
    }

    public class Mielimina extends RecyclerView.ViewHolder {
        TextView titulo;
        CheckBox checkBoxx;

        public Mielimina(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txt_rv_eliminar);
            checkBoxx = itemView.findViewById(R.id.ccheckbox);
        }
    }
}
