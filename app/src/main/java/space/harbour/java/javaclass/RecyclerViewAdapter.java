package space.harbour.java.javaclass;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<String> movies;

    public RecyclerViewAdapter(List<String> movies) {
        this.movies = movies;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView movieTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            movieTitle = itemView.findViewById(R.id.movie_list_item);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), movieTitle.getText(), Toast.LENGTH_SHORT).show();
        }

        public void setMovieTitle(String title) {
            movieTitle.setText(title);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.movie_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String movieTitle = movies.get(i);
        myViewHolder.setMovieTitle(movieTitle);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
