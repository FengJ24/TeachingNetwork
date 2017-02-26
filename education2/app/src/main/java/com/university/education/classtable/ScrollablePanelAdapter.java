package com.university.education.classtable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.SubjectBean;
import com.university.education.bean.WeekBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kelin on 16-11-18.
 */

public class ScrollablePanelAdapter extends PanelAdapter {
    private static final int TITLE_TYPE = 4;
    private static final int WEEK_TYPE = 0;
    private static final int SUBJECT_TYPE = 1;
    private static final int ITEM_TYPE = 2;

    List<WeekBean> beanArrayList = new ArrayList<>();
    List<ArrayList<SubjectBean>> subjectBeenList = new ArrayList<>();
    List<String> subjectItemList = new ArrayList<>();


    @Override
    public int getRowCount() {
        int size = subjectItemList.size();
        return subjectItemList.size() + 1;
}

    @Override
    public int getColumnCount() {
        int size = beanArrayList.size();
        return subjectItemList.size()+1 ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case WEEK_TYPE:
                //设置星期
                setWeekView(column, (WeekViewHolder) holder);
                break;
            case SUBJECT_TYPE:
                //设置课程
                setSubjectView(row, column, (SubjectViewHolder) holder);
                break;
            case ITEM_TYPE:
                //设置第几节
                setItemView(row, (ItemViewHolder) holder);
                break;
            case TITLE_TYPE:
                //设置标题
                break;
            default:
                setSubjectView(row, column, (SubjectViewHolder) holder);
        }

    }


    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return ITEM_TYPE;
        }
        if (row == 0) {
            return WEEK_TYPE;
        }
        return SUBJECT_TYPE;
    }

    /*设置第几节*/
    private void setItemView(int row, ItemViewHolder holder) {
        holder.item.setText(subjectItemList.get(row - 1));
    }

    /*设置科目*/
    private void setSubjectView(int row, int column, SubjectViewHolder holder) {
        SubjectBean subjectBean = subjectBeenList.get(row - 1).get(column - 1);
        holder.subject.setText(subjectBean.getText().substring(0,5));

    }

    /*设置星期*/
    private void setWeekView(int column, WeekViewHolder holder) {
//        holder.week.setText(beanArrayList.get(column - 1).getText());
        holder.week.setText(subjectItemList.get(column - 1));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case WEEK_TYPE:
                return new WeekViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_week_info, parent, false));
            case ITEM_TYPE:
                return new ItemViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_item_info, parent, false));
            case SUBJECT_TYPE:
                return new SubjectViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_subject_info, parent, false));
            case TITLE_TYPE:
                return new TitleViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.layout_title, parent, false));
            default:
                break;
        }
        return new SubjectViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_subject_info, parent, false));
    }


//    private void setDateView(int pos, DateViewHolder viewHolder) {
//        DateInfo dateInfo = dateInfoList.get(pos - 1);
//        if (dateInfo != null && pos > 0) {
//            viewHolder.dateTextView.setText(dateInfo.getDate());
//            viewHolder.weekTextView.setText(dateInfo.getWeek());
//        }
//    }
//
//    private void setRoomView(int pos, RoomViewHolder viewHolder) {
//        RoomInfo roomInfo = roomInfoList.get(pos - 1);
//        if (roomInfo != null && pos > 0) {
//            viewHolder.roomTypeTextView.setText(roomInfo.getRoomType());
//            viewHolder.roomNameTextView.setText(roomInfo.getRoomName());
//        }
//    }
//
//    private void setOrderView(final int row, final int column, OrderViewHolder viewHolder) {
//        final OrderInfo orderInfo = ordersList.get(row - 1).get(column - 1);
//        if (orderInfo != null) {
//            if (orderInfo.getStatus() == OrderInfo.Status.BLANK) {
//                viewHolder.view.setBackgroundResource(R.drawable.bg_white_gray_stroke);
//                viewHolder.nameTextView.setText("");
//                viewHolder.statusTextView.setText("");
//            } else if (orderInfo.getStatus() == OrderInfo.Status.CHECK_IN) {
//                viewHolder.nameTextView.setText(orderInfo.isBegin() ? orderInfo.getGuestName() : "");
//                viewHolder.statusTextView.setText(orderInfo.isBegin() ? "check in" : "");
//                viewHolder.view.setBackgroundResource(orderInfo.isBegin() ? R.drawable.bg_room_red_begin_with_stroke : R.drawable.bg_room_red_with_stroke);
//            } else if (orderInfo.getStatus() == OrderInfo.Status.REVERSE) {
//                viewHolder.nameTextView.setText(orderInfo.isBegin() ? orderInfo.getGuestName() : "");
//                viewHolder.statusTextView.setText(orderInfo.isBegin() ? "reverse" : "");
//                viewHolder.view.setBackgroundResource(orderInfo.isBegin() ? R.drawable.bg_room_blue_begin_with_stroke : R.mipmap.bg_room_blue_middle);
//            }
//            if (orderInfo.getStatus() != OrderInfo.Status.BLANK) {
//                viewHolder.itemView.setClickable(true);
//                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (orderInfo.isBegin()) {
//                            Toast.makeText(v.getContext(), "name:" + orderInfo.getGuestName(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            int i = 2;
//                            while (column - i >= 0 && ordersList.get(row - 1).get(column - i).getId() == orderInfo.getId()) {
//                                i++;
//                            }
//                            final OrderInfo info = ordersList.get(row - 1).get(column - i + 1);
//                            Toast.makeText(v.getContext(), "name:" + info.getGuestName(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else {
//                viewHolder.itemView.setClickable(false);
//            }
//        }
//    }
//    ArrayList<WeekBean> beanArrayList = new ArrayList<>();
//    ArrayList<SubjectBean> subjectBeenList = new ArrayList<>();
//    ArrayList<String> subjectItemList = new ArrayList<>();


    public void setWeekBeanList(List<WeekBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    public void setSubjectBeanList(List<ArrayList<SubjectBean>> subjectBeenList) {
        this.subjectBeenList = subjectBeenList;
    }

    public void setStringList(List<String> subjectItemList) {
        this.subjectItemList = subjectItemList;
    }

    public static class WeekViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView week;

        public WeekViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.week = (TextView) rootView.findViewById(R.id.week);
        }

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView item;

        public ItemViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.item = (TextView) rootView.findViewById(R.id.item);
        }


    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView subject;

        public SubjectViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.subject = (TextView) rootView.findViewById(R.id.subject);
        }

    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView title;

        public TitleViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.title = (TextView) rootView.findViewById(R.id.title);
        }

    }
}
