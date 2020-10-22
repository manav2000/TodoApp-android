package Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.TaskList;

import java.util.List;

import DataHandler.DatabaseHandler;
import Model.Task;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<Task> tasklist;
    private AlertDialog.Builder alertDialog;

    public MyAdapter(Context context, List<Task> tasklist) {
        this.context = context;
        this.tasklist = tasklist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Task task = tasklist.get(position);

        holder.id.setText(String.valueOf(task.getId()));
        holder.task.setText(task.getTask());
        holder.description.setText(task.getDescription());
        holder.date_time.setText(task.getDate() + "  " + task.getTime());
        holder.priority.setText(task.getPriority());
        holder.editButton.setImageResource(android.R.drawable.ic_menu_edit);
        holder.delButton.setImageResource(android.R.drawable.ic_delete);
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView id;
        public TextView task;
        public TextView description;
        public TextView date_time;
        public TextView priority;
        public ImageButton editButton;
        public ImageButton delButton;

        public AlertDialog.Builder alertDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.task_id);
            task = (TextView) itemView.findViewById(R.id.task);
            description = (TextView) itemView.findViewById(R.id.task_desc);
            date_time = (TextView) itemView.findViewById(R.id.date_time);
            priority = (TextView) itemView.findViewById(R.id.task_priority);
            editButton = (ImageButton) itemView.findViewById(R.id.editBtn);
            delButton = (ImageButton) itemView.findViewById(R.id.delBtn);

            editButton.setOnClickListener(this);
            delButton.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Task task = tasklist.get(position);
                    Toast.makeText(context,
                             "ID: " + task.getId() + " TASK: " + task.getTask() + " PRIORITY: "
                                     + task.getPriority(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editBtn:
                    int position = getAdapterPosition();
                    Task task = tasklist.get(position);
                    editTask(task);
                    break;
                case R.id.delBtn:
                    position = getAdapterPosition();
                    task = tasklist.get(position);
                    deleteTask(task.getId());
                    break;
            }
        }

        public void editTask(final Task task) {
            //create alert dialog with custom styles
            alertDialog = new AlertDialog.Builder(
                    new ContextThemeWrapper(context, R.style.AlertDialogCustom));

            //create custom dialog view
            final View updateView = LayoutInflater.from(context)
                    .inflate(R.layout.edit_task_dialog, null);

            alertDialog.setTitle(R.string.updateTitle);

            final EditText up_task = (EditText) updateView.findViewById(R.id.updateTask);
            final EditText up_desc = (EditText) updateView.findViewById(R.id.updateDesc);

            alertDialog.setPositiveButton(R.string.updatePos,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHandler db = new DatabaseHandler(context);

                            task.setTask(up_task.getText().toString());
                            task.setDescription((up_desc.getText().toString().isEmpty())
                                    ?"No Description":up_desc.getText().toString());

                            if (!up_task.getText().toString().isEmpty()
                                    && !up_task.getText().toString().isEmpty()) {
                                db.updateTask(task);
                                //notifies the adapter that the task is updated and to change
                                //itself accordingly
                                notifyItemChanged(getAdapterPosition(), task);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context,
                                        "enter task and description to update the item",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            alertDialog.setNegativeButton(R.string.updateNeg,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = alertDialog.create();
            //apply custom dialog view
            dialog.setView(updateView);
            dialog.show();

        }

        public void deleteTask(final int id) {
            //create alert dialog with custom styles
            alertDialog = new AlertDialog.Builder(
                    new ContextThemeWrapper(context, R.style.AlertDialogCustom));

            alertDialog.setTitle(R.string.delOneTitle);
            alertDialog.setMessage(R.string.delOneMsg);
            alertDialog.setCancelable(false);

            alertDialog.setPositiveButton(R.string.alertPos,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHandler db = new DatabaseHandler(context);

                            db.delTask(id);
                            tasklist.remove(getAdapterPosition());
                            //notifies the adapter that the task is deleted and to change
                            //itself accordingly
                            notifyItemRemoved(getAdapterPosition());
                            dialog.dismiss();

                            Toast.makeText(context,
                                    "You have successfully deleted task " + String.valueOf(id),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

            alertDialog.setNegativeButton(R.string.alertNeg,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }

    }
}
