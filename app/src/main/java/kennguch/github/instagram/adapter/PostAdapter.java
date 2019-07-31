package kennguch.github.instagram.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import kennguch.github.instagram.R;
import kennguch.github.instagram.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private FirebaseAuth mAuth;
      FirebaseUser mUser;
     DatabaseReference likesRef, usersRef;
    private String current_user_id;
    private int countLikes;
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

        mAuth = FirebaseAuth.getInstance();
        current_user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        likesRef = FirebaseDatabase.getInstance().getReference().child("likes");

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder holder, int position) {
        final Post post = mPosts.get(position);

        holder.mUsername.setText(post.getUsername());
        holder.mAuthor.setText(post.getUsername());
        holder.mDescription.setText(post.getDescription());

        Glide.with(mContext).load(post.getUserImage()).into(holder.mUserProfile);

        Picasso.get()
                .load(post.getImageUrl())
                .into(holder.mImageView);

        holder.likeStatus(post.getPostId());

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        TextView mUsername, mAuthor, mDescription, mLikesCount;
        ImageView mImageView, mLike, mUserProfile;
        Boolean likeChecker = false;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            mUsername = itemView.findViewById(R.id.post_username);
            mAuthor = itemView.findViewById(R.id.postUsername);
            mDescription = itemView.findViewById(R.id.postDescription);
            mImageView = itemView.findViewById(R.id.postImage);
            mLike = itemView.findViewById(R.id.post_like);
            mLikesCount = itemView.findViewById(R.id.post_likes_count);
            mUserProfile = itemView.findViewById(R.id.post_user_profile);

            mLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final String postId = mPosts.get(position).getPostId();

                    likeChecker = true;

                    likesRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (likeChecker.equals(true)) {
                                if (dataSnapshot.child(postId).hasChild(current_user_id)) {
                                    likesRef.child(postId).child(current_user_id).removeValue();
                                    likeChecker = false;
                                } else {
                                    likesRef.child(postId).child(current_user_id).setValue(true);
                                    likeChecker = false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

        }

        void likeStatus(final String postId) {
            likesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(postId).hasChild(current_user_id)) {
                        countLikes = (int) dataSnapshot.child(postId).getChildrenCount();
                        mLike.setImageResource(R.drawable.ic_like);
                        if (countLikes == 1) {
                            mLikesCount.setText((countLikes) + " like");
                        } else {
                            mLikesCount.setText((countLikes) + " likes");
                        }
                    } else {
                        countLikes = (int) dataSnapshot.child(postId).getChildrenCount();
                        mLike.setImageResource(R.drawable.ic_fav);
                        if (countLikes == 1) {
                            mLikesCount.setText((countLikes) + " like");
                        } else {
                            mLikesCount.setText((countLikes) + " likes");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}