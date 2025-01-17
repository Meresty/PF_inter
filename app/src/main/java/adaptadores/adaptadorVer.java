package adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pf_inter.R;
import com.example.pf_inter.cardview;

import java.util.List;

import POJO.nota;
import global.info;

public class adaptadorVer extends RecyclerView.Adapter<adaptadorVer.Miactivity> {
    public Context context;
    private List<nota> listaNotas;

    public adaptadorVer(Context context) {
        this.context = context;
        this.listaNotas = listaNotas;
    }

    @NonNull
    @Override
    public adaptadorVer.Miactivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(context, R.layout.mi_vista, null);
        Miactivity obj = new Miactivity(v);
        return obj;
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorVer.Miactivity miactivity, int i) {
        final int pos = i;
        miactivity.titulo.setText(info.lista.get(i).getTitulo());
        miactivity.titulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car = new Intent(context, cardview.class);
                car.putExtra("posic", pos);
                context.startActivity(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.lista.size();
    }

    public class Miactivity extends RecyclerView.ViewHolder {
        TextView titulo;
        public Miactivity(@NonNull View itemView) {
            super(itemView);

           titulo =itemView.findViewById(R.id.txt_rv_lista);
        }
    }
}
