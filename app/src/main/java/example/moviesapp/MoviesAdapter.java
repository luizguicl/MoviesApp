package example.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MoviesAdapter extends ArrayAdapter<Movie> {


    public MoviesAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView poster = (ImageView) convertView.findViewById(R.id.poster_image);
        //TODO: use picasso

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(movie.getTitle());

        TextView voteAverage = (TextView) convertView.findViewById(R.id.vote_average);
        voteAverage.setText(String.format(new Locale("pt_BR", "BR"), "%.2f", movie.getVoteAverage()));

        TextView releaseDate = (TextView) convertView.findViewById(R.id.release_date);
        releaseDate.setText(movie.getReleaseDate());

        return convertView;
    }
}
