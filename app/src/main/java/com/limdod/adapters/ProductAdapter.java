package com.limdod.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.limdod.BuyActivity;
import com.limdod.R;
import com.limdod.databinding.LiProductBinding;
import com.limdod.models.Product;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>
{
    private Context context;
    private ArrayList<Product> products;
    private String type;

    public ProductAdapter(Context context, ArrayList<Product> products, String type)
    {
        this.context = context;
        this.products = products;
        this.type = type;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LiProductBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.li_product, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position)
    {
        Glide.with(context)
                .load(products.get(holder.getAdapterPosition()).getImageUrl())
                .into(holder.binding.ivProduct);

        if(type.equals("home") || type.equals("orders") )
        {
            holder.binding.tvPrice.setText("$" + products.get(holder.getAdapterPosition()).getPrice());
            holder.binding.tvTitle.setText(products.get(holder.getAdapterPosition()).getName());
        }
        else
        {
            holder.binding.tvTitle.setVisibility(View.GONE);
            holder.binding.tvPrice.setText(products.get(holder.getAdapterPosition()).getStatus().toUpperCase(Locale.ROOT));
        }


        holder.binding.llUser.setVisibility(View.GONE);



        if(type.equals("home"))
        {
            holder.binding.llUser.setVisibility(View.VISIBLE);

            holder.binding.tvUsername.setText(products.get(holder.getAdapterPosition()).getPostedBy().getName());

            String[] initials = products.get(holder.getAdapterPosition())
                    .getPostedBy().getName().split(" ");
            String initialToDisplay = "";

            if(initials.length > 1)
            {
                initialToDisplay = initials[0].charAt(0) + "" + initials[1].charAt(0);
            }
            else
            {
                initialToDisplay = String.valueOf(initials[0].charAt(0));
            }

            holder.binding.tvInitials.setText(initialToDisplay.toUpperCase(Locale.ROOT));


            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, BuyActivity.class);
                    intent.putExtra("product", products.get(holder.getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount()
    {
        return products.size();
    }
}

class ProductViewHolder extends RecyclerView.ViewHolder
{
    LiProductBinding binding;

    public ProductViewHolder(@NonNull LiProductBinding binding)
    {
        super(binding.getRoot());
        this.binding = binding;
    }
}
