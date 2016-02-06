package androidfanatic.lennyfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaceAdapter extends RecyclerView.Adapter<FaceAdapter.FaceHolder> {

    List<String> faces = new ArrayList<>();

    @Override public FaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_face, null);
        return new FaceHolder(view);
    }

    @Override public void onBindViewHolder(FaceHolder holder, int position) {
        holder.faceTextView.setText(Html.fromHtml(faces.get(position)));
    }

    @Override public int getItemCount() {
        return faces.size();
    }

    public void setFaces(List<String> faces) {
        clearFaces();
        for (int i = 0; i < faces.size(); i++) {
            this.faces.add(faces.get(i));
        }
        notifyItemRangeInserted(0, faces.size());
    }

    private void clearFaces() {
        int size = this.faces.size();
        if (size > 0) {
            this.faces.clear();
            this.notifyItemRangeRemoved(0, size);
        }
    }

    public class FaceHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textview_face) TextView faceTextView;
        private Toast toast;

        public FaceHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.textview_face) public void faceTextClick(View view) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text", faceTextView.getText());
            clipboard.setPrimaryClip(clip);
            msg(view.getContext(), "Copied!");
        }

        private void msg(Context context, String msg) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            toast.show();
        }


    }
}
