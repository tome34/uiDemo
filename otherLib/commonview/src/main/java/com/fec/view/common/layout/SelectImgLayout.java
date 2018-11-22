package com.fec.view.common.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.fec.view.common.R;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 选图布局
 *
 * @author XQ Yang
 * @date 2017/12/6  13:53
 */
public class SelectImgLayout extends LinearLayout {

    private   int mMaxSize = 4;
    private RecyclerView mRecycleView;
    private SelectImgAdapter mAdapter;
    private OnImageSelectedListener mSelectedListener;
    public static int sCurSelectPos = 0;
    private LocalMedia mAdd;

    private Activity mActivity;
    private Fragment mFragment;

    private int imageHeight = Integer.MIN_VALUE;

    @StyleRes
    private int mStyle;

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public OnImageSelectedListener getSelectedListener() {
        return mSelectedListener;
    }

    public void setSelectedListener(OnImageSelectedListener selectedListener) {
        mSelectedListener = selectedListener;
    }

    public SelectImgLayout(Context context) {
        this(context, null);
    }

    public SelectImgLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SelectImgLayout);
        mStyle = array.getResourceId(R.styleable.SelectImgLayout_selector_style, -1);
        mMaxSize = array.getInt(R.styleable.SelectImgLayout_maxCount, mMaxSize);
        imageHeight = array.getDimensionPixelSize(R.styleable.SelectImgLayout_imageLayoutHeight, imageHeight);
        array.recycle();
        LayoutInflater.from(context).inflate(R.layout.layout_select_img, this, true);
        mRecycleView = findViewById(R.id.select_img_rv);
        ArrayList<LocalMedia> mList = new ArrayList<>(mMaxSize);
        mAdd = new LocalMedia();
        mAdd.setNum(Integer.MIN_VALUE);
        mList.add(mAdd);
        mAdapter = new SelectImgAdapter(context);
        mAdapter.replaceDatas(mList);
        mRecycleView.setAdapter(mAdapter);
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect,View view,RecyclerView parent,RecyclerView.State state) {
                outRect.right = 5;
            }
        });
        mRecycleView.setLayoutManager(layout);

    }



    public List<String> getSelectedPath() {
        List<String> list = new ArrayList<>();
        if (mAdapter.list.size() > 1) {
            list = new ArrayList<>(mMaxSize);
            for (int i = 0; i < mAdapter.list.size(); i++) {
                LocalMedia media = mAdapter.list.get(i);
                if (media.getNum() != Integer.MIN_VALUE) {
                    if (media.isCompressed()) {
                        list.add(media.getCompressPath());
                    } else if (media.isCut()) {
                        list.add(media.getCutPath());
                    }else{
                        list.add(media.getPath());
                    }
                }
            }
        }
        return list;
    }

    public void recycle() {
        //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
        PictureFileUtils.deleteCacheDirFile(getContext());
    }

    public void onRequestResult(Intent data) {
        // 图片选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        // 例如 LocalMedia 里面返回三种path
        // 1.media.getPath(); 为原图path
        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
        // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
        if (selectList != null && selectList.size() > 0) {
            mAdapter.replaceDatas(selectList);
            if (mAdapter.list.size() < mMaxSize) {
                mAdapter.list.add(mAdd);
            }
            mAdapter.notifyDataSetChanged();
            if (mSelectedListener != null) {
                mSelectedListener.onResult(mAdapter.list);
            }
        }
    }

    public void setMaxSelectedCount(int count) {
        mMaxSize = count;
    }

    public class SelectImgAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<LocalMedia> list;
        private Context mContext;

        public SelectImgAdapter(Context context) {
            mContext = context;
        }



        protected void onItemClick(RecyclerView.ViewHolder vh, View view, LocalMedia item, int position) {
            int addIndex = mAdapter.list.indexOf(mAdd);
            if (item.getNum() == Integer.MIN_VALUE) {
                // 进入相册 以下是例子：用不到的api可以不写
                List<LocalMedia> selectionMedia = null;
                if (addIndex > 0) {
                    selectionMedia = mAdapter.list.subList(0,mAdapter.list.size() - 1);
                } else if (addIndex < 0) {
                    selectionMedia = mAdapter.list;
                }
                PictureSelector pictureSelector;
                if (mActivity == null) {
                    pictureSelector = PictureSelector.create(mFragment);
                } else {
                    pictureSelector =  PictureSelector.create(mActivity);
                }
                //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                PictureSelectionModel pictureSelectionModel = pictureSelector.openGallery(PictureMimeType.ofImage());
                if (mStyle != -1) {
                    pictureSelectionModel.theme(mStyle);//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                }
                pictureSelectionModel.maxSelectNum(mMaxSize)// 最大图片选择数量 int
                               .minSelectNum(0)// 最小选择数量 int
                               .imageSpanCount(4)// 每行显示个数 int
                               .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                               .previewImage(true)// 是否可预览图片 true or false
                               .previewVideo(false)// 是否可预览视频 true or false
                               .enablePreviewAudio(false) // 是否可播放音频 true or false
                               .isCamera(true)// 是否显示拍照按钮 true or false
                               .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                               .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                               .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                               .enableCrop(false)// 是否裁剪 true or false
                               .compress(true)// 是否压缩 true or false
                               .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                               .isGif(false)// 是否显示gif图片 true or false
                               .openClickSound(false)// 是否开启点击声音 true or false
                               .selectionMedia(selectionMedia)// 是否传入已选图片 List<LocalMedia> list
                               .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                               .minimumCompressSize(100)// 小于100kb的图片不压缩
                               .synOrAsy(true)//同步true或异步false 压缩 默认同步
                               .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
                if (mSelectedListener != null) {
                    sCurSelectPos = mSelectedListener.getPos();
                }
            } else {
                mAdapter.list.remove(item);
                if (addIndex < 0) {
                    mAdapter.list.add(mAdd);
                }
                mAdapter.notifyDataSetChanged();
                if (mSelectedListener != null) {
                    mSelectedListener.onResult(mAdapter.list);
                }
            }
        }

        @SuppressWarnings("SuspiciousNameCombination")
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_select_img, parent, false);
            if (imageHeight != Integer.MIN_VALUE) {
                ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LinearLayout.LayoutParams(imageHeight, imageHeight);
                } else {
                    layoutParams.width = imageHeight;
                    layoutParams.height = imageHeight;
                }
                rootView.setLayoutParams(layoutParams);
            }
            return new ViewHolder(rootView, mActivity);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
             holder.bindData(list.get(position), position);
             holder.itemView.setOnClickListener(new OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     onItemClick(holder,holder.itemView,list.get(position),position);
                 }
             });
        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }

        public void replaceDatas(List<LocalMedia> selectList) {
            list = selectList;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        ViewHolder(View rootView, Context context) {
            super(rootView);
            iv = (ImageView) rootView.findViewById(R.id.iv);
        }
        
        public void bindData(LocalMedia mBean, int position) {
            if (mBean.getNum() != Integer.MIN_VALUE) {
                Glide.with(itemView).load(new File(mBean.getPath())).into(iv);
                itemView.setBackgroundResource(R.drawable.shape_e5_stroke);
            } else {
                Glide.with(itemView).load(R.mipmap.xiangji).into(iv);
                itemView.setBackground(null);
            }
        }
    }

    public static String getUpLoadImgPath(LocalMedia media) {
        String path = "";
        if (media.isCompressed()) {
            path = media.getCompressPath();
        } else if (media.isCut()) {
            path = media.getCutPath();
        } else {
            path = media.getPath();
        }
        return path;
    }


    public interface OnImageSelectedListener{
        void onResult(List<LocalMedia> selectList);
        int getPos();
    }
}