package com.yydcdut.markdowndemo.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yydcdut.markdown.MarkdownConfiguration;
import com.yydcdut.markdown.MarkdownEditText;
import com.yydcdut.markdowndemo.R;
import com.yydcdut.markdowndemo.controller.BlockQuotesController;
import com.yydcdut.markdowndemo.controller.CenterAlignController;
import com.yydcdut.markdowndemo.controller.CodeController;
import com.yydcdut.markdowndemo.controller.HeaderController;
import com.yydcdut.markdowndemo.controller.HorizontalRulesController;
import com.yydcdut.markdowndemo.controller.ImageController;
import com.yydcdut.markdowndemo.controller.LinkController;
import com.yydcdut.markdowndemo.controller.ListController;
import com.yydcdut.markdowndemo.controller.StrikeThroughController;
import com.yydcdut.markdowndemo.controller.StyleController;
import com.yydcdut.markdowndemo.controller.TodoController;

/**
 * Created by yuyidong on 16/7/12.
 */
public class HorizontalEditScrollView extends FrameLayout implements View.OnClickListener,
        View.OnLongClickListener {
    private MarkdownEditText mMarkdownEditText;

    private HeaderController mHeaderController;
    private StyleController mStyleController;
    private CenterAlignController mCenterAlignController;
    private HorizontalRulesController mHorizontalRulesController;
    private TodoController mTodoController;
    private StrikeThroughController mStrikeThroughController;
    private CodeController mCodeController;
    private BlockQuotesController mBlockQuotesController;
    private ListController mListController;
    private ImageController mImageController;
    private LinkController mLinkController;

    public HorizontalEditScrollView(Context context) {
        this(context, null);
    }

    public HorizontalEditScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalEditScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_horizontal_scroll, this, true);
    }

    public void setEditTextAndConfig(@NonNull MarkdownEditText markdownEditText,
                                     @NonNull MarkdownConfiguration markdownConfiguration) {
        mMarkdownEditText = markdownEditText;
        mHeaderController = new HeaderController(markdownEditText, markdownConfiguration);
        mStyleController = new StyleController(markdownEditText);
        mCenterAlignController = new CenterAlignController(markdownEditText);
        mHorizontalRulesController = new HorizontalRulesController(markdownEditText);
        mTodoController = new TodoController(markdownEditText);
        mStrikeThroughController = new StrikeThroughController(markdownEditText);
        mCodeController = new CodeController(markdownEditText);
        mBlockQuotesController = new BlockQuotesController(markdownEditText);
        mListController = new ListController(markdownEditText);
        mImageController = new ImageController(markdownEditText);
        mLinkController = new LinkController(markdownEditText);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.img_header1).setOnClickListener(this);
        findViewById(R.id.img_header2).setOnClickListener(this);
        findViewById(R.id.img_header3).setOnClickListener(this);
        findViewById(R.id.img_header4).setOnClickListener(this);
        findViewById(R.id.img_header5).setOnClickListener(this);
        findViewById(R.id.img_header6).setOnClickListener(this);
        findViewById(R.id.img_bold).setOnClickListener(this);
        findViewById(R.id.img_italic).setOnClickListener(this);
        findViewById(R.id.img_center_align).setOnClickListener(this);
        findViewById(R.id.img_horizontal_rules).setOnClickListener(this);
        findViewById(R.id.img_todo).setOnClickListener(this);
        findViewById(R.id.img_todo_done).setOnClickListener(this);
        findViewById(R.id.img_strike_through).setOnClickListener(this);
        findViewById(R.id.img_inline_code).setOnClickListener(this);
        findViewById(R.id.img_code).setOnClickListener(this);
        findViewById(R.id.img_block_quote).setOnClickListener(this);
        findViewById(R.id.img_block_quote).setOnLongClickListener(this);
        findViewById(R.id.img_unorder_list).setOnClickListener(this);
        findViewById(R.id.img_order_list).setOnClickListener(this);
        findViewById(R.id.img_link).setOnClickListener(this);
        findViewById(R.id.img_photo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mMarkdownEditText == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.img_header1:
                mHeaderController.doHeader(1);
                break;
            case R.id.img_header2:
                mHeaderController.doHeader(2);
                break;
            case R.id.img_header3:
                mHeaderController.doHeader(3);
                break;
            case R.id.img_header4:
                mHeaderController.doHeader(4);
                break;
            case R.id.img_header5:
                mHeaderController.doHeader(5);
                break;
            case R.id.img_header6:
                mHeaderController.doHeader(6);
                break;
            case R.id.img_bold:
                mStyleController.doBold();
                break;
            case R.id.img_italic:
                mStyleController.doItalic();
                break;
            case R.id.img_center_align:
                mCenterAlignController.doCenter();
                break;
            case R.id.img_horizontal_rules:
                mHorizontalRulesController.doHorizontalRules();
                break;
            case R.id.img_todo:
                mTodoController.doTodo();
                break;
            case R.id.img_todo_done:
                mTodoController.doTodoDone();
                break;
            case R.id.img_strike_through:
                mStrikeThroughController.doStrikeThrough();
                break;
            case R.id.img_inline_code:
                mCodeController.doInlineCode();
                break;
            case R.id.img_code:
                mCodeController.doCode();
                break;
            case R.id.img_block_quote:
                mBlockQuotesController.doBlockQuotes();
                break;
            case R.id.img_unorder_list:
                mListController.doUnOrderList();
                break;
            case R.id.img_order_list:
                mListController.doOrderList();
                break;
            case R.id.img_link:
                mLinkController.doImage();
                break;
            case R.id.img_photo:
                mImageController.doImage();
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.img_block_quote:
                mBlockQuotesController.addNestedBlockQuotes();
                break;
        }
        return true;
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        mImageController.handleResult(requestCode, resultCode, data);
    }

}
