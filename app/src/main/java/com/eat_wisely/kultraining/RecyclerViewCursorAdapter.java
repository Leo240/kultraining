package com.eat_wisely.kultraining;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

/**
 *
 */

public abstract class RecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private Cursor mCursor;
    private Context mContext;
    private boolean isValid;
    private int idColumn;
    private DataSetObserver dataSetObserver;

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    public RecyclerViewCursorAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        isValid = (cursor != null);
        idColumn = isValid ? mCursor.getColumnIndex("_id") : -1;
        dataSetObserver = new NotifyingDataSetObserver();
        setHasStableIds(true);

        if (mCursor != null) {
            mCursor.registerDataSetObserver(dataSetObserver);
        }
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (isValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (isValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(idColumn);
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
        if (!isValid) {
            throw new IllegalStateException("Cursor is not valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Couldn't move cursor to position " + position);
        }

        onBindViewHolder(viewHolder, mCursor);
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;

        if (oldCursor != null && dataSetObserver != null) {
            oldCursor.unregisterDataSetObserver(dataSetObserver);
        }
        mCursor = newCursor;
        if (mCursor != null) {
            if (dataSetObserver != null) {
                mCursor.registerDataSetObserver(dataSetObserver);
            }
            idColumn = mCursor.getColumnIndexOrThrow("_id");
            isValid = true;
            notifyDataSetChanged();
        } else {
            idColumn = -1;
            isValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            isValid = true;
            notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            isValid = false;
            notifyDataSetChanged();
        }
    }
}
