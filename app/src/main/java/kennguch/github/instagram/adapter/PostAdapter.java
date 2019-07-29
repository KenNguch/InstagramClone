package kennguch.github.instagram.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import kennguch.github.instagram.R;
import kennguch.github.instagram.models.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mContext;
    private List<Post> mPosts;

    public PostAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.post_list, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPosts.get(position);

        holder.mUsername.setText(post.getUsername());
        holder.mAuthor.setText(post.getUsername());
        holder.mDescription.setText(post.getDescription());
        Picasso.get()
                .load(post.getImageUrl())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mUsername, mAuthor, mDescription;
        ImageView mImageView;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.post_username);
            mAuthor = itemView.findViewById(R.id.postUsername);
            mDescription = itemView.findViewById(R.id.postDescription);
            mImageView = itemView.findViewById(R.id.postImage);

        }


    }

}