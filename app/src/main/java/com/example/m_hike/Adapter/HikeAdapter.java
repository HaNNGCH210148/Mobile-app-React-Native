    package com.example.m_hike.Adapter;

    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Color;
    import android.net.Uri;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.RecyclerView;

    import com.example.m_hike.Model.DatabaseHelper;
    import com.example.m_hike.Model.HikeList;
    import com.example.m_hike.R;
    import com.example.m_hike.View.ObserActivity;
    import com.example.m_hike.View.WishListActivity;
    import com.example.m_hike.ViewModel.Hike;

    import java.util.List;

    public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.HikeViewHolder> {

        private Context context;
        private List<HikeList> hikeList;
        private OnItemClickListener itemClickListener;
        private OnEditClickListener editClickListener;
        private OnDeleteClickListener deleteClickListener;
        private OnViewClickListener viewClickListener;
        private boolean itemClickWishlist = false;

        private DatabaseHelper db;

        public interface OnItemClickListener { void onItemClick(HikeList hike);}

        public interface OnEditClickListener { void onEditClick(HikeList hike);}

        public interface OnDeleteClickListener { void onDeleteClick(HikeList hike);}

        public interface OnViewClickListener { void onViewClick(HikeList hike);}

        public HikeAdapter(Context context, List<HikeList> hikeList,
                           OnItemClickListener itemClickListener,
                           OnEditClickListener editClickListener,
                           OnDeleteClickListener deleteClickListener,
                           OnViewClickListener viewClickListener,
                           boolean itemClickWishlist) {
            this.context = context;
            this.hikeList = hikeList;
            this.itemClickListener = itemClickListener;
            this.editClickListener = editClickListener;
            this.deleteClickListener = deleteClickListener;
            this.viewClickListener = viewClickListener;
            this.itemClickWishlist = itemClickWishlist;

            db = new DatabaseHelper(context);
        }

        @NonNull
        @Override
        public HikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_hike, parent, false);
            return new HikeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HikeViewHolder holder, int position) {
            HikeList hike = hikeList.get(position);

            holder.tvName.setText(hike.getName());
            holder.tvLocation.setText("📍 " + hike.getLocation());
            holder.tvDate.setText("📅 " + hike.getDate());
            holder.tvLength.setText("⛰️ " + hike.getLength() + " km");
            String parkingText = hike.getParking() == 1 ? "Parking: Available" : "Parking: Not available";
            holder.tvWeather.setText("Weather: " + hike.getWeather());
            holder.tvTrail.setText("Trail: " + hike.getTrail());
            holder.tvParking.setText(parkingText);
            holder.tvDesc.setText("Description: "+hike.getDescription());

            holder.tvDifficulty.setText(hike.getDifficult());

            String diff = hike.getDifficult().trim().toLowerCase();
            switch (diff) {
                case "easy":
                    holder.tvDifficulty.setTextColor(Color.parseColor("#4CAF50"));
                    break;

                case "medium":
                    holder.tvDifficulty.setTextColor(Color.parseColor("#FF9800"));
                    break;

                case "hard":
                    holder.tvDifficulty.setTextColor(Color.parseColor("#F44336"));
                    break;
            }

            if (hike.getWishlist() == 1) {
                holder.imgLike.setImageResource(R.drawable.liked);
            } else {
                holder.imgLike.setImageResource(R.drawable.like);
            }

            holder.imgLike.setOnClickListener(v -> {

                int newState = hike.getWishlist() == 1 ? 0 : 1;

                db.updateWishlist(hike.getId(), newState);
                hike.setWishlist(newState);

                if (newState == 1) {
                    Toast.makeText(context, "Added to wishlist ❤️", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Removed from wishlist 💔", Toast.LENGTH_SHORT).show();
                }

                if(itemClickWishlist && newState == 0){
                    hikeList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, hikeList.size());
                } else {
                    notifyItemChanged(position);
                }
            });


            int count = new DatabaseHelper(context).getObservationCount(hike.getId());
            holder.txtObsCount.setText("Observations: " + count);

            holder.itemView.setOnClickListener(v -> itemClickListener.onItemClick(hike));
            holder.btnEdit.setOnClickListener(v -> editClickListener.onEditClick(hike));
            holder.btnDelete.setOnClickListener(v -> deleteClickListener.onDeleteClick(hike));
            holder.btnViewObser.setOnClickListener(v -> {
                Intent intent = new Intent(context, ObserActivity.class);

                intent.putExtra("HIKE_ID", hike.getId());
                intent.putExtra("HIKE_NAME", hike.getName());
                intent.putExtra("HIKE_LOCATION", hike.getLocation());
                intent.putExtra("HIKE_DATE", hike.getDate());

                context.startActivity(intent);
            });


        }

        @Override
        public int getItemCount() {
            return hikeList.size();
        }

        public static class HikeViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvLocation, tvDate, tvLength, tvDesc, tvDifficulty, txtObsCount;
            ImageView imgLike;
            TextView tvParking,  tvWeather, tvTrail;

            Button btnEdit, btnDelete, btnViewObser;

            public HikeViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvLocation = itemView.findViewById(R.id.tvLocation);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvLength = itemView.findViewById(R.id.tvLength);
                tvParking = itemView.findViewById(R.id.tvParking);
                tvWeather = itemView.findViewById(R.id.tvWeather);
                tvTrail = itemView.findViewById(R.id.tvTrail);
                imgLike = itemView.findViewById(R.id.imgLike);
                tvDesc = itemView.findViewById(R.id.tvDesc);
                tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
                txtObsCount = itemView.findViewById(R.id.txtObsCount);

                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
                btnViewObser = itemView.findViewById(R.id.btnViewObser);
            }

        }
        public void setHikeList(List<HikeList> hikes) {
            this.hikeList = hikes;
            notifyDataSetChanged();
        }


    }
