package com.example.samar.firebaseintro.DATA;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samar.firebaseintro.ACTIVITIES.LinkListActivity;
import com.example.samar.firebaseintro.ACTIVITIES.MainActivity;
import com.example.samar.firebaseintro.MODEL.Link;
import com.example.samar.firebaseintro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class LinkRecyclerAdapter extends RecyclerView.Adapter<LinkRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Link> linkList;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference ref;


    public LinkRecyclerAdapter(Context context, List<Link> linkList) {
        this.context = context;
        this.linkList = linkList;
    }

    public LinkRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.link_card,viewGroup,false);

        return new LinkRecyclerAdapter.ViewHolder(view,context);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Link  link = linkList.get(position);
        String LinkValue = null;
        String LinkAddnl;
        final String desc=link.getLinkdesc();
        viewHolder.Link_Desc.setText(desc);
        LinkValue = link.getLinkvalue();
        LinkAddnl = link.getLinkaddnl();
        final String finalLinkValue = LinkValue;
        final String finalLinkAddnl = LinkAddnl.toLowerCase();

        if(finalLinkAddnl.matches("link")) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {// Toast.makeText(v.getContext(), finalBranchCode,Toast.LENGTH_LONG).show();


                    storageReference = firebaseStorage.getInstance().getReference();
                    ref = storageReference.child(finalLinkValue);

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            downloadFile(context, finalLinkValue, DIRECTORY_DOWNLOADS, url);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });

                    Toast.makeText(context, "File Downloading!!", Toast.LENGTH_LONG).show();


                }
            });
        }

             else if(finalLinkAddnl.matches("not")){

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//

                        Intent intent = new Intent("android.intent.action.VIEW",
                                Uri.parse(finalLinkValue));
                        context.startActivity(intent);
                    }
                });
            }




    }


    @Override
    public int getItemCount() {
        return linkList.size();
    }



    //Viewholder Class

    public class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView Link_Desc;
        public ImageView Link_but;
        public ViewHolder(final View view,Context ctx) {
            super(view);

            context=ctx;

            Link_Desc=(TextView)view.findViewById(R.id.link_text);

            Link_but=(ImageView)view.findViewById(R.id.cardview_image);



        }



    }



    public void downloadFile(Context context, String fileName, String destinationDirectory, String url) {


        DownloadManager downloadmanager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName);

        downloadmanager.enqueue(request);
    }



}
