package com.fullshare.basebusiness.data;

import com.common.basecomponent.util.FileUtil;
import com.common.basecomponent.util.L;
import com.fullshare.basebusiness.net.ParameterizeTypeImpl;
import com.fullshare.basebusiness.util.GsonHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * author: chenweibin
 * date: 2017/7/20
 */

public class HistoryManagerBase<T> {
    private int mMaxSize;
    private String mDataFile;
    private SoftReference<List<T>> mDataList;
    private boolean repeatable;

    public HistoryManagerBase(String dataFile, int maxSize) {
        this.mDataFile = dataFile;
        this.mMaxSize = maxSize;
    }

    public synchronized List<T> loadHistories() {
        if (mDataList == null || mDataList.get() == null) {
            List<T> datas = null;
            try {
                String json = FileUtil.openStringFile(mDataFile);
                Gson gson = GsonHelper.getGson();
                ParameterizeTypeImpl type = ParameterizeTypeImpl.get(ArrayList.class, getSuperclassTypeParameter());
                datas = gson.fromJson(json, type);
            } catch (IOException e) {
            }
            if (datas == null) {
                datas = new ArrayList<>();
            }

            mDataList = new SoftReference<>(datas);
        }
        return mDataList.get();
    }

    private Type getSuperclassTypeParameter() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterize = (ParameterizedType) superclass;
        return parameterize.getActualTypeArguments()[0];
    }

    private synchronized void persistData(List<T> histories) {
        Gson gson = GsonHelper.getGson();
        String data = gson.toJson(histories);
        try {
            FileUtil.saveStringFile(mDataFile, data);
        } catch (IOException e) {
            L.e(e, "persistData error");
        }
    }

    private void trimSize(List<T> histories) {
        for (int i = histories.size() - 1; i >= mMaxSize; i--) {
            histories.remove(i);
        }
    }

    public void addData(T value) {
        List<T> histories = loadHistories();
        if (!repeatable && histories.contains(value)) {
            histories.remove(value);
        }
        histories.add(0, value);
        trimSize(histories);
        persistData(histories);
    }

    public void remove(int position) {
        List<T> histories = loadHistories();
        if (position < histories.size()) {
            histories.remove(position);
            persistData(histories);
        }
    }

    public void clearData() {
        List<T> histories = loadHistories();
        mDataList.clear();
        if (histories != null && histories.size() > 0) {
            histories.clear();
            persistData(histories);
        }
    }

    public void setMaxSize(int mMaxSize) {
        this.mMaxSize = mMaxSize;
    }

    protected void setRepeatable(boolean repeatable) {
        this.repeatable = repeatable;
    }
}
