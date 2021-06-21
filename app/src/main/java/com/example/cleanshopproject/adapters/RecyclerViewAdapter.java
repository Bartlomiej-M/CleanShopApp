package com.example.cleanshopproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.cleanshopproject.DataAdapter;
import com.example.cleanshopproject.ImageAdapter;
import com.example.cleanshopproject.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    Context context;
    List<DataAdapter> dataAdapters;
    ImageLoader imageLoader;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewproduct, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);

        imageLoader = ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);

        Viewholder.ImageTitleTextView.setText(dataAdapterOBJ.getImageTitle());
        Viewholder.ImagePriceTextView.setText(dataAdapterOBJ.getImagePrice());
        Viewholder.ImageDescTextView.setText(dataAdapterOBJ.getImageDesc());
        Viewholder.ImageCategoryTextView.setText(dataAdapterOBJ.getImageCategory());
        Viewholder.ImageDescriptionTextView.setText(dataAdapterOBJ.getImageCategory());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public Button button4;
        public TextView ImageTitleTextView;
        public TextView ImagePriceTextView;
        public TextView ImageDescTextView;
        public TextView ImageCategoryTextView;
        public TextView ImageDescriptionTextView;
        public NetworkImageView VollyImageView ;

        public ViewHolder(View itemView) {

            super(itemView);
            ImageTitleTextView = (TextView) itemView.findViewById(R.id.imageTitle) ;
            ImagePriceTextView = (TextView) itemView.findViewById(R.id.imagePrice) ;
            ImageDescTextView = (TextView) itemView.findViewById(R.id.imageRating) ;
            ImageCategoryTextView = (TextView) itemView.findViewById(R.id.imageCategory);
            ImageDescriptionTextView = (TextView) itemView.findViewById(R.id.imageCategory);

            button4 = (Button) itemView.findViewById(R.id.button4);
            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

        }
    }
}