package com.example.joshua.derivenav.Destinations;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.joshua.derivenav.R;

import android.widget.CheckBox;

import java.util.HashSet;
import java.util.Set;

import android.widget.CompoundButton;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class DestinationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private String mHeaderTitle;

    private OnHeaderClickListener mHeaderClickListener;

    private Context mContext;
    private ArrayList<DestinationModel> modelList;

    private OnItemClickListener mItemClickListener;

    private OnCheckedListener mOnCheckedListener;


    private Set<Integer> checkSet = new HashSet<>();


    public DestinationAdapter(Context context, ArrayList<DestinationModel> modelList, String headerTitle) {
        this.mContext = context;
        this.modelList = modelList;
        this.mHeaderTitle = headerTitle;
    }

    public void updateList(ArrayList<DestinationModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
            return new HeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_list, parent, false);
            return new ViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.txtTitleHeader.setText("List of Destinations");

        } else if (holder instanceof ViewHolder) {
            final DestinationModel model = getItem(position - 1);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getMessage());


            //in some cases, it will prevent unwanted situations
            genericViewHolder.itemCheckList.setOnCheckedChangeListener(null);

            //if true, your checkbox will be selected, else unselected
            genericViewHolder.itemCheckList.setChecked(checkSet.contains(position));

            genericViewHolder.itemCheckList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        checkSet.add(position);
                    } else {
                        checkSet.remove(position);
                    }

                    mOnCheckedListener.onChecked(buttonView, isChecked, position, model);

                }
            });


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    @Override
    public int getItemCount() {

        return modelList.size() + 1;
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void SetOnHeaderClickListener(final OnHeaderClickListener headerClickListener) {
        this.mHeaderClickListener = headerClickListener;
    }

    public void SetOnCheckedListener(final OnCheckedListener onCheckedListener) {
        this.mOnCheckedListener = onCheckedListener;

    }

    private DestinationModel getItem(int position) {
        return modelList.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, DestinationModel model);
    }


    public interface OnHeaderClickListener {
        void onHeaderClick(View view, String headerTitle);
    }

    public interface OnCheckedListener {
        void onChecked(View view, boolean isChecked, int position, DestinationModel model);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;

        public HeaderViewHolder(final View itemView) {
            super(itemView);
            this.txtTitleHeader = (TextView) itemView.findViewById(R.id.txt_header);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mHeaderClickListener.onHeaderClick(itemView, mHeaderTitle);
                }
            });

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;


        private CheckBox itemCheckList;

        // @BindView(R.id.img_user)
        // ImageView imgUser;
        // @BindView(R.id.item_txt_title)
        // TextView itemTxtTitle;
        // @BindView(R.id.item_txt_message)
        // TextView itemTxtMessage;
        // @BindView(R.id.radio_list)
        // RadioButton itemTxtMessage;
        // @BindView(R.id.check_list)
        // CheckBox itemCheckList;
        public ViewHolder(final View itemView) {
            super(itemView);

            // ButterKnife.bind(this, itemView);

            this.imgUser = (ImageView) itemView.findViewById(R.id.img_user);
            this.itemTxtTitle = (TextView) itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage = (TextView) itemView.findViewById(R.id.item_txt_message);
            this.itemCheckList = (CheckBox) itemView.findViewById(R.id.check_list);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition() - 1));


                }
            });

        }
    }

}

