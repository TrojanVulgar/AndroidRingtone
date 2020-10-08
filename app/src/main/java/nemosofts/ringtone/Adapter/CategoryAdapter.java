package nemosofts.ringtone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nemosofts.ringtone.Activity.CategoriesActivity;
import nemosofts.ringtone.item.ListltemCategory;
import nemosofts.ringtone.R;


/**
 * Created by Thivakaran
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<ListltemCategory> listltems;
    private Context context;

    public CategoryAdapter(List<ListltemCategory> listltems, Context context) {
        this.listltems = listltems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListltemCategory listltem = listltems.get(position);

        holder.name.setText(listltem.getCategory_name());

        //load album cover using picasso
        Picasso.get()
                .load(listltem.getCategory_image_thumb())
                .placeholder(R.color.colorAccent)
                .into(holder.imageView);

        int step = 1;
        int final_step = 1;
        for (int i = 1; i < position + 1; i++) {
            if (i == position + 1) {
                final_step = step;
            }
            step++;
            if (step > 7) {
                step = 1;
            }
        }

        switch (step) {
            case 1:
                Picasso.get()
                        .load(R.drawable.gradient_slide_1)
                        .placeholder(R.drawable.gradient_slide_1)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_1)
                        .placeholder(R.drawable.ic_thiva_1)
                        .into(holder.logo);
                break;
            case 2:
                Picasso.get()
                        .load(R.drawable.gradient_slide_2)
                        .placeholder(R.drawable.gradient_slide_2)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_2)
                        .placeholder(R.drawable.ic_thiva_2)
                        .into(holder.logo);
                break;
            case 3:
                Picasso.get()
                        .load(R.drawable.gradient_slide_3)
                        .placeholder(R.drawable.gradient_slide_3)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_3)
                        .placeholder(R.drawable.ic_thiva_3)
                        .into(holder.logo);
                break;
            case 4:
                Picasso.get()
                        .load(R.drawable.gradient_slide_4)
                        .placeholder(R.drawable.gradient_slide_4)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_4)
                        .placeholder(R.drawable.ic_thiva_4)
                        .into(holder.logo);
                break;
            case 5:
                Picasso.get()
                        .load(R.drawable.gradient_slide_5)
                        .placeholder(R.drawable.gradient_slide_5)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_5)
                        .placeholder(R.drawable.ic_thiva_5)
                        .into(holder.logo);
                break;
            case 6:
                Picasso.get()
                        .load(R.drawable.gradient_slide_6)
                        .placeholder(R.drawable.gradient_slide_6)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_6)
                        .placeholder(R.drawable.ic_thiva_6)
                        .into(holder.logo);
                break;
            case 7:
                Picasso.get()
                        .load(R.drawable.gradient_slide_7)
                        .placeholder(R.drawable.gradient_slide_7)
                        .into(holder.thiva);

                Picasso.get()
                        .load(R.drawable.ic_thiva_7)
                        .placeholder(R.drawable.ic_thiva_7)
                        .into(holder.logo);
                break;
        }




    }

    @Override
    public int getItemCount() {
        return listltems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public ImageView imageView, thiva, logo;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            imageView = (ImageView) itemView.findViewById(R.id.image);

            thiva = itemView.findViewById(R.id.thiva);

            logo = itemView.findViewById(R.id.logo);

            //on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, CategoriesActivity.class);
                        intent.putExtra("name", listltems.get(pos).getCategory_name());
                        intent.putExtra("cid", listltems.get(pos).getCid());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });



        }
    }



}
