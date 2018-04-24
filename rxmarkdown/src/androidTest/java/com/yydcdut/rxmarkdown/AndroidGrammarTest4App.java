package com.yydcdut.rxmarkdown;

import android.test.InstrumentationTestCase;

/**
 * Created by yuyidong on 16/5/14.
 */
public class AndroidGrammarTest4App extends InstrumentationTestCase {
//    private RxMDConfiguration mRxMDConfiguration;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
//        mRxMDConfiguration = new RxMDConfiguration.Builder(getInstrumentation().getContext())
//                .setDefaultImageSize(50, 50)
//                .setBlockQuotesLineColor(0xff33b5e5)
//                .setHeader1RelativeSize(1.6f)
//                .setHeader2RelativeSize(1.5f)
//                .setHeader3RelativeSize(1.4f)
//                .setHeader4RelativeSize(1.3f)
//                .setHeader5RelativeSize(1.2f)
//                .setHeader6RelativeSize(1.1f)
//                .setHorizontalRulesColor(0xff99cc00)
//                .setCodeBgColor(0xffff4444)
//                .setCodeBlockBgColor(0x33999999)
//                .setTodoColor(0xffaa66cc)
//                .setTodoDoneColor(0xffff8800)
//                .setUnOrderListColor(0xff00ddff)
//                .build();
    }

    public void testHyperLink() {
//        String testString = "111](22[22](33333)";
//        Syntax hyperLinkGrammar = AndroidInstanceFactory.getAndroidGrammar(AndroidInstanceFactory.GRAMMAR_HYPERLINK);
//        boolean b = hyperLinkGrammar.isMatch(testString);
//        if (b) {
//            CharSequence charSequence = hyperLinkGrammar.format(testString);
//            assertEquals("aa[bbbdddd", charSequence+"");
//        } else {
//            assertTrue(b);
//        }
//        SpannableStringBuilder ssb = new SpannableStringBuilder(testString);
//        ssb = complex(testString, ssb);
//        Log.i("yuyidong", "result--->" + ssb.toString());
//        assertEquals("111](2222", ssb.toString());
    }

//    private static final String KEY_0_HYPER_LINK = "[";
//    private static final String KEY_1 = "](";
//    private static final String KEY_2_HYPER_LINK = ")";
//
//    private static final String PLACE_HOLDER = " ";
//
//    private SpannableStringBuilder complex(String text, SpannableStringBuilder ssb) {
//        SpannableStringBuilder tmp = new SpannableStringBuilder();
//        String tmpTotal = text;
//        while (true) {
//            int position4Key0 = tmpTotal.indexOf(KEY_0_HYPER_LINK);
//            int position4Key1 = tmpTotal.indexOf(KEY_1);
//            int position4Key2 = tmpTotal.indexOf(KEY_2_HYPER_LINK);
//            if (position4Key0 == -1 || position4Key1 == -1 || position4Key2 == -1) {
//                break;
//            }
//            if (position4Key0 < position4Key1 && position4Key1 < position4Key2) {
//                //处理aa[bb[b](cccc)dddd
//                int tmpCenter = tmpTotal.indexOf(KEY_1);
//                String tmpLeft = tmpTotal.substring(0, tmpCenter);
//                //正常流程
//                int positionHeader = tmpLeft.lastIndexOf(KEY_0_HYPER_LINK);
//                tmp.append(tmpTotal.substring(0, positionHeader));
//                int index = tmp.length();
//                tmpTotal = tmpTotal.substring(positionHeader + KEY_0_HYPER_LINK.length(), tmpTotal.length());
//                int positionCenter = tmpTotal.indexOf(KEY_1);
//                ssb.delete(tmp.length(), tmp.length() + KEY_0_HYPER_LINK.length());
//                tmp.append(tmpTotal.substring(0, positionCenter));
//                tmpTotal = tmpTotal.substring(positionCenter + KEY_1.length(), tmpTotal.length());
//                int positionFooter = tmpTotal.indexOf(KEY_2_HYPER_LINK);
//                String link = tmpTotal.substring(0, positionFooter);
//                ssb.setSpan(new URLSpan(link), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                ssb.delete(tmp.length(), tmp.length() + KEY_1.length() + link.length() + KEY_2_HYPER_LINK.length());
//                tmpTotal = tmpTotal.substring(positionFooter + KEY_2_HYPER_LINK.length(), tmpTotal.length());
//            } else if (position4Key0 < position4Key1 && position4Key0 < position4Key2 && position4Key2 < position4Key1) {
//                //111[22)22](33333)
//                tmpTotal = replaceFirstOne(tmpTotal, KEY_2_HYPER_LINK, PLACE_HOLDER);
//            } else if (position4Key1 < position4Key0 && position4Key1 < position4Key2) {
//                //](在最前面的情况 111](2222[333)4444  1111](2222)3333[4444
//                tmp.append(tmpTotal.substring(0, position4Key1 + KEY_1.length()));
//                tmpTotal = tmpTotal.substring(position4Key1 + KEY_1.length(), tmpTotal.length());
//            } else if (position4Key2 < position4Key0 && position4Key2 < position4Key1) {
//                //)在最前面的情况 111)2222](333[4444  1111)2222[3333](4444
//                tmp.append(tmpTotal.substring(0, position4Key2 + KEY_2_HYPER_LINK.length()));
//                tmpTotal = tmpTotal.substring(position4Key2 + KEY_2_HYPER_LINK.length(), tmpTotal.length());
//            }
//        }
//        return ssb;
//    }
//
//    private String replaceFirstOne(@NonNull String content, @NonNull String target, @NonNull String replacement) {
//        if (target == null) {
//            throw new NullPointerException("target == null");
//        }
//        if (replacement == null) {
//            throw new NullPointerException("replacement == null");
//        }
//        int matchStart = content.indexOf(target, 0);
//        if (matchStart == -1) {
//            return content;
//        }
//        int targetLength = target.length();
//        if (targetLength == 0) {
//            int resultLength = content.length() + (content.length() + 1) * replacement.length();
//            StringBuilder result = new StringBuilder(resultLength);
//            result.append(replacement);
//            for (int i = 0; i != content.length(); ++i) {
//                result.append(content.charAt(i));
//                result.append(replacement);
//            }
//            return result.toString();
//        }
//        StringBuilder result = new StringBuilder(content.length());
//        for (int i = 0; i < matchStart; ++i) {
//            result.append(content.charAt(i));
//        }
//        result.append(replacement);
//        int over = matchStart + targetLength;
//        for (int i = over; i < content.length(); ++i) {
//            result.append(content.charAt(i));
//        }
//        return result.toString();
//    }

    public void testImageGrammar() {
//        String testString = "aa![bb![b](cccc)dddd";
//        boolean b = isMatch(testString);
//        if (b) {
//            SpannableStringBuilder ssb = new SpannableStringBuilder(testString);
//            ssb = complex(testString, ssb);
//            assertEquals("aa![bbbdddd",ssb.toString());
//        } else {
//            assertTrue(b);
//        }
    }

//    private static final String KEY_0_HYPER_LINK = "![";
//    private static final String KEY_1 = "](";
//    private static final String KEY_2_HYPER_LINK = ")";
//
//    private static final String PLACE_HOLDER_0 = "  ";
//    private static final String PLACE_HOLDER_2 = " ";
//
//    boolean isMatch(@NonNull String text) {
//        if (TextUtils.isEmpty(text)) {
//            return false;
//        }
//        if (!(text.contains(KEY_0_HYPER_LINK) && text.contains(KEY_1) && text.contains(KEY_2_HYPER_LINK))) {
//            return false;
//        }
//        Pattern pattern = Pattern.compile(".*[!\\[]{1}.*[\\](]{1}.*[)]{1}.*");
//        return pattern.matcher(text).matches();
//    }
//
//    private SpannableStringBuilder complex(String text, SpannableStringBuilder ssb) {
//        SpannableStringBuilder tmp = new SpannableStringBuilder();
//        String tmpTotal = text;
//        while (true) {
//            int position4Key0 = tmpTotal.indexOf(KEY_0_HYPER_LINK);
//            int position4Key1 = tmpTotal.indexOf(KEY_1);
//            int position4Key2 = tmpTotal.indexOf(KEY_2_HYPER_LINK);
//            if (position4Key0 == -1 || position4Key1 == -1 || position4Key2 == -1) {
//                break;
//            }
//            if (position4Key0 < position4Key1 && position4Key1 < position4Key2) {
//                //处理aa![bb![b](cccc)dddd
//                int tmpCenter = tmpTotal.indexOf(KEY_1);
//                String tmpLeft = tmpTotal.substring(0, tmpCenter);
//                //正常流程
//                int positionHeader = tmpLeft.lastIndexOf(KEY_0_HYPER_LINK);
//                tmp.append(tmpTotal.substring(0, positionHeader));
//                int index = tmp.length();
//                tmpTotal = tmpTotal.substring(positionHeader + KEY_0_HYPER_LINK.length(), tmpTotal.length());
//                int positionCenter = tmpTotal.indexOf(KEY_1);
//                ssb.delete(tmp.length(), tmp.length() + KEY_0_HYPER_LINK.length());
//                tmp.append(tmpTotal.substring(0, positionCenter));
//                tmpTotal = tmpTotal.substring(positionCenter + KEY_1.length(), tmpTotal.length());
//                int positionFooter = tmpTotal.indexOf(KEY_2_HYPER_LINK);
//                String link = tmpTotal.substring(0, positionFooter);
//                ssb.setSpan(new URLSpan(link), index, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                ssb.delete(tmp.length(), tmp.length() + KEY_1.length() + link.length() + KEY_2_HYPER_LINK.length());
//                tmpTotal = tmpTotal.substring(positionFooter + KEY_2_HYPER_LINK.length(), tmpTotal.length());
//            } else if (position4Key0 < position4Key1 && position4Key0 < position4Key2 && position4Key2 < position4Key1) {
//                //111![22)22](33333)
//                tmpTotal = replaceFirstOne(tmpTotal, KEY_2_HYPER_LINK, PLACE_HOLDER_2);
//            } else if (position4Key1 < position4Key0 && position4Key1 < position4Key2) {
//                //](在最前面的情况 111](2222![333)4444  1111](2222)3333![4444
//                tmp.append(tmpTotal.substring(0, position4Key1 + KEY_1.length()));
//                tmpTotal = tmpTotal.substring(position4Key1 + KEY_1.length(), tmpTotal.length());
//            } else if (position4Key2 < position4Key0 && position4Key2 < position4Key1) {
//                //)在最前面的情况 111)2222](333![4444  1111)2222![3333](4444
//                tmp.append(tmpTotal.substring(0, position4Key2 + KEY_2_HYPER_LINK.length()));
//                tmpTotal = tmpTotal.substring(position4Key2 + KEY_2_HYPER_LINK.length(), tmpTotal.length());
//            }
//        }
//        return ssb;
//    }
//
//    private String replaceFirstOne(@NonNull String content, @NonNull String target, @NonNull String replacement) {
//        if (target == null) {
//            throw new NullPointerException("target == null");
//        }
//        if (replacement == null) {
//            throw new NullPointerException("replacement == null");
//        }
//        int matchStart = content.indexOf(target, 0);
//        if (matchStart == -1) {
//            return content;
//        }
//        int targetLength = target.length();
//        if (targetLength == 0) {
//            int resultLength = content.length() + (content.length() + 1) * replacement.length();
//            StringBuilder result = new StringBuilder(resultLength);
//            result.append(replacement);
//            for (int i = 0; i != content.length(); ++i) {
//                result.append(content.charAt(i));
//                result.append(replacement);
//            }
//            return result.toString();
//        }
//        StringBuilder result = new StringBuilder(content.length());
//        for (int i = 0; i < matchStart; ++i) {
//            result.append(content.charAt(i));
//        }
//        result.append(replacement);
//        int over = matchStart + targetLength;
//        for (int i = over; i < content.length(); ++i) {
//            result.append(content.charAt(i));
//        }
//        return result.toString();
//    }

    public void testCodeGrammar() {
//        CodeSyntax codeGrammar = new CodeSyntax(mRxMDConfiguration);
//        List<Pair<Integer, Integer>> list = codeGrammar.find(MD_SAMPLE);
//        Log.d("yuyidong", "list-->" + list.size());
//        for (Pair<Integer, Integer> pair : list) {
//            Log.d("yuyidong", "pair-->" + pair.first + "    " + pair.second);
//        }
    }

//    static String MD_SAMPLE = "在这个**版本**中我们*增加test*了 `Markdown` 功能。`Markdown` 是~~一种使用纯文本编写的标记~~语言，可以产生格式![test](http://7xs03u.com1.z0.glb.clouddn.com/dex_dexopt_dex2oat.png/320$320)丰富的页面[^排版效果]，比如突出[标题](http://www.baidu.com)、居中、加粗、引用和生成列表。\n" +
//            "\n" +
//            "## **用法与规则：**\n" +
////                    "\n" +
////                    "你可以手动输入，也可以点击键盘上方的按钮快速输入 Markdown 符号。\n" +
//            "\n" +
//            "### **标题**\n" +
//            "***\n" +
//            "\n" +
//            "```\n" +
//            "test1\n" +
//            "test2\n" +
//            "test3\n" +
//            "test4\n" +
//            "```\n" +
//            "\n" +
//            "- [ ] 123\n" +
//            "- [ ] 456\n" +
//            "- [ ] 789\n" +
//            "\n" +
//            "- [x] 987\n" +
//            "- [x] 654\n" +
//            "- [x] 321\n" +
//            "\n" +
//            "例如：\n" +
//            "# 一级标题\n" +
//            "## 二级标题\n" +
//            "### 三级标题\n" +
//            "---\n" +
//            "![test](file://" + Environment.getExternalStorageDirectory() + File.separator + "b.jpg/400$400" + ")\n" +
//            "### **加粗功能**\n" +
//            "使用一组星号“**”来加粗一段文字\n" +
//            "\n" +
//            "```\n" +
//            "test1\n" +
//            "test2\n" +
//            "test3\n" +
//            "test4\n" +
//            "```" +
//            "例如：\n" +
//            "这是**加粗的文字**\n" +
//            "\n" +
//            "### **居中**\n" +
//            "使用一对中括号“[文字]”来居中一段文字，也可![test](assets://bb.jpg/100$100)以和标题叠加使用\n" +
//            "\n" +
//            "例如：\n" +
//            "[### 这是一个居中的标题]\n" +
//            "\n" +
//            "### **引用**\n" +
//            "使用“> ”在段首来引用一段文字\n" +
//            "\n" +
//            "例如：\n" +
//            "> 这是一段引用\n" +
//            "> > > 这是一段引用\n" +
//            "\n" +
//            "### **无序列表**\n" +
//            "使用 “-”、“*”或“+”加空格 来创建无序列表\n" +
//            "\n" +
//            "例如：\n" +
//            "- 这是一个无序列表\n" +
//            "+ 这是一个无序列表\n" +
//            "* 这是一个无序列表\n" +
//            "\n" +
//            "### **有序列表**\n" +
//            "使用 数字圆点加空格 如“1. ”、“2. ”来创建有序列表\n" +
//            "\n" +
//            "例如：\n" +
//            "1. 这是一个有序列表\n" +
//            "2. 这是一个有序列表\n" +
//            "3. 这是一个有序列表";

}
