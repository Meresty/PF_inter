package adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pf_inter.R;

import POJO.nota;
import global.info;

public class adaptadorEliminar extends RecyclerView.Adapter<adaptadorEliminar.Mielimina> {
    public Context context;
    private final String URL_API = "http://10.0.2.2/Eliminar_BD.php";

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
    public void onBindViewHolder(@NonNull Mielimina holder, int position) {
        final int pos = position;

        nota notaActual = info.lista.get(pos);
        holder.titulo.setText(notaActual.getTitulo());
        holder.checkBoxx.setChecked(false);

        holder.checkBoxx.setOnClickListener(new View.OnClickListener() {
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