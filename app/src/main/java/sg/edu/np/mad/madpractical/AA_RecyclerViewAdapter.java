package sg.edu.np.mad.madpractical;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class AA_RecyclerViewAdapter extends RecyclerView.Adapter<AA_RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<User> userModels;
    private OnUserClickListener onUserClickListener;

    public AA_RecyclerViewAdapter(Context context, ArrayList<User> userModels, OnUserClickListener onUserClickListener) {
        this.context = context;
        this.userModels = userModels;
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.recycler_view_row_2, parent, false);
        } else {
            view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int Position=position;
        User user = userModels.get(position);
        holder.textView.setText(user.getMsg());
        holder.textView1.setText(user.getDesc());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = Position;
                int userClickedID = (userModels.get(clickedPosition)).getID();
                Toast.makeText(context, "id"+userClickedID, Toast.LENGTH_LONG).show();
                User userClicked=userModels.get(clickedPosition);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(userClicked.getMsg());
                builder.setMessage(userClicked.getDesc());
                builder.setCancelable(false);
                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        intent.putExtra("userClickedID", userClickedID);
                        ((Activity) context).startActivityForResult(intent, 1);
                    }
                });
                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    public int getItemViewType(int position) {
        String message = userModels.get(position).getMsg();
        int lastDigit = Integer.parseInt(message.substring(message.length() - 1));
        if (lastDigit == 7) {
            return 1; // View type for special layout
        } else {
            return 0; // View type for default layout
        }
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView imageView;
        TextView textView, textView1;
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView4);
            textView1 = itemView.findViewById(R.id.textView2);
            cardview = itemView.findViewById(R.id.list_card_view);

        }



    }

    public interface OnUserClickListener {
        void onUserClick(int position);
    }
}
