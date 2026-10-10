package com.manojperla.deviceharbor.demo;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout layout=new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL); layout.setPadding(32,48,32,32);
        TextView heading=new TextView(this); heading.setText("DeviceHarbor • Tasks"); heading.setTextSize(24); layout.addView(heading);
        EditText input=new EditText(this); input.setSingleLine(true); input.setHint("Task title"); input.setContentDescription("task-input"); layout.addView(input);
        Button add=new Button(this); add.setText("Add task"); add.setContentDescription("add-task"); layout.addView(add);
        TextView error=new TextView(this); error.setContentDescription("validation-error"); layout.addView(error);
        LinearLayout task=new LinearLayout(this); task.setOrientation(LinearLayout.VERTICAL); layout.addView(task);
        add.setOnClickListener(v->{
            String value=input.getText().toString().trim();
            if(value.isEmpty()) { error.setText("Enter a task title"); return; }
            error.setText(""); task.removeAllViews();
            TextView title=new TextView(this); title.setText(value); title.setContentDescription("task-title"); task.addView(title);
            TextView status=new TextView(this); status.setText("Pending"); status.setContentDescription("task-status"); task.addView(status);
            Button complete=new Button(this); complete.setText("Complete"); complete.setContentDescription("complete-task"); task.addView(complete);
            complete.setOnClickListener(w->status.setText("Completed"));
            Button delete=new Button(this); delete.setText("Delete"); delete.setContentDescription("delete-task"); task.addView(delete);
            delete.setOnClickListener(w->task.removeAllViews()); input.setText("");
        });
        setContentView(layout);
    }
}
