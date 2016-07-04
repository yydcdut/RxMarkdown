package com.yydcdut.markdown;

import android.os.Environment;

import java.io.File;

/**
 * Created by yuyidong on 16/5/11.
 */
public interface Const {

    String MD_SAMPLE = "在这个**版本**中我们*增加test*了 `Markdown` 功能。`Markdown` 是~~一种使用纯文本编写的标记~~语言，可以产生格式![test](http://static.yo9.com/web/emotions/tv_sad.png/200$400)丰富的页面[^排版效果]，比如突出[标题](http://www.baidu.com)、居中、加粗、引用和生成列表。\n" +
            "\n" +
            "## **用法与规则：**\n" +
//                    "\n" +
//                    "你可以手动输入，也可以点击键盘上方的按钮快速输入 Markdown 符号。\n" +
            "\n" +
            "### **标题**\n" +
            "使用“#”加空格在*行首*来创建标题![test](drawable://" + R.drawable.ic_school_white_24dp + "/300$300)\n" +
            "***\n" +
            "\n" +
            "```\n" +
            "test1\n" +
            "test2\n" +
            "test3\n" +
            "test4\n" +
            "```\n" +
            "\n" +
            "- [ ] 123\n" +
            "- [ ] 456\n" +
            "- [ ] 789\n" +
            "\n" +
            "- [x] 987\n" +
            "- [x] 654\n" +
            "- [x] 321\n" +
            "\n" +
            "例如：\n" +
            "# 一级标题\n" +
            "## 二级标题\n" +
            "### 三级标题\n" +
            "---\n" +
            "![test](file://" + Environment.getExternalStorageDirectory() + File.separator + "tv_cheers.png/400$300" + ")\n" +
            "### **加粗功能**\n" +
            "使用一组星号“**”来加粗一段文字\n" +
            "\n" +
            "```\n" +
            "test1\n" +
            "test2\n" +
            "test3\n" +
            "test4\n" +
            "```" +
            "例如：\n" +
            "这是**加粗的文字**\n" +
            "\n" +
            "### **居中**\n" +
            "使用一对中括号“[文字]”来居中一段文字，也可![test](assets://tv_cheers.png/100$100)以和标题叠加使用\n" +
            "\n" +
            "例如：\n" +
            "[### 这是一个居中的标题]\n" +
            "\n" +
            "### **引用**\n" +
            "使用“> ”在段首来引用一段文字\n" +
            "\n" +
            "例如：\n" +
            "> 这是一段引用\n" +
            "> > > 这是一段引用\n" +
            "\n" +
            "### **无序列表**\n" +
            "使用 “-”、“*”或“+”加空格 来创建无序列表\n" +
            "\n" +
            "例如：\n" +
            "- 这是一个无序列表\n" +
            "+ 这是一个无序列表\n" +
            "* 这是一个无序列表\n" +
            "\n" +
            "### **有序列表**\n" +
            "使用 数字圆点加空格 如“1. ”、“2. ”来创建有序列表\n" +
            "\n" +
            "例如：\n" +
            "1. 这是一个有序列表\n" +
            "2. 这是一个有序列表\n" +
            "3. 这是一个有序列表";

    String MD_SAMPLE2 =
            "当程序越来越大之后，出现了一个 dex 包装不下的情况，通过 `MultiDex` 的方法解决了这个问题，但是在底端机器上又出现了 `INSTALL_FAILED_DEXOPT` 的情况，那再解决这个问题吧。等解决完这个问题之后，发现需要填的坑越来越多了，文章讲的是我在分包处理中填的坑，比如 65536、LinearAlloc、NoClassDefFoundError等等。\n" +
                    "\n" +
                    "<!-- more -->\n" +
                    "\n" +
                    "## INSTALL_FAILED_DEXOPT\n" +
                    "\n" +
                    "INSTALL_FAILED_DEXOPT 出现的原因大部分都是两种，一种是 65536 了，另外一种是 `LinearAlloc` 太小了。两者的限制不同，但是原因却是相似，那就是App太大了，导致没办法安装到手机上。\n" +
                    "\n" +
                    "### 65536\n" +
                    "\n" +
                    "> trouble writing output: Too many method references: 70048; max is 65536.\n" +
                    "\n" +
                    "或者\n" +
                    "\n" +
                    "> UNEXPECTED TOP-LEVEL EXCEPTION: java.lang.IllegalArgumentException: method ID not in [0, 0xffff]: 65536 \n" +
                    "> \u200B\tat com.android.dx.merge.DexMerger$6.updateIndex(DexMerger.java:501) \n" +
                    "> \u200B\tat com.android.dx.merge.DexMerger$IdMerger.mergeSorted(DexMerger.java:276) \n" +
                    "> \u200B\tat com.android.dx.merge.DexMerger.mergeMethodIds(DexMerger.java:490) \n" +
                    "> \u200B\tat com.android.dx.merge.DexMerger.mergeDexes(DexMerger.java:167) \n" +
                    "> \u200B\tat com.android.dx.merge.DexMerger.merge(DexMerger.java:188) \n" +
                    "> \u200B\tat com.android.dx.command.dexer.Main.mergeLibraryDexBuffers(Main.java:439) \n" +
                    "> \u200B\tat com.android.dx.command.dexer.Main.runMonoDex(Main.java:287) \n" +
                    "> \u200B\tat com.android.dx.command.dexer.Main.run(Main.java:230) \n" +
                    "> \u200B\tat com.android.dx.command.dexer.Main.main(Main.java:199) \n" +
                    "> \u200B\tat com.android.dx.command.Main.main(Main.java:103):Derp:dexDerpDebug FAILED\n" +
                    "\n" +
                    "#### 编译环境\n" +
                    "\n" +
                    "```\n" +
                    "buildscript {\n" +
                    "    repositories {\n" +
                    "        jcenter()\n" +
                    "    }\n" +
                    "    dependencies {\n" +
                    "        classpath 'com.android.tools.build:gradle:1.3.0'\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "android {\n" +
                    "    compileSdkVersion 23\n" +
                    "    buildToolsVersion \"24.0.0rc1\"\n" +
                    "\t//....\n" +
                    "    defaultConfig {\n" +
                    "        minSdkVersion 14\n" +
                    "        targetSdkVersion 23\n" +
                    "        //....\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "#### 为什么是65536\n" +
                    "\n" +
                    "根据  [StackOverFlow -- Does the Android ART runtime have the same method limit limitations as Dalvik?](http://stackoverflow.com/questions/21490382/does-the-android-art-runtime-have-the-same-method-limit-limitations-as-dalvik/21492160#21492160) 上面的说法，是因为 Dalvik 的 invoke-kind 指令集中，method reference index 只留了 16 bits，最多能**引用** 65535 个方法。[Dalvik bytecode](https://source.android.com/devices/tech/dalvik/dalvik-bytecode.html) ：\n" +
                    "\n" +
                    "> | Op & Format | Mnemonic / Syntax                        | Arguments                                |\n" +
                    "> | :---------: | ---------------------------------------- | ---------------------------------------- |\n" +
                    "> | 6e..72 35c  | invoke-*kind* {vC, vD, vE, vF, vG}, meth@BBBB6e: invoke-virtual6f: invoke-super70: invoke-direct71: invoke-static72: invoke-interface | `A:` argument word count (4 bits)`B:` method reference index (16 bits)`C..G:` argument registers (4 bits each) |\n" +
                    "\n" +
                    "即使 dex 里面的引用方法数超过了 65536，那也只有前面的 65536 得的到调用。所以这个不是 dex 的原因。其次，既然和 dex 没有关系，那在打包 dex 的时候为什么会报错。我们先定位 `Too many` 关键字，定位到了 [MemberIdsSection](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/dex/file/MemberIdsSection.java) ：\n" +
                    "\n" +
                    "```\n" +
                    "public abstract class MemberIdsSection extends UniformItemSection {\n" +
                    "  /** {@inheritDoc} */\n" +
                    "    @Override\n" +
                    "    protected void orderItems() {\n" +
                    "        int idx = 0;\n" +
                    "\n" +
                    "        if (items().size() > DexFormat.MAX_MEMBER_IDX + 1) {\n" +
                    "            throw new DexIndexOverflowException(getTooManyMembersMessage());\n" +
                    "        }\n" +
                    "\n" +
                    "        for (Object i : items()) {\n" +
                    "            ((MemberIdItem) i).setIndex(idx);\n" +
                    "            idx++;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    private String getTooManyMembersMessage() {\n" +
                    "        Map<String, AtomicInteger> membersByPackage = new TreeMap<String, AtomicInteger>();\n" +
                    "        for (Object member : items()) {\n" +
                    "            String packageName = ((MemberIdItem) member).getDefiningClass().getPackageName();\n" +
                    "            AtomicInteger count = membersByPackage.get(packageName);\n" +
                    "            if (count == null) {\n" +
                    "                count = new AtomicInteger();\n" +
                    "                membersByPackage.put(packageName, count);\n" +
                    "            }\n" +
                    "            count.incrementAndGet();\n" +
                    "        }\n" +
                    "\n" +
                    "        Formatter formatter = new Formatter();\n" +
                    "        try {\n" +
                    "            String memberType = this instanceof MethodIdsSection ? \"method\" : \"field\";\n" +
                    "            formatter.format(\"Too many %s references: %d; max is %d.%n\" +\n" +
                    "                    Main.getTooManyIdsErrorMessage() + \"%n\" +\n" +
                    "                    \"References by package:\",\n" +
                    "                    memberType, items().size(), DexFormat.MAX_MEMBER_IDX + 1);\n" +
                    "            for (Map.Entry<String, AtomicInteger> entry : membersByPackage.entrySet()) {\n" +
                    "                formatter.format(\"%n%6d %s\", entry.getValue().get(), entry.getKey());\n" +
                    "            }\n" +
                    "            return formatter.toString();\n" +
                    "        } finally {\n" +
                    "            formatter.close();\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "`items().size() > DexFormat.MAX_MEMBER_IDX + 1 ` ，那 [DexFormat](https://github.com/yydcdut/android_libcore/blob/kitkat/dex/src/main/java/com/android/dex/DexFormat.java) 的值是：\n" +
                    "\n" +
                    "```\n" +
                    "public final class DexFormat {\n" +
                    "  /**\n" +
                    "     * Maximum addressable field or method index.\n" +
                    "     * The largest addressable member is 0xffff, in the \"instruction formats\" spec as field@CCCC or\n" +
                    "     * meth@CCCC.\n" +
                    "     */\n" +
                    "    public static final int MAX_MEMBER_IDX = 0xFFFF;\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "dx 在这里做了判断，当大于 65536 的时候就抛出异常了。所以在生成 dex 文件的过程中，当调用方法数不能超过 65535 。那我们再跟一跟代码，发现 `MemberIdsSection` 的一个子类叫  [MethodidsSection](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/dex/file/MethodIdsSection.java) ：\n" +
                    "\n" +
                    "```\n" +
                    "public final class MethodIdsSection extends MemberIdsSection {}\n" +
                    "```\n" +
                    "\n" +
                    "回过头来，看一下 `orderItems()` 方法在哪里被调用了，跟到了 `MemberIdsSection` 的父类 [UniformItemSection](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/dex/file/UniformItemSection.java) :\n" +
                    "\n" +
                    "```\n" +
                    "public abstract class UniformItemSection extends Section {\n" +
                    "\t@Override\n" +
                    "    protected final void prepare0() {\n" +
                    "        DexFile file = getFile();\n" +
                    "\n" +
                    "        orderItems();\n" +
                    "\n" +
                    "        for (Item one : items()) {\n" +
                    "            one.addContents(file);\n" +
                    "        }\n" +
                    "    }\n" +
                    "  \t\n" +
                    "\tprotected abstract void orderItems();\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "再跟一下 `prepare0` 在哪里被调用，查到了 `UniformItemSection` 父类 [Section](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/dex/file/Section.java) :\n" +
                    "\n" +
                    "```\n" +
                    "public abstract class Section {\n" +
                    "\tpublic final void prepare() {\n" +
                    "        throwIfPrepared();\n" +
                    "        prepare0();\n" +
                    "        prepared = true;\n" +
                    "    }\n" +
                    "  \t\n" +
                    "  \tprotected abstract void prepare0();\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "那现在再跟一下 `prepare()` ，查到 [DexFile](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/dex/file/DexFile.java) 中有调用：\n" +
                    "\n" +
                    "```\n" +
                    "public final class DexFile {\n" +
                    "  private ByteArrayAnnotatedOutput toDex0(boolean annotate, boolean verbose) {\n" +
                    "        classDefs.prepare();\n" +
                    "        classData.prepare();\n" +
                    "        wordData.prepare();\n" +
                    "        byteData.prepare();\n" +
                    "        methodIds.prepare();\n" +
                    "        fieldIds.prepare();\n" +
                    "        protoIds.prepare();\n" +
                    "        typeLists.prepare();\n" +
                    "        typeIds.prepare();\n" +
                    "        stringIds.prepare();\n" +
                    "        stringData.prepare();\n" +
                    "        header.prepare();\n" +
                    "\t\t//blablabla......\n" +
                    "  \t}\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "那再看一下 `toDex0()` 吧，因为是 private 的，直接在类中找调用的地方就可以了：\n" +
                    "\n" +
                    "```\n" +
                    "public final class DexFile {\n" +
                    "\tpublic byte[] toDex(Writer humanOut, boolean verbose) throws IOException {\n" +
                    "        boolean annotate = (humanOut != null);\n" +
                    "        ByteArrayAnnotatedOutput result = toDex0(annotate, verbose);\n" +
                    "\n" +
                    "        if (annotate) {\n" +
                    "            result.writeAnnotationsTo(humanOut);\n" +
                    "        }\n" +
                    "\n" +
                    "        return result.getArray();\n" +
                    "    }\n" +
                    "\n" +
                    "  \tpublic void writeTo(OutputStream out, Writer humanOut, boolean verbose) throws IOException {\n" +
                    "        boolean annotate = (humanOut != null);\n" +
                    "        ByteArrayAnnotatedOutput result = toDex0(annotate, verbose);\n" +
                    "\n" +
                    "        if (out != null) {\n" +
                    "            out.write(result.getArray());\n" +
                    "        }\n" +
                    "\n" +
                    "        if (annotate) {\n" +
                    "            result.writeAnnotationsTo(humanOut);\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "先搜搜 `toDex()` 方法吧，最终发现在 [com.android.dx.command.dexer.Main](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/command/dexer/Main.java) 中：\n" +
                    "\n" +
                    "```\n" +
                    "public class Main {\n" +
                    "\tprivate static byte[] writeDex(DexFile outputDex) {\n" +
                    "        byte[] outArray = null;\n" +
                    "      \t//blablabla......\n" +
                    "\t\tif (args.methodToDump != null) {\n" +
                    "\t\t\toutputDex.toDex(null, false);\n" +
                    "            dumpMethod(outputDex, args.methodToDump, humanOutWriter);\n" +
                    "\t\t} else {\n" +
                    "\t\t\toutArray = outputDex.toDex(humanOutWriter, args.verboseDump);\n" +
                    "\t\t}\n" +
                    "\t\t//blablabla......\n" +
                    "        return outArray;\n" +
                    "    }\n" +
                    "  \t//调用writeDex的地方\n" +
                    "  \tprivate static int runMonoDex() throws IOException {\n" +
                    "  \t\t//blablabla......\n" +
                    "      \toutArray = writeDex(outputDex);\n" +
                    "      \t//blablabla......\n" +
                    "\t}\n" +
                    "  \t//调用runMonoDex的地方\n" +
                    "  \tpublic static int run(Arguments arguments) throws IOException {\n" +
                    "  \t\tif (args.multiDex) {\n" +
                    "\t\t\treturn runMultiDex();\n" +
                    "\t\t} else {\n" +
                    "            return runMonoDex();\n" +
                    "        }\n" +
                    "\t}\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "`args.multiDex` 就是是否分包的参数，那么问题找着了，如果不选择分包的情况下，引用方法数超过了 65536 的话就会抛出异常。\n" +
                    "\n" +
                    "同样分析第二种情况，根据错误信息可以具体定位到代码，但是很奇怪的是 `DexMerger` ，我们没有设置分包参数或者其他参数，为什么会有 `DexMerger` ，而且依赖工程最终不都是 aar 格式的吗？那我们还是来跟一跟代码吧。\n" +
                    "\n" +
                    "```\n" +
                    "public class Main {\n" +
                    "\tprivate static byte[] mergeLibraryDexBuffers(byte[] outArray) throws IOException {\n" +
                    "        ArrayList<Dex> dexes = new ArrayList<Dex>();\n" +
                    "        if (outArray != null) {\n" +
                    "            dexes.add(new Dex(outArray));\n" +
                    "        }\n" +
                    "        for (byte[] libraryDex : libraryDexBuffers) {\n" +
                    "            dexes.add(new Dex(libraryDex));\n" +
                    "        }\n" +
                    "        if (dexes.isEmpty()) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        Dex merged = new DexMerger(dexes.toArray(new Dex[dexes.size()]), CollisionPolicy.FAIL).merge();\n" +
                    "        return merged.getBytes();\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "这里可以看到变量 `libraryDexBuffers` ，是一个 List 集合，那么我们看一下这个集合在哪里添加数据的：\n" +
                    "\n" +
                    "```\n" +
                    "public class Main {\n" +
                    "\tprivate static boolean processFileBytes(String name, long lastModified, byte[] bytes) {\n" +
                    "  \t\tboolean isClassesDex = name.equals(DexFormat.DEX_IN_JAR_NAME);\n" +
                    "      \t//blablabla...\n" +
                    "        } else if (isClassesDex) {\n" +
                    "            synchronized (libraryDexBuffers) {\n" +
                    "                libraryDexBuffers.add(bytes);\n" +
                    "            }\n" +
                    "            return true;\n" +
                    "        } else {\n" +
                    "      \t//blablabla...\n" +
                    "\t}\n" +
                    "  \t//调用processFileBytes的地方\n" +
                    "  \tprivate static class FileBytesConsumer implements ClassPathOpener.Consumer {\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public boolean processFileBytes(String name, long lastModified,\n" +
                    "                byte[] bytes)   {\n" +
                    "            return Main.processFileBytes(name, lastModified, bytes);\n" +
                    "        }\n" +
                    "      \t//blablabla...\n" +
                    "    }\n" +
                    "  \t//调用FileBytesConsumer的地方\n" +
                    "  \tprivate static void processOne(String pathname, FileNameFilter filter) {\n" +
                    "        ClassPathOpener opener;\n" +
                    "\n" +
                    "        opener = new ClassPathOpener(pathname, true, filter, new FileBytesConsumer());\n" +
                    "\n" +
                    "        if (opener.process()) {\n" +
                    "          updateStatus(true);\n" +
                    "        }\n" +
                    "    }\n" +
                    "  \t//调用processOne的地方\n" +
                    "  \tprivate static boolean processAllFiles() {\n" +
                    "      \t//blablabla...\n" +
                    "  \t\t// forced in main dex\n" +
                    "        for (int i = 0; i < fileNames.length; i++) {\n" +
                    "\t\t\tprocessOne(fileNames[i], mainPassFilter);\n" +
                    "\t\t}\n" +
                    "      \t//blablabla...\n" +
                    "\t}\n" +
                    "  \t//调用processAllFiles的地方\n" +
                    "  \tprivate static int runMonoDex() throws IOException {\n" +
                    "      \t//blablabla...\n" +
                    "  \t\tif (!processAllFiles()) {\n" +
                    "            return 1;\n" +
                    "        }\n" +
                    "      \t//blablabla...\n" +
                    "\t}\n" +
                    "\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "跟了一圈又跟回来了，但是注意一个变量：`fileNames[i]`，传进去这个变量，是个地址，最终在 `processFileBytes` 中处理后添加到 `libraryDexBuffers` 中，那跟一下这个变量：\n" +
                    "\n" +
                    "```\n" +
                    "public class Main {\n" +
                    "  \tprivate static boolean processAllFiles() {\n" +
                    "      \t//blablabla...\n" +
                    "  \t\tString[] fileNames = args.fileNames;\n" +
                    "      \t//blablabla...\n" +
                    "\t}\n" +
                    "  \tpublic void parse(String[] args) {\n" +
                    "      \t//blablabla...\n" +
                    "    \t}else if(parser.isArg(INPUT_LIST_OPTION + \"=\")) {\n" +
                    "\t\t\tFile inputListFile = new File(parser.getLastValue());\n" +
                    "\t\t\ttry{\n" +
                    "\t\t\t\tinputList = new ArrayList<String>();\n" +
                    "                readPathsFromFile(inputListFile.getAbsolutePath(), inputList);\n" +
                    "            } catch(IOException e) {\n" +
                    "\t\t\t\tSystem.err.println(\"Unable to read input list file: \" + inputListFile.getName());\n" +
                    "\t\t\t\tthrow new UsageException();\n" +
                    "            }\n" +
                    "        } else {\n" +
                    "      \t//blablabla...\n" +
                    "  \t\tfileNames = parser.getRemaining();\n" +
                    "\t\tif(inputList != null && !inputList.isEmpty()) {\n" +
                    "            inputList.addAll(Arrays.asList(fileNames));\n" +
                    "            fileNames = inputList.toArray(new String[inputList.size()]);\n" +
                    "        }\n" +
                    "\t}\n" +
                    "  \t\n" +
                    "  \tpublic static void main(String[] argArray) throws IOException {\n" +
                    "        Arguments arguments = new Arguments();\n" +
                    "        arguments.parse(argArray);\n" +
                    "\n" +
                    "        int result = run(arguments);\n" +
                    "        if (result != 0) {\n" +
                    "            System.exit(result);\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "跟到这里发现是传进来的参数，那我们再看看 gradle 里面传的是什么参数吧，查看 [Dex](https://github.com/yydcdut/android_tools_base/blob/mm/build-system/gradle-core/src/main/groovy/com/android/build/gradle/tasks/Dex.groovy) task :\n" +
                    "\n" +
                    "```\n" +
                    "public class Dex extends BaseTask {\n" +
                    "  \t@InputFiles\n" +
                    "    Collection<File> libraries\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "我们把这个参数打印出来：\n" +
                    "\n" +
                    "```\n" +
                    "afterEvaluate {\n" +
                    "    tasks.matching {\n" +
                    "        it.name.startsWith('dex')\n" +
                    "    }.each { dx ->\n" +
                    "        if (dx.additionalParameters == null) {\n" +
                    "            dx.additionalParameters = []\n" +
                    "        }\n" +
                    "        println dx.libraries\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "打印出来发现是 `build/intermediates/pre-dexed/` 目录里面的 jar 文件，再把 jar 文件解压发现里面就是 dex 文件了。所以 `DexMerger` 的工作就是合并这里的 dex 。\n" +
                    "\n" +
                    "#### 更改编译环境\n" +
                    "\n" +
                    "```\n" +
                    "buildscript {\n" +
                    "\t//...\n" +
                    "    dependencies {\n" +
                    "        classpath 'com.android.tools.build:gradle:2.1.0-alpha3'\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "将 gradle 设置为 2.1.0-alpha3 之后，在项目的 `build.gradle` 中即使没有设置 ` multiDexEnabled true` 也能够编译通过，但是生成的 apk 包依旧是两个 dex ，我想的是可能为了设置 `instantRun` 。\n" +
                    "\n" +
                    "#### 解决 65536\n" +
                    "\n" +
                    "Google MultiDex 解决方案：\n" +
                    "\n" +
                    "在 gradle 中添加 `MultiDex` 的依赖：\n" +
                    "\n" +
                    "```\n" +
                    "dependencies { compile 'com.android.support:MultiDex:1.0.0' }\n" +
                    "```\n" +
                    "\n" +
                    "在 gradle 中配置 `MultiDexEnable` :\n" +
                    "\n" +
                    "```\n" +
                    "android {\n" +
                    "\tbuildToolsVersion \"21.1.0\"\n" +
                    "\tdefaultConfig {\n" +
                    "        // Enabling MultiDex support.\n" +
                    "        MultiDexEnabled true\n" +
                    "  }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "在 AndroidManifest.xml 的 application 中声明:\n" +
                    "\n" +
                    "```\n" +
                    "<application\n" +
                    "  android:name=\"android.support.multidex.MultiDexApplication\">\n" +
                    "<application/>\n" +
                    "```\n" +
                    "\n" +
                    "如果有自己的 Application 了，让其继承于 MultiDexApplication 。\n" +
                    "\n" +
                    "如果继承了其他的 Application ，那么可以重写 `attachBaseContext(Context)`:\n" +
                    "\n" +
                    "```\n" +
                    "@Override \n" +
                    "protected void attachBaseContext(Context base) {\n" +
                    "    super.attachBaseContext(base);\n" +
                    "    MultiDex.install(this);\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "### LinearAlloc\n" +
                    "\n" +
                    "> ERROR/dalvikvm(4620): LinearAlloc exceeded capacity (5242880), last=...\n" +
                    "\n" +
                    "#### LinearAlloc 是什么\n" +
                    "\n" +
                    "LinearAlloc 主要用来管理 Dalvik 中 class 加载时的内存，就是让 App 在执行时减少系统内存的占用。在 App 的安装过程中，系统会运行一个名为 dexopt 的程序为该应用在当前机型中运行做准备。dexopt 使用 LinearAlloc 来存储应用的方法信息。App 在执行前会将 class 读进 LinearAlloc 这个 buffer 中，这个 LinearAlloc 在 Android 2.3 之前是 4M 或 5M ，到 4.0 之后变为 8M 或 16M。因为 5M 实在是太小了，可能还没有 65536 就已经超过 5M 了，什么意思呢，就是只有一个包的情况下也有可能出现 INSTALL_FAILED_DEXOPT ，原因就在于 LinearAlloc。\n" +
                    "\n" +
                    "#### 解决 LinearAlloc\n" +
                    "\n" +
                    "gradle：\n" +
                    "\n" +
                    "```\n" +
                    "afterEvaluate { \n" +
                    "  tasks.matching { \n" +
                    "    it.name.startsWith('dex') \n" +
                    "  }.each { dx -> \n" +
                    "    if (dx.additionalParameters == null) { \n" +
                    "      dx.additionalParameters = []\n" +
                    "    }  \n" +
                    "    dx.additionalParameters += '--set-max-idx-number=48000' \n" +
                    "  } \n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "`--set-max-idx-number=` 用于控制每一个 dex 的最大方法个数。\n" +
                    "\n" +
                    "这个参数在查看 dx.jar 找到：\n" +
                    "\n" +
                    "```\n" +
                    "//blablabla...\n" +
                    "} else if (parser.isArg(\"--set-max-idx-number=\")) { // undocumented test option\n" +
                    "  maxNumberOfIdxPerDex = Integer.parseInt(parser.getLastValue());\n" +
                    "} else if(parser.isArg(INPUT_LIST_OPTION + \"=\")) {\n" +
                    "//blablabla...\n" +
                    "```\n" +
                    "\n" +
                    "更多细节可以查看源码：[Github -- platform_dalvik/Main](https://github.com/yydcdut/platform_dalvik/blob/master/dx/src/com/android/dx/command/dexer/Main.java)\n" +
                    "\n" +
                    "FB 的工程师们曾经还想到过直接修改 LinearAlloc 的\b大小，比如从 5M 修改到 8M： [Under the Hood: Dalvik patch for Facebook for Android](https://www.facebook.com/notes/facebook-engineering/under-the-hood-dalvik-patch-for-facebook-for-android/10151345597798920) 。\n" +
                    "\n" +
                    "## dexopt && dex2oat\n" +
                    "\n" +
                    "![dexopt_dex2oat](http://7xs03u.com1.z0.glb.clouddn.com/dex_dexopt_dex2oat.png)\n" +
                    "\n" +
                    "### dexopt\n" +
                    "\n" +
                    "当 Android 系统安装一个应用的时候，有一步是对 Dex 进行优化，这个过程有一个专门的工具来处理，叫 DexOpt。DexOpt 是在第一次加载 Dex 文件的时候执行的，将 dex 的依赖库文件和一些辅助数据打包成 odex 文件，即 Optimised Dex，存放在 cache/dalvik_cache 目录下。保存格式为 `apk路径 @ apk名 @ classes.dex` 。执行 ODEX 的效率会比直接执行 Dex 文件的效率要高很多。\n" +
                    "\n" +
                    "更多可查看 [Dalvik Optimization and Verification With *dexopt*](http://www.netmite.com/android/mydroid/dalvik/docs/dexopt.html) 。\n" +
                    "\n" +
                    "### dex2oat\n" +
                    "\n" +
                    "Android Runtime 的 dex2oat 是将 dex 文件编译成 oat 文件。而 oat 文件是 elf 文件，是可以在本地执行的文件，而 Android Runtime 替换掉了虚拟机读取的字节码转而用本地可执行代码，这就被叫做 AOT(ahead-of-time)。dex2oat 对所有 apk 进行编译并保存在 dalvik-cache 目录里。PackageManagerService 会持续扫描安装目录，如果有新的 App 安装则马上调用 dex2oat 进行编译。\n" +
                    "\n" +
                    "更多可查看  [Android运行时ART简要介绍和学习计划](http://blog.csdn.net/luoshengyang/article/details/39256813) 。\n" +
                    "\n" +
                    "## NoClassDefFoundError\n" +
                    "\n" +
                    "现在 INSTALL_FAILED_DEXOPT 问题是解决了，但是有时候编译完运行的时候一打开 App 就 crash 了，查看 log 发现是某个类找不到引用。\n" +
                    "\n" +
                    "### Build Tool 是如何分包的\n" +
                    "\n" +
                    "为什么会这样呢？是因为 build-tool 在分包的时候只判断了直接引用类。什么是直接引用类呢？举个栗子：\n" +
                    "\n" +
                    "```\n" +
                    "public class MainActivity extends Activity {\n" +
                    "    protected void onCreate(Bundle savedInstanceState) {\n" +
                    "        DirectReferenceClass test = new DirectReferenceClass();\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "public class DirectReferenceClass {\n" +
                    "    public DirectReferenceClass() {\n" +
                    "        InDirectReferenceClass test = new InDirectReferenceClass();\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "public class InDirectReferenceClass {\n" +
                    "    public InDirectReferenceClass() {\n" +
                    "\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "上面有 MainActivity、DirectReferenceClass 、InDirectReferenceClass 三个类，其中 DirectReferenceClass 是 MainActivity 的直接引用类，InDirectReferenceClass 是 DirectReferenceClass 的直接引用类。而 InDirectReferenceClass 是 MainActivity 的间接引用类(即直接引用类的所有直接引用类)。\n" +
                    "\n" +
                    "如果我们代码是这样写的：\n" +
                    "\n" +
                    "```\n" +
                    "public class HelloMultiDexApplication extends Application {\n" +
                    "    @Override\n" +
                    "    protected void attachBaseContext(Context base) {\n" +
                    "        super.attachBaseContext(base);\n" +
                    "        DirectReferenceClass test = new DirectReferenceClass();\n" +
                    "        MultiDex.install(this);\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "这样直接就 crash 了。同理还要单例模式中拿到单例之后直接调用某个方法返回的是另外一个对象，并非单例对象。\n" +
                    "\n" +
                    "build tool 的分包操作可以查看 sdk 中 build-tools 文件夹下的 `mainDexClasses` 脚本，同时还发现了 `mainDexClasses.rules` 文件，该文件是主 dex 的匹配规则。该脚本要求输入一个文件组（包含编译后的目录或jar包），然后分析文件组中的类并写入到–output所指定的文件中。实现原理也不复杂，主要分为三步：\n" +
                    "\n" +
                    "1. 环境检查，包括传入参数合法性检查，路径检查以及proguard环境检测等。\n" +
                    "2. 使用mainDexClasses.rules规则，通过Proguard的shrink功能，裁剪无关类，生成一个tmp.jar包。\n" +
                    "3. 通过生成的tmp jar包，调用MainDexListBuilder类生成主dex的文件列表。\n" +
                    "\n" +
                    "更多细节可以查看源码：[Github -- platform_dalvik/MainDexListBuilder](https://github.com/yydcdut/platform_dalvik/blob/0be69b5268823139b04411f6a8e15cb58f54ddae/dx/src/com/android/multidex/MainDexListBuilder.java)\n" +
                    "\n" +
                    "### Gradle 打包流程中是如何分包的\n" +
                    "\n" +
                    "在项目中，可以直接运行 gradle 的 task 。\n" +
                    "\n" +
                    "*  `collect{flavor}{buildType}MultiDexComponents` Task 。这个 task 是获取 AndroidManifest.xml 中 Application 、Activity 、Service 、 Receiver 、 Provider 等相关类，以及 Annotation ，之后将内容写到 `build/intermediates/multi-dex/{flavor}/{buildType}/maindexlist.txt` 文件中去。\n" +
                    "\n" +
                    "\n" +
                    "* `packageAll{flavor}DebugClassesForMultiDex` Task 。该 task 是将所有类打包成 jar 文件存在 `build/intermediates/multi-dex/{flavor}/debug/allclasses.jar` 。 当 BuildType 为 Release 的时候，执行的是 `proguard{flavor}Release` Task，该 task 将 proguard 混淆后的类打包成 jar 文件存在 `build/intermediates/classes-proguard/{flavor}/release/classes.jar`\n" +
                    "* `shrink{flavor}{buildType}MultiDexComponents` Task 。该 task 会根据 maindexlist.txt 生成  componentClasses.jar ，该 jar 包里面就只有 maindexlist.txt 里面的类，该 jar 包的位置在 `build/intermediates/multi-dex/{flavor}/{buildType}/componentClasses.jar`\n" +
                    "* `create{flavor}{buildType}MainDexClassList` Task 。该 task 会根据生成的 componentClasses.jar 去找这里面的所有的 class 中直接依赖的 class ，然后将内容写到 `build/intermediates/multi-dex/{flavor}/{buildType}/maindexlist.txt` 中。最终这个文件里面列出来的类都会被分配到第一个 dex 里面。\n" +
                    "\n" +
                    "更多细节可以查看源码：[Github -- android_tools_base](https://github.com/yydcdut/android_tools_base/blob/mm/build-system/gradle-core/src/main/groovy/com/android/build/gradle/internal/tasks/multidex/CreateManifestKeepList.groovy) 。\n" +
                    "\n" +
                    "### 解决 NoClassDefFoundError\n" +
                    "\n" +
                    "gradle :\n" +
                    "\n" +
                    "```\n" +
                    "afterEvaluate { \n" +
                    "  tasks.matching { \n" +
                    "    it.name.startsWith('dex') \n" +
                    "  }.each { dx -> \n" +
                    "    if (dx.additionalParameters == null) { \n" +
                    "      dx.additionalParameters = []\n" +
                    "    }  \n" +
                    "    dx.additionalParameters += '--set-max-idx-number=48000' \n" +
                    "    dx.additionalParameters += \"--main-dex-list=$projectDir/multidex.keep\".toString()\n" +
                    "  } \n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "`--main-dex-list=` 参数是一个类列表的文件，在该文件中的类会被打包在第一个 dex 中。\n" +
                    "\n" +
                    "multidex.keep 里面列上需要打包到第一个 dex 的 class 文件，注意，如果需要混淆的话需要写混淆之后的 class 。\n" +
                    "\n" +
                    "## Application Not Responding\n" +
                    "\n" +
                    "因为第一次运行（包括清除数据之后）的时候需要 dexopt ，然而 dexopt 是一个比较耗时的操作，同时 `MultiDex.install()` 操作是在 `Application.attachBaseContext()` 中进行的，占用的是UI线程。那么问题来了，当我的第二个包、第三个包很大的时候，程序就阻塞在 `MultiDex.install()` 这个地方了，一旦超过规定时间，那就 ANR 了。那怎么办？放子线程？如果 Application 有一些初始化操作，到初始化操作的地方的时候都还没有完成 install + dexopt 的话，那不是又 NoClassDefFoundError 了吗？同时 ClassLoader 放在哪个线程都让主线程挂起。好了，那在 multidex.keep 的加上相关的所有的类吧。好像这样成了，但是第一个 dex 又大起来了，而且如果用户操作快，还没完成 install + dexopt 但是已经把 App 所以界面\b都打开了一遍。。。虽然这不现实。。\n" +
                    "\n" +
                    "### 微信加载方案\n" +
                    "\n" +
                    "首次加载在地球中页中, 并用线程去加载（但是 5.0 之前加载 dex 时还是会挂起主线程一段时间（不是全程都挂起））。\n" +
                    "\n" +
                    "* dex 形式\n" +
                    "\n" +
                    "  微信是将包放在 `assets` 目录下的，在加载 Dex 的代码时，实际上传进去的是 zip，在加载前需要验证 MD5，确保所加载的 Dex 没有被篡改。\n" +
                    "\n" +
                    "* dex 类分包规则\n" +
                    "\n" +
                    "  分包规则即将所有 Application、ContentProvider 以及所有 export 的 Activity、Service 、Receiver 的间接依赖集都必须放在主 dex。\n" +
                    "\n" +
                    "* 加载 dex 的方式\n" +
                    "\n" +
                    "  加载逻辑这边主要判断是否已经 dexopt，若已经 dexopt，即放在 attachBaseContext 加载，反之放于地球中用线程加载。怎么判断？因为在微信中，若判断 revision 改变，即将 dex 以及 dexopt 目录清空。只需简单判断两个目录 dex 名称、数量是否与配置文件的一致。\n" +
                    "\n" +
                    "总的来说，这种方案用户体验较好，缺点在于太过复杂，每次都需重新扫描依赖集，而且使用的是比较大的间接依赖集。\n" +
                    "\n" +
                    "### Facebook 加载方案\n" +
                    "\n" +
                    "Facebook的思路是将 `Multi\bDex.install()` 操作放在另外一个经常进行的。\n" +
                    "\n" +
                    "* dex 形式\n" +
                    "\n" +
                    "  与微信相同。\n" +
                    "\n" +
                    "* dex 类分包规则\n" +
                    "\n" +
                    "  Facebook 将加载 dex 的逻辑单独放于一个单独的 nodex 进程中。\n" +
                    "\n" +
                    "```\n" +
                    "  <activity \n" +
                    "  android:exported=\"false\"\n" +
                    "  android:process=\":nodex\"\n" +
                    "      android:name=\"com.facebook.nodex.startup.splashscreen.NodexSplashActivity\">\n" +
                    "```\n" +
                    "\n" +
                    "  所有的依赖集为 Application、NodexSplashActivity 的间接依赖集即可。\n" +
                    "\n" +
                    "* 加载 dex 的方式\n" +
                    "\n" +
                    "  因为 `NodexSplashActivity` 的 intent-filter 指定为 `Main` 和 `LAUNCHER` ，所以一打开 App 首先拉起 nodex 进程，然后打开 `NodexSplashActivity` 进行 `MultiDex.install()` 。如果已经进行了 dexpot 操作的话就直接跳转主界面，没有的话就等待 dexpot 操作完成再跳转主界面。\n" +
                    "\n" +
                    "这种方式好处在于依赖集非常简单，同时首次加载 dex 时也不会卡死。但是它的缺点也很明显，即每次启动主进程时，都需先启动 nodex 进程。尽管 nodex 进程逻辑非常简单，这也需100ms以上。\n" +
                    "\n" +
                    "### 美团加载方案\n" +
                    "\n" +
                    "* dex 形式\n" +
                    "\n" +
                    "  在 gradle 生成 dex 文件的这步中，自定义一个 task 来干预 dex 的生产过程，从而产生多个 dex 。\n" +
                    "\n" +
                    "```\n" +
                    "  tasks.whenTaskAdded { task ->\n" +
                    "      if (task.name.startsWith('proguard') && (task.name.endsWith('Debug') || task.name.endsWith('Release'))) {\n" +
                    "          task.doLast {\n" +
                    "              makeDexFileAfterProguardJar();\n" +
                    "          }\n" +
                    "          task.doFirst {\n" +
                    "              delete \"${project.buildDir}/intermediates/classes-proguard\";\n" +
                    "\n" +
                    "              String flavor = task.name.substring('proguard'.length(), task.name.lastIndexOf(task.name.endsWith('Debug') ? \"Debug\" : \"Release\"));\n" +
                    "              generateMainIndexKeepList(flavor.toLowerCase());\n" +
                    "          }\n" +
                    "      } else if (task.name.startsWith('zipalign') && (task.name.endsWith('Debug') || task.name.endsWith('Release'))) {\n" +
                    "          task.doFirst {\n" +
                    "              ensureMultiDexInApk();\n" +
                    "          }\n" +
                    "      }\n" +
                    "  }\n" +
                    "```\n" +
                    "\n" +
                    "* dex 类分包规则\n" +
                    "\n" +
                    "  把 Service、Receiver、Provider 涉及到的代码都放到主 dex 中，而把 Activity 涉及到的代码进行了一定的拆分，把首页 Activity、Laucher Activity 、欢迎页的 Activity 、城市列表页 Activity 等所依赖的 class 放到了主 dex 中，把二级、三级页面的 Activity 以及业务频道的代码放到了第二个  dex 中，为了减少人工分析 class 的依赖所带了的不可维护性和高风险性，美团编写了一个能够自动分析 class 依赖的脚本， 从而能够保证\u001D主 dex 包含 class 以及他们所依赖的所有 class 都在其内，这样这个脚本就会在打包之前自动分析出启动到主 dex 所涉及的所有代码，保证主 dex 运行正常。\n" +
                    "\n" +
                    "* 加载 dex 的方式\n" +
                    "\n" +
                    "  通过分析 Activity 的启动过程，发现 Activity 是由 ActivityThread 通过 Instrumentation 来启动的，那么是否可以在 Instrumentation 中做一定的手脚呢？通过分析代码 ActivityThread 和 Instrumentation 发现，Instrumentation 有关 Activity 启动相关的方法大概有：execStartActivity、 newActivity 等等，这样就可以在这些方法中添加代码逻辑进行判断这个 class 是否加载了，如果加载则直接启动这个 Activity，如果没有加载完成则启动一个等待的 Activity 显示给用户，然后在这个 Activity 中等待后台第二个 dex 加载完成，完成后自动跳转到用户实际要跳转的 Activity；这样在代码充分解耦合，以及每个业务代码能够做到颗粒化的前提下，就做到第二个 dex 的按需加载了。\n" +
                    "\n" +
                    "美团的这种方式对主 dex 的要求非常高，因为第二个 dex 是等到需要的时候再去加载。重写Instrumentation 的 execStartActivity 方法，hook 跳转 Activity 的总入口做判断，如果当前第二个 dex 还没有加载完成，就弹一个 loading Activity等待加载完成。\n" +
                    "\n" +
                    "### \b综合加载方案\n" +
                    "\n" +
                    "微信的方案需要将 dex 放于 `assets` 目录下，在打包的时候太过负责；Facebook 的方案每次进入都是开启一个 nodex 进程，而我们希望节省资源的同时快速打开 App；美团的方案确实很 hack，但是对于项目已经很庞大，耦合度又比较高的情况下并不适合。所以这里尝试结合三个方案，针对自己的项目来进行优化。\n" +
                    "\n" +
                    "* dex 形式\n" +
                    "\n" +
                    "  第一，为了能够继续支持 Android 2.x 的机型，我们将每个包的方法数控制在 48000 个，这样最后分出来 dex 包大约在 5M 左右；第二，为了防止 NoClassDefFoundError 的情况，我们找出来启动页、引导页、首页比较在意的一些类，比如 Fragment 等（因为在生成 maindexlist.txt 的时候只会找 Activity 的直接引用，比如首页 Activity 直接引用 AFragemnt，但是 AFragment 的引用并没有去找）。\n" +
                    "\n" +
                    "* dex 类分包规则\n" +
                    "\n" +
                    "  第一个包放 Application、Android四大组件以及启动页、引导页、首页的直接引用的 Fragment 的引用类，还放了推送消息过来点击 Notification 之后要展示的 Activity 中的 Fragment 的引用类。\n" +
                    "\n" +
                    "  Fragment 的引用类是写了一个脚本，输入需要找的类然后将这些引用类写到 multidex.keep 文件中，如果是 debug 的就直接在生成的 jar 里面找，如果是 release 的话就通过 mapping.txt 找，找不到的话再去 jar 里面找，所以在 gradle 打包的过程中我们人为干扰一下：\n" +
                    "\n" +
                    "```\n" +
                    "  tasks.whenTaskAdded { task ->\n" +
                    "      if (task.name.startsWith(\"create\") && task.name.endsWith(\"MainDexClassList\")) {\n" +
                    "          task.doLast {\n" +
                    "              def flavorAndBuildType = task.name.substring(\"create\".length(), task.name.length() - \"MainDexClassList\".length())\n" +
                    "              autoSplitDex.configure {\n" +
                    "                  description = flavorAndBuildType\n" +
                    "              }\n" +
                    "              autoSplitDex.execute()\n" +
                    "          }\n" +
                    "      } \n" +
                    "  }\n" +
                    "```\n" +
                    "\n" +
                    "  详细代码可见：[Github — PhotoNoter/gradle](https://github.com/yydcdut/PhotoNoter/blob/master/app/build.gradle)\n" +
                    "\n" +
                    "* 加载 dex 的方式\n" +
                    "\n" +
                    "  在防止 ANR 方面，我们采用了 Facebook 的思路。但是稍微有一点区别，差别在于我们并不在一开启 App 的时候就去起进程，而是一开启 App 的时候在主进程里面判断是否 dexopt 过没，没有的话再去起另外的进程的 Activity 专门做 dexopt 操作 。一旦拉起了去做 dexopt 的进程，那么让主进程进入一个死循环，一直等到 dexopt 进程结束再结束死循环往下走。那么问题来了，第一，主进程进入死循环会 ANR 吗？第二，如何判断是否 dexopt 过；第三，为了界面友好，dexopt 的进程该怎么做；第四，主进程怎么知道 dexopt 进程结束了，也就是怎么去做进程间通信。\n" +
                    "\n" +
                    "  一个一个问题的解决，先第一个：因为当拉起 dexopt 进程之后，我们在 dexopt 进程的 Activity 中进行 `MultiDex.install()` 操作，此时主进程\b不再是前台进程了，所以不会 ANR 。\n" +
                    "\n" +
                    "  第二个问题：因为第一次启动是什么数据都没有的，那么我们就建立一个 `SharedPreference` ，启动的时候先去从这里获取数据，如果没有数据那么也就是没有 dexopt 过，如果有数据那么肯定是 dexopt 过的，但是这个 `SharedPreference` 我们得保证我们的程序只有这个地方可以修改，其他地方不能修改。\n" +
                    "\n" +
                    "  第三个问题：因为 App 的启动也是一张图片，所以在 dexopt 的 Activity 的 layout 中，我们就把这张图片设置上去就好了，当关闭 dexopt 的 Activity 的时候，我们得关闭 Activity 的动画。同时为了不让 dexopt 进程发生 ANR ，我们将 `MultiDex.install()` 过程放在了子线程中进行。\n" +
                    "\n" +
                    "  第四个问题：Linux 的进程间通信的方式有很多，Android 中还有 Binder 等，那么我们这里采用哪种方式比较好呢？首先想到的是既然 dexopt 进程结束了自然在主进程的死循环中去判断 dexopt 进程是否存在。但是在实际操作中发现，dexopt 虽然已经退出了，但是进程并没有马上被回收掉，所以这个方法走不通。那么用 Broadcast 广播可以吗？可是可以，但是增加了 Application 的负担，在拉起 dexopt 进程前还得注册一个动态广播，接收到广播之后还得注销掉，所以这个也没有采用。那么最终采用的方式是判断文件是否存在，在拉起 dexopt 进程前在某个安全的地方建立一个临时文件，然后死循环判断这个文件是否存在，在 dexopt 进程结束的时候删除这个临时文件，那么在主进程的死循环中发现此文件不存在了，就直接跳出循环，继续 Application 初始化操作。\n" +
                    "\n" +
                    "```\n" +
                    "  public class NoteApplication extends Application {\n" +
                    "  @Override\n" +
                    "      protected void attachBaseContext(Context base) {\n" +
                    "          super.attachBaseContext(base);\n" +
                    "          //开启dex进程的话也会进入application\n" +
                    "          if (isDexProcess()) {\n" +
                    "              return;\n" +
                    "          }\n" +
                    "          doInstallBeforeLollipop();\n" +
                    "          MultiDex.install(this);\n" +
                    "      }\n" +
                    "\n" +
                    "      @Override\n" +
                    "      public void onCreate() {\n" +
                    "          super.onCreate();\n" +
                    "          if (isDexProcess()) {\n" +
                    "              return;\n" +
                    "          }\n" +
                    "        //其他初始化\n" +
                    "      }\n" +
                    "\n" +
                    "    private void doInstallBeforeLollipop() {\n" +
                    "          //满足3个条件，1.第一次安装开启，2.主进程，3.API<21(因为21之后ART的速度比dalvik快接近10倍(毕竟5.0之后的手机性能也要好很多))\n" +
                    "          if (isAppFirstInstall() && !isDexProcessOrOtherProcesses() && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {\n" +
                    "              try {\n" +
                    "                  createTempFile();\n" +
                    "                  startDexProcess();\n" +
                    "                  while (true) {\n" +
                    "                      if (existTempFile()) {\n" +
                    "                          try {\n" +
                    "                              Thread.sleep(50);\n" +
                    "                          } catch (InterruptedException e) {\n" +
                    "                              e.printStackTrace();\n" +
                    "                          }\n" +
                    "                      } else {\n" +
                    "                          setAppNoteFirstInstall();\n" +
                    "                          break;\n" +
                    "                      }\n" +
                    "                  }\n" +
                    "              } catch (IOException e) {\n" +
                    "                  e.printStackTrace();\n" +
                    "              }\n" +
                    "          }\n" +
                    "      }\n" +
                    "```\n" +
                    "\n" +
                    "  详细代码可见：[Github — PhotoNoter/NoteApplication](https://github.com/yydcdut/PhotoNoter/blob/master/app/src/main/java/com/yydcdut/note/NoteApplication.java)\n" +
                    "\n" +
                    "  \u200B\n" +
                    "\n" +
                    "总的来说，这种方式好处在于依赖集非常简单，同时它的集成方式也是非常简单，我们无须去修改与加载无关的代码。但是当没有启动过 App 的时候，被推送全家桶唤醒或者收到了广播，虽然这里都是没有界面的过程，但是运用了这种加载方式的话会弹出 dexopt 进程的 Activity，用户看到会一脸懵比的。\n" +
                    "\n" +
                    "## 坑\n" +
                    "\n" +
                    "### Too many classes in --main-dex-list\n" +
                    "\n" +
                    "> UNEXPECTED TOP-LEVEL EXCEPTION:com.android.dex.DexException: Too many classes in --main-dex-list, main dex capacity exceeded  at com.android.dx.command.dexer.Main.processAllFiles(Main.java:494)  at com.android.dx.command.dexer.Main.runMultiDex(Main.java:332)  at com.android.dx.command.dexer.Main.run(Main.java:243)  at com.android.dx.command.dexer.Main.main(Main.java:214)  at com.android.dx.command.Main.main(Main.java:106)\n" +
                    "\n" +
                    "通过 sdk 的 `mainDexClasses.rules` 知道主 dex 里面会有 Application、Activity、Service、Receiver、Provider、Instrumentation、BackupAgent 和 Annotation。当这些类以及直接引用类比较多的时候，都要塞进主 dex ，就引发了 main dex capacity exceeded build error 。\n" +
                    "\n" +
                    "为了解决这个问题，当执行 `Create{flavor}{buildType}ManifestKeepList` task 之前将其中的 `activity` 去掉，之后会发现 /build/intermediates/multi_dex/{flavor}/{buildType}/manifest_keep.txt 文件中已经没有 Activity 相关的类了。\n" +
                    "\n" +
                    "```\n" +
                    "def patchKeepSpecs() {\n" +
                    "    def taskClass = \"com.android.build.gradle.internal.tasks.multidex.CreateManifestKeepList\";\n" +
                    "    def clazz = this.class.classLoader.loadClass(taskClass)\n" +
                    "    def keepSpecsField = clazz.getDeclaredField(\"KEEP_SPECS\")\n" +
                    "    keepSpecsField.setAccessible(true)\n" +
                    "    def keepSpecsMap = (Map) keepSpecsField.get(null)\n" +
                    "    if (keepSpecsMap.remove(\"activity\") != null) {\n" +
                    "        println \"KEEP_SPECS patched: removed 'activity' root\"\n" +
                    "    } else {\n" +
                    "        println \"Failed to patch KEEP_SPECS: no 'activity' root found\"\n" +
                    "    }\n" +
                    "}\n" +
                    "\n" +
                    "patchKeepSpecs()\n" +
                    "```\n" +
                    "\n" +
                    "详细可以看 `CreateManifestKeepList` 的源码：[Github -- CreateManifestKeepList](https://github.com/yydcdut/android_tools_base/blob/mm/build-system/gradle-core/src/main/groovy/com/android/build/gradle/internal/tasks/multidex/CreateManifestKeepList.groovy)\n" +
                    "\n" +
                    "### Too many classes in --main-dex-list\n" +
                    "\n" +
                    "没错，还是 Too many classes in --main-dex-list 的错误。在美团的自动拆包中讲到：\n" +
                    "\n" +
                    "> 实际应用中我们还遇到另外一个比较棘手的问题， 就是Field的过多的问题，Field过多是由我们目前采用的代码组织结构引入的，我们为了方便多业务线、多团队并发协作的情况下开发，我们采用的aar的方式进行开发，并同时在aar依赖链的最底层引入了一个通用业务aar，而这个通用业务aar中包含了很多资源，而ADT14以及更高的版本中对Library资源处理时，Library的R资源不再是static final的了，[详情请查看google官方说明](http://tools.android.com/tips/non-constant-fields)，这样在最终打包时Library中的R没法做到内联，这样带来了R field过多的情况，导致需要拆分多个Secondary DEX，为了解决这个问题我们采用的是在打包过程中利用脚本把Libray中R field（例如ID、Layout、Drawable等）的引用替换成常量，然后删去Library中R.class中的相应Field。\n" +
                    "\n" +
                    "同样，hu关于这个问题可以参考这篇大神的文章：[当Field邂逅65535](http://jiajixin.cn/2015/10/21/field-65535/) 。\n" +
                    "\n" +
                    "### DexException: Library dex files are not supported in multi-dex mode\n" +
                    "\n" +
                    "> com.android.dex.DexException: Library dex files are not supported in multi-dex mode\n" +
                    "> \u200B at com.android.dx.command.dexer.Main.runMultiDex(Main.java:322)    \n" +
                    "> \u200B at com.android.dx.command.dexer.Main.run(Main.java:228)\n" +
                    "> \u200B at com.android.dx.command.dexer.Main.main(Main.java:199)\n" +
                    "> \u200B at com.android.dx.command.Main.main(Main.java:103)\n" +
                    "\n" +
                    "解决：\n" +
                    "\n" +
                    "```\n" +
                    "android {\n" +
                    "    dexOptions {\n" +
                    "        preDexLibraries = false\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "### OutOfMemoryError: Java heap space\n" +
                    "\n" +
                    "> UNEXPECTED TOP-LEVEL ERROR:\n" +
                    "> \u200B  java.lang.OutOfMemoryError: Java heap space\n" +
                    "\n" +
                    "解决：\n" +
                    "\n" +
                    "```\n" +
                    "android {\n" +
                    "    dexOptions {\n" +
                    "        javaMaxHeapSize \"2g\"\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "## MultiDex.install(Context)\n" +
                    "\n" +
                    "回过头来我们看看 `MultiDex.install()` 做了一些什么：\n" +
                    "\n" +
                    "```\n" +
                    "public final class MultiDex {\n" +
                    "\n" +
                    "    static {\n" +
                    "        //secondary-dexes的位置：/code_cache/secondary-dexes\n" +
                    "        SECONDARY_FOLDER_NAME = \"code_cache\" + File.separator + \"secondary-dexes\";\n" +
                    "        installedApk = new HashSet();\n" +
                    "        IS_VM_MULTIDEX_CAPABLE = isVMMultidexCapable(System.getProperty(\"java.vm.version\"));\n" +
                    "    } \n" +
                    "\n" +
                    "    public static void install(Context context) {\n" +
                    "        Log.i(\"MultiDex\", \"install\");\n" +
                    "        if(IS_VM_MULTIDEX_CAPABLE) {//针对ART\n" +
                    "            Log.i(\"MultiDex\", \"VM has multidex support, MultiDex support library is disabled.\");\n" +
                    "        } else if(VERSION.SDK_INT < 4) {\n" +
                    "            throw new RuntimeException(\"Multi dex installation failed. SDK \" + VERSION.SDK_INT + \" is unsupported. Min SDK version is \" + 4 + \".\");\n" +
                    "        } else {\n" +
                    "            try {\n" +
                    "                ApplicationInfo e = getApplicationInfo(context);\n" +
                    "                if(e == null) {\n" +
                    "                    return;\n" +
                    "                }\n" +
                    "\n" +
                    "                Set var2 = installedApk;\n" +
                    "                synchronized(installedApk) {\n" +
                    "                    String apkPath = e.sourceDir;\n" +
                    "                    //installedApk的类型是：Set<String>，如果这个apk已经安装，则不重复安装   \n" +
                    "                    if(installedApk.contains(apkPath)) {\n" +
                    "                        return;\n" +
                    "                    }\n" +
                    "\n" +
                    "                    installedApk.add(apkPath);\n" +
                    "                    if(VERSION.SDK_INT > 20) {\n" +
                    "                        Log.w(\"MultiDex\", \"MultiDex is not guaranteed to work in SDK version \" + VERSION.SDK_INT + \": SDK version higher than \" + 20 + \" should be backed by \" + \"runtime with built-in multidex capabilty but it\\'s not the \" + \"case here: java.vm.version=\\\"\" + System.getProperty(\"java.vm.version\") + \"\\\"\");\n" +
                    "                    }\n" +
                    "          //类加载器，PathClassLoader\n" +
                    "                    ClassLoader loader;\n" +
                    "                    try {\n" +
                    "                        loader = context.getClassLoader();\n" +
                    "                    } catch (RuntimeException var9) {\n" +
                    "                        Log.w(\"MultiDex\", \"Failure while trying to obtain Context class loader. Must be running in test mode. Skip patching.\", var9);\n" +
                    "                        return;\n" +
                    "                    }\n" +
                    "\n" +
                    "                    if(loader == null) {\n" +
                    "                        Log.e(\"MultiDex\", \"Context class loader is null. Must be running in test mode. Skip patching.\");\n" +
                    "                        return;\n" +
                    "                    }\n" +
                    "\n" +
                    "                    try {\n" +
                    "                        //删除以前的dex\n" +
                    "                        clearOldDexDir(context);\n" +
                    "                    } catch (Throwable var8) {\n" +
                    "                        Log.w(\"MultiDex\", \"Something went wrong when trying to clear old MultiDex extraction, continuing without cleaning.\", var8);\n" +
                    "                    }\n" +
                    "          //dex将会输出到/data/data/{packagename}/code_cache/secondary-dexes目录。  \n" +
                    "                    File dexDir = new File(e.dataDir, SECONDARY_FOLDER_NAME);\n" +
                    "                    List files = MultiDexExtractor.load(context, e, dexDir, false);\n" +
                    "                  //blablabla......\n" +
                    "                }\n" +
                    "            } catch (Exception var11) {\n" +
                    "                Log.e(\"MultiDex\", \"Multidex installation failure\", var11);\n" +
                    "                throw new RuntimeException(\"Multi dex installation failed (\" + var11.getMessage() + \").\");\n" +
                    "            }\n" +
                    "\n" +
                    "            Log.i(\"MultiDex\", \"install done\");\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "之前都是准备工作，然后进入 `MultiDexExtractor.load()` 看一下：\n" +
                    "\n" +
                    "```\n" +
                    "final class MultiDexExtractor {\n" +
                    "  static List<File> load(Context context, ApplicationInfo applicationInfo, File dexDir, boolean forceReload) throws IOException {\n" +
                    "        //dexDir地址是/data/data/{packagename}/code_cache/secondary-dexes\n" +
                    "        Log.i(\"MultiDex\", \"MultiDexExtractor.load(\" + applicationInfo.sourceDir + \", \" + forceReload + \")\");\n" +
                    "        //applicationInfo.sourceDir地址是/data/app/{packagename}-1.apk\n" +
                    "        File sourceApk = new File(applicationInfo.sourceDir);\n" +
                    "        //Crc校验\n" +
                    "        long currentCrc = getZipCrc(sourceApk);\n" +
                    "        List files;\n" +
                    "        //isModified()判断apk是否被修改过\n" +
                    "        if(!forceReload && !isModified(context, sourceApk, currentCrc)) {\n" +
                    "            try {\n" +
                    "                // 加载已经存在的文件，如果有的文件不存在，或者不是zip文件，则会抛出异常  \n" +
                    "                files = loadExistingExtractions(context, sourceApk, dexDir);\n" +
                    "            } catch (IOException var9) {\n" +
                    "                Log.w(\"MultiDex\", \"Failed to reload existing extracted secondary dex files, falling back to fresh extraction\", var9);\n" +
                    "                // 从apk中提取出多dex，然后将这些dex逐个打包为zip文件，最终返回提取出来的zip文件列表\n" +
                    "                files = performExtractions(sourceApk, dexDir);\n" +
                    "        // getTimeStamp方法中调用的是sourceApk.lastModified()方法  \n" +
                    "                // putStoredApkInfo方法存储apk的信息：时间戳、crc值、apk中dex的总个数  \n" +
                    "                putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);\n" +
                    "            }\n" +
                    "        } else {\n" +
                    "            Log.i(\"MultiDex\", \"Detected that extraction must be performed.\");\n" +
                    "            files = performExtractions(sourceApk, dexDir);\n" +
                    "            putStoredApkInfo(context, getTimeStamp(sourceApk), currentCrc, files.size() + 1);\n" +
                    "        }\n" +
                    "\n" +
                    "        Log.i(\"MultiDex\", \"load found \" + files.size() + \" secondary dex files\");\n" +
                    "        return files;\n" +
                    "    }\n" +
                    "\n" +
                    "    private static boolean isModified(Context context, File archive, long currentCrc) {\n" +
                    "        SharedPreferences prefs = getMultiDexPreferences(context);\n" +
                    "        return prefs.getLong(\"timestamp\", -1L) != getTimeStamp(archive) || prefs.getLong(\"crc\", -1L) != currentCrc;\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "来看一下 `performExtractions()`：\n" +
                    "\n" +
                    "```\n" +
                    "final class MultiDexExtractor {\n" +
                    "  private static List<File> performExtractions(File sourceApk, File dexDir) throws IOException {\n" +
                    "        //extractedFilePrefix的内容是{packagename}-1.apk.classes\n" +
                    "        String extractedFilePrefix = sourceApk.getName() + \".classes\";\n" +
                    "        //准备文件夹\n" +
                    "        prepareDexDir(dexDir, extractedFilePrefix);\n" +
                    "        ArrayList files = new ArrayList();\n" +
                    "        ZipFile apk = new ZipFile(sourceApk);\n" +
                    "\n" +
                    "        try {\n" +
                    "            int e = 2;\n" +
                    "\n" +
                    "            for(ZipEntry dexFile = apk.getEntry(\"classes\" + e + \".dex\"); dexFile != null; dexFile = apk.getEntry(\"classes\" + e + \".dex\")) {//那第e个dex\n" +
                    "                //{packagename}-1.apk.classes2.zip\n" +
                    "                String fileName = extractedFilePrefix + e + \".zip\";\n" +
                    "                //extractedFile地址是/data/data/{packagename}/code_cache/secondary-dexes/com.yydcdut.note-1.apk.classes2.zip\n" +
                    "                File extractedFile = new File(dexDir, fileName);\n" +
                    "                files.add(extractedFile);\n" +
                    "                Log.i(\"MultiDex\", \"Extraction is needed for file \" + extractedFile);\n" +
                    "                int numAttempts = 0;\n" +
                    "                boolean isExtractionSuccessful = false;\n" +
                    "\n" +
                    "                while(numAttempts < 3 && !isExtractionSuccessful) {\n" +
                    "                    ++numAttempts;\n" +
                    "                    //提取apk中的dex文件，然后打包成一个zip文\n" +
                    "                    extract(apk, dexFile, extractedFile, extractedFilePrefix);\n" +
                    "                    //验证提取的文件是否是一个zip文件。  \n" +
                    "                    isExtractionSuccessful = verifyZipFile(extractedFile);\n" +
                    "                    Log.i(\"MultiDex\", \"Extraction \" + (isExtractionSuccessful?\"success\":\"failed\") + \" - length \" + extractedFile.getAbsolutePath() + \": \" + extractedFile.length());\n" +
                    "                    if(!isExtractionSuccessful) {\n" +
                    "                        extractedFile.delete();\n" +
                    "                        if(extractedFile.exists()) {\n" +
                    "                            Log.w(\"MultiDex\", \"Failed to delete corrupted secondary dex \\'\" + extractedFile.getPath() + \"\\'\");\n" +
                    "                        }\n" +
                    "                    }\n" +
                    "                }\n" +
                    "\n" +
                    "                if(!isExtractionSuccessful) {\n" +
                    "                    throw new IOException(\"Could not create zip file \" + extractedFile.getAbsolutePath() + \" for secondary dex (\" + e + \")\");\n" +
                    "                }\n" +
                    "\n" +
                    "                ++e;\n" +
                    "            }\n" +
                    "        } finally {\n" +
                    "            try {\n" +
                    "                apk.close();\n" +
                    "            } catch (IOException var16) {\n" +
                    "                Log.w(\"MultiDex\", \"Failed to close resource\", var16);\n" +
                    "            }\n" +
                    "\n" +
                    "        }\n" +
                    "\n" +
                    "        return files;\n" +
                    "    }\n" +
                    "\n" +
                    "    private static void prepareDexDir(File dexDir, final String extractedFilePrefix) throws IOException {\n" +
                    "        //dexDir的地址是：/data/data/{packagename}/code_cache/secondary-dexes\n" +
                    "        //extractedFilePrefix的内容是data/app/{packagename}-1.apk.classes\n" +
                    "        File cache = dexDir.getParentFile();\n" +
                    "        //创建文件夹\n" +
                    "        mkdirChecked(cache);\n" +
                    "        mkdirChecked(dexDir);\n" +
                    "        FileFilter filter = new FileFilter() {\n" +
                    "            public boolean accept(File pathname) {\n" +
                    "                return !pathname.getName().startsWith(extractedFilePrefix);\n" +
                    "            }\n" +
                    "        };\n" +
                    "        File[] files = dexDir.listFiles(filter);\n" +
                    "        if(files == null) {\n" +
                    "            Log.w(\"MultiDex\", \"Failed to list secondary dex dir content (\" + dexDir.getPath() + \").\");\n" +
                    "        } else {\n" +
                    "            File[] arr$ = files;\n" +
                    "            int len$ = files.length;\n" +
                    "\n" +
                    "            for(int i$ = 0; i$ < len$; ++i$) {\n" +
                    "                File oldFile = arr$[i$];\n" +
                    "                Log.i(\"MultiDex\", \"Trying to delete old file \" + oldFile.getPath() + \" of size \" + oldFile.length());\n" +
                    "                if(!oldFile.delete()) {\n" +
                    "                    Log.w(\"MultiDex\", \"Failed to delete old file \" + oldFile.getPath());\n" +
                    "                } else {\n" +
                    "                    Log.i(\"MultiDex\", \"Deleted old file \" + oldFile.getPath());\n" +
                    "                }\n" +
                    "            }\n" +
                    "\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "自习看一下 `extract()` 做的事：\n" +
                    "\n" +
                    "```\n" +
                    "final class MultiDexExtractor {\n" +
                    "  private static void extract(ZipFile apk, ZipEntry dexFile, File extractTo, String extractedFilePrefix) throws IOException, FileNotFoundException {\n" +
                    "        InputStream in = apk.getInputStream(dexFile);\n" +
                    "        ZipOutputStream out = null;\n" +
                    "        //创建临时文件，地址/data/data/{packagename}/code_cache/secondary-dexes/{packagename}-1.apk.classes941893003.zip\n" +
                    "        File tmp = File.createTempFile(extractedFilePrefix, \".zip\", extractTo.getParentFile());\n" +
                    "        Log.i(\"MultiDex\", \"Extracting \" + tmp.getPath());\n" +
                    "    //变zip\n" +
                    "        try {\n" +
                    "            out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(tmp)));\n" +
                    "\n" +
                    "            try {\n" +
                    "                ZipEntry classesDex = new ZipEntry(\"classes.dex\");\n" +
                    "                classesDex.setTime(dexFile.getTime());\n" +
                    "                out.putNextEntry(classesDex);\n" +
                    "                byte[] buffer = new byte[16384];\n" +
                    "\n" +
                    "                for(int length = in.read(buffer); length != -1; length = in.read(buffer)) {\n" +
                    "                    out.write(buffer, 0, length);\n" +
                    "                }\n" +
                    "\n" +
                    "                out.closeEntry();\n" +
                    "            } finally {\n" +
                    "                out.close();\n" +
                    "            }\n" +
                    "      //extractTo的地址是 /data/data/{packagename}/code_cache/secondary-dexes/{packagename}-1.apk.classes2.zip\n" +
                    "            Log.i(\"MultiDex\", \"Renaming to \" + extractTo.getPath());\n" +
                    "            //重明明\n" +
                    "            if(!tmp.renameTo(extractTo)) {\n" +
                    "                throw new IOException(\"Failed to rename \\\"\" + tmp.getAbsolutePath() + \"\\\" to \\\"\" + extractTo.getAbsolutePath() + \"\\\"\");\n" +
                    "            }\n" +
                    "        } finally {\n" +
                    "            closeQuietly(in);\n" +
                    "            tmp.delete();\n" +
                    "        }\n" +
                    "\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "执行完这里，`List files = MultiDexExtractor.load(context, e, dexDir, false);` 也就执行完了，继续看 `MultiDex.install()`：\n" +
                    "\n" +
                    "```\n" +
                    "public final class MultiDex {\n" +
                    "\n" +
                    "\n" +
                    "    public static void install(Context context) {\n" +
                    "        Log.i(\"MultiDex\", \"install\");\n" +
                    "        if(IS_VM_MULTIDEX_CAPABLE) {//针对ART\n" +
                    "            Log.i(\"MultiDex\", \"VM has multidex support, MultiDex support library is disabled.\");\n" +
                    "        } else if(VERSION.SDK_INT < 4) {\n" +
                    "            throw new RuntimeException(\"Multi dex installation failed. SDK \" + VERSION.SDK_INT + \" is unsupported. Min SDK version is \" + 4 + \".\");\n" +
                    "        } else {\n" +
                    "            try {\n" +
                    "                  //blablabla......\n" +
                    "          //dex将会输出到/data/data/{packagename}/code_cache/secondary-dexes目录。  \n" +
                    "                    File dexDir = new File(e.dataDir, SECONDARY_FOLDER_NAME);\n" +
                    "                    List files = MultiDexExtractor.load(context, e, dexDir, false);\n" +
                    "          //校验这些zip文件是否合法  \n" +
                    "                    if(checkValidZipFiles(files)) {\n" +
                    "                        //安装提取出来的zip文件  \n" +
                    "                        installSecondaryDexes(loader, dexDir, files);\n" +
                    "                    } else {//不合法的情况下强制load一遍\n" +
                    "                        Log.w(\"MultiDex\", \"Files were not valid zip files.  Forcing a reload.\");\n" +
                    "                         //最后一个参数是true，代表强制加载\n" +
                    "                        files = MultiDexExtractor.load(context, e, dexDir, true);\n" +
                    "                        //还是不合法的就抛异常\n" +
                    "                        if(!checkValidZipFiles(files)) {\n" +
                    "                            throw new RuntimeException(\"Zip files were not valid.\");\n" +
                    "                        }\n" +
                    "            //终于合法了，安装zip文件\n" +
                    "                        installSecondaryDexes(loader, dexDir, files);\n" +
                    "                    }\n" +
                    "                }\n" +
                    "            } catch (Exception var11) {\n" +
                    "                Log.e(\"MultiDex\", \"Multidex installation failure\", var11);\n" +
                    "                throw new RuntimeException(\"Multi dex installation failed (\" + var11.getMessage() + \").\");\n" +
                    "            }\n" +
                    "\n" +
                    "            Log.i(\"MultiDex\", \"install done\");\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "`installSecondaryDexes(loader, dexDir, files)` 比较重要，在这个方法里面进行将第二个 dex 的代码加载到程序中。\n" +
                    "\n" +
                    "```\n" +
                    "public final class MultiDex {\n" +
                    "  private static void installSecondaryDexes(ClassLoader loader, File dexDir, List<File> files) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IOException {\n" +
                    "        //分版本加载\n" +
                    "        if(!files.isEmpty()) {\n" +
                    "            if(VERSION.SDK_INT >= 19) {\n" +
                    "                MultiDex.V19.install(loader, files, dexDir);\n" +
                    "            } else if(VERSION.SDK_INT >= 14) {\n" +
                    "                MultiDex.V14.install(loader, files, dexDir);\n" +
                    "            } else {\n" +
                    "                MultiDex.V4.install(loader, files);\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "来看看 `MultiDex.V14.install(loader, files, dexDir);` :\n" +
                    "\n" +
                    "```\n" +
                    "public final class MultiDex {\n" +
                    "    private static final class V14 {\n" +
                    "    private static void install(ClassLoader loader, List<File> additionalClassPathEntries, File optimizedDirectory) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {\n" +
                    "            //optimizedDirectory地址是/data/data/{packagename}/code_cache/secondary-dexes\n" +
                    "            //反射找到PathClassLoader的pathList属性，是DexPathList类，这个属性是父类 BaseDexClassLoader\n" +
                    "            Field pathListField = MultiDex.findField(loader, \"pathList\");\n" +
                    "            Object dexPathList = pathListField.get(loader);\n" +
                    "            //dexElements为dexPathList的一个属性，是Element数组\n" +
                    "            MultiDex.expandFieldArray(dexPathList, \"dexElements\", makeDexElements(dexPathList, new ArrayList(additionalClassPathEntries), optimizedDirectory));\n" +
                    "        }\n" +
                    "\n" +
                    "    private static Object[] makeDexElements(Object dexPathList, ArrayList<File> files, File optimizedDirectory) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {\n" +
                    "          //反射拿到dexPathList的方法makeDexElements，private static Element[] makeDexElements(ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions)\n" +
                    "            Method makeDexElements = MultiDex.findMethod(dexPathList, \"makeDexElements\", new Class[]{ArrayList.class, File.class});\n" +
                    "            //调用方法，该方法的作用是通过传入的files去加载jar或者zip，封装成DexFile，在封装成Element返回\n" +
                    "            return (Object[])((Object[])makeDexElements.invoke(dexPathList, new Object[]{files, optimizedDirectory}));\n" +
                    "        }\n" +
                    "  }\n" +
                    "\n" +
                    "  private static void expandFieldArray(Object instance, String fieldName, Object[] extraElements) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {\n" +
                    "        //找到dexElements这个属性\n" +
                    "        Field jlrField = findField(instance, fieldName);\n" +
                    "        //classloader中原始的dexElements\n" +
                    "        Object[] original = (Object[])((Object[])jlrField.get(instance));\n" +
                    "        //new一个新的出来\n" +
                    "        Object[] combined = (Object[])((Object[])Array.newInstance(original.getClass().getComponentType(), original.length + extraElements.length));\n" +
                    "        //将原有的复制到新的里面去\n" +
                    "        System.arraycopy(original, 0, combined, 0, original.length);\n" +
                    "        //将第二个dex的复制到新的里面去\n" +
                    "        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);\n" +
                    "        //再塞回去\n" +
                    "        jlrField.set(instance, combined);\n" +
                    "    }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "至此，`MultiDex.install()` 分析完了，这里只讲一下V14的，在 Java 层的主要流程将第二个 dex 取出（现在只考虑两个 dex 的情况），整成 Zip 形式的，然后通过反射将 zip 的地址等参数封装起来再塞给 PathClassLoader 。为什么是 Zip ，因为在 BaseDexClassLoader 中 `DexFile.loadDex()` 只接受 jar 或者 zip。\n" +
                    "\n" +
                    "更多的可以参考：Github -- [BaseDexClassLoader](https://github.com/yydcdut/android_libcore/blob/kitkat/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java) [DexFile](https://github.com/yydcdut/android_libcore/blob/kitkat/dalvik/src/main/java/dalvik/system/DexFile.java) [DexPathList](https://github.com/yydcdut/android_libcore/blob/kitkat/dalvik/src/main/java/dalvik/system/DexPathList.java)\n" +
                    "\n" +
                    "## Jack\n" +
                    "\n" +
                    "通过 [Experimental New Android Tool Chain - Jack and Jill](http://tools.android.com/tech-docs/jackandjill) 查看到 build tools 21.1.1 开始就支持 `Jack` 了。\n" +
                    "\n" +
                    "那么我们直接使用文章给出的 jack 在 gradle 中的用法使用编译工程吧：\n" +
                    "\n" +
                    "```\n" +
                    "buildscript {\n" +
                    "\t//...\n" +
                    "    dependencies {\n" +
                    "        classpath 'com.android.tools.build:gradle:2.1.0-alpha3'\n" +
                    "    }\n" +
                    "}\n" +
                    "android {\n" +
                    "    //...\n" +
                    "    buildToolsVersion '21.1.2'\n" +
                    "  \tminSdkVersion 21\n" +
                    "    defaultConfig {\n" +
                    "    // Enable the experimental Jack build tools.\n" +
                    "    useJack = true\n" +
                    "    }\n" +
                    "    //...\n" +
                    "  compileOptions {\n" +
                    "    sourceCompatibility JavaVersion.VERSION_1_8\n" +
                    "    targetCompatibility JavaVersion.VERSION_1_8\n" +
                    "  }\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "但是在实际编译过程中出现了问题：\n" +
                    "> Error:Execution failed for task ':app:jillDebugPackagedLibraries'.\n" +
                    ">  Jack requires Build Tools 24.0.0 or later\n" +
                    "\n" +
                    "那么把 build tool 更新到 `24.0.0rc1` 再试试吧：\n" +
                    "\n" +
                    "```\n" +
                    "android {\n" +
                    "    ...\n" +
                    "    buildToolsVersion '24.0.0rc1'\n" +
                    "    ...\n" +
                    "}\n" +
                    "```\n" +
                    "\n" +
                    "重新编译，然后当 gradle 运行到 `compileDebugJavaWithJack` 的时候：\n" +
                    "\n" +
                    "> ERROR: Dex writing phase: classes.dex has too many IDs. Try using multi-dex\n" +
                    "> com.android.jack.api.v01.CompilationException: Dex writing phase: classes.dex has too many IDs. Try using multi-dex\n" +
                    "> \u200B\tat com.android.jack.api.v01.impl.Api01ConfigImpl$Api01CompilationTaskImpl.run(Api01ConfigImpl.java:113)\n" +
                    "> \u200B\tat com.android.builder.core.AndroidBuilder.convertByteCodeUsingJackApis(AndroidBuilder.java:1904)\n" +
                    "> \u200B\tat com.android.build.gradle.tasks.JackTask.doMinification(JackTask.java:148)\n" +
                    "> \u200B\tat com.android.build.gradle.tasks.JackTask.access$000(JackTask.java:73)\n" +
                    "> \u200B\tat com.android.build.gradle.tasks.JackTask$1.run(JackTask.java:112)\n" +
                    "> \u200B\tat com.android.builder.tasks.Job.runTask(Job.java:51)\n" +
                    "> \u200B\tat com.android.build.gradle.tasks.SimpleWorkQueue$EmptyThreadContext.runTask(SimpleWorkQueue.java:41)\n" +
                    "> \u200B\tat com.android.builder.tasks.WorkQueue.run(WorkQueue.java:223)\n" +
                    "> \u200B\tat java.lang.Thread.run(Thread.java:745)\n" +
                    "> Caused by: com.android.jack.JackAbortException: Dex writing phase: classes.dex has too many IDs. Try using multi-dex\n" +
                    "> \u200B\tat com.android.jack.backend.dex.DexFileWriter.run(DexFileWriter.java:90)\n" +
                    "> \u200B\tat com.android.jack.backend.dex.DexFileWriter.run(DexFileWriter.java:41)\n" +
                    "> \u200B\tat com.android.sched.scheduler.ScheduleInstance.runWithLog(ScheduleInstance.java:161)\n" +
                    "> \u200B\tat com.android.sched.scheduler.MultiWorkersScheduleInstance$SequentialTask.process(MultiWorkersScheduleInstance.java:442)\n" +
                    "> \u200B\tat com.android.sched.scheduler.MultiWorkersScheduleInstance$Worker.run(MultiWorkersScheduleInstance.java:162)\n" +
                    "> Caused by: com.android.jack.backend.dex.DexWritingException: Dex writing phase: classes.dex has too many IDs. Try using multi-dex\n" +
                    "> \u200B\tat com.android.jack.backend.dex.SingleDexWritingTool.write(SingleDexWritingTool.java:64)\n" +
                    "> \u200B\tat com.android.jack.backend.dex.DexFileWriter.run(DexFileWriter.java:87)\n" +
                    "> \u200B\t... 4 more\n" +
                    "> Caused by: com.android.jack.backend.dex.SingleDexOverflowException: classes.dex has too many IDs. Try using multi-dex\n" +
                    "> \u200B\t... 6 more\n" +
                    "> Caused by: com.android.jack.tools.merger.MethodIdOverflowException: Method ID overflow when trying to merge dex files\n" +
                    "> \u200B\tat com.android.jack.tools.merger.ConstantManager.addDexFile(ConstantManager.java:177)\n" +
                    "> \u200B\tat com.android.jack.tools.merger.JackMerger.addDexFile(JackMerger.java:69)\n" +
                    "> \u200B\tat com.android.jack.backend.dex.DexWritingTool.mergeDex(DexWritingTool.java:107)\n" +
                    "> \u200B\tat com.android.jack.backend.dex.SingleDexWritingTool.write(SingleDexWritingTool.java:62)\n" +
                    "> \u200B\t... 5 more\n" +
                    ">\n" +
                    "> \n" +
                    "\n" +
                    "还是得使用 multidex 。jack 不支持 `instantRun` ，同样 gradle 是 `2.1.0-alpha3` ，这里就需要 multidex 了。 65536 的根本问题并不在于  jack 身上，而在于指令集身上，jack 也是采用的 multidex 来解决这个问题的。\n" +
                    "\n" +
                    "\n" +
                    "更多 jack 的资料：\n" +
                    "\n" +
                    "* [Experimental New Android Tool Chain - Jack and Jill](http://tools.android.com/tech-docs/jackandjill)\n" +
                    "* [Jack (Java Android Compiler Kit)](https://source.android.com/source/jack.html)\n" +
                    "* [Java 8 Language Features](http://developer.android.com/intl/zh-tw/preview/j8-jack.html)\n" +
                    "* [The upcoming Jack & Jill compilers in Android](https://www.guardsquare.com/blog/the_upcoming_jack_and_jill_compilers_in_android)\n" +
                    "* [The New **Android** Compilers - Meet **Jack** And Jill](http://www.i-programmer.info/news/193-android/8072-the-new-android-compilers-meet-jack-and-jill.html)\n" +
                    "* [Hello World, meet our new experimental toolchain, Jack and Jill | Android Developers Blog](android-developers.blogspot.co.uk/2014/12/hello-world-meet-our-new-experimental.html)\n" +
                    "\n" +
                    "## 参考\n" +
                    "\n" +
                    "* [美团Android DEX自动拆包及动态加载简介](http://tech.meituan.com/mt-android-auto-split-dex.html)\n" +
                    "* [Android拆分与加载Dex的多种方案对比](http://mp.weixin.qq.com/s?__biz=MzAwNDY1ODY2OQ==&mid=207151651&idx=1&sn=9eab282711f4eb2b4daf2fbae5a5ca9a&3rd=MzA3MDU4NTYzMw==&scene=6#rd)\n" +
                    "* [其实你不知道MultiDex到底有多坑](http://blog.zongwu233.com/the-touble-of-multidex)\n" +
                    "* [Alex Lipov @ osom.info: Too many methods in main-dex?](http://blog.osom.info/2014/12/too-many-methods-in-main-dex.html)\n" +
                    "* [casidiablo/multidex](https://github.com/casidiablo/multidex)\n" +
                    "* [由Android 65K方法数限制引发的思考](http://jayfeng.com/2016/03/10/%E7%94%B1Android-65K%E6%96%B9%E6%B3%95%E6%95%B0%E9%99%90%E5%88%B6%E5%BC%95%E5%8F%91%E7%9A%84%E6%80%9D%E8%80%83/)";

}
