package com.luu;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Throwable {
        // write your code here

        if (args.length == 0) {
            System.err.println("please input your txt file");
//            return;
        }

        String fileName = args.length == 0 ? "test.txt" : args[0];
        String destName = fileName + ".md";
        int pageSize = 1000;

        File mdFile = new File(destName);
        if (mdFile.exists()){
            mdFile.delete();
        }
        mdFile.createNewFile();

        String encode = codeString(fileName);

        System.out.println("input txt dir is : " + fileName + ":" + encode);
        System.out.println("output md dir is : " + mdFile.getAbsolutePath());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),encode));

        BufferedWriter fileWriter = new BufferedWriter( new OutputStreamWriter(new FileOutputStream(mdFile),"UTF-8"));

        long lineNum = 0;

        while (true){

            if(lineNum++ % pageSize == 0){
                System.out.println("处理进度: " + lineNum);
                fileWriter.write("\r\n#  第" + lineNum / pageSize  + "段\r\n\r\n");
                fileWriter.flush();
            }


            String line = bufferedReader.readLine();
            if(null == line){
                break;
            }

            line = line.trim().replaceAll("\\*","").replaceAll("#", "").replaceAll("\\[","");

//            System.out.println(line);
            fileWriter.write(line);
            fileWriter.write("\r\n");

        }

        fileWriter.flush();
        bufferedReader.close();
        fileWriter.close();

        System.err.println("finish txt to markdown : " + mdFile.getAbsolutePath());

    }


    /**
     * 判断文件的编码格式
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String fileName) throws Exception{

        File file = new File(fileName);
        if(file==null || !file.exists()){
            System.out.println("文件不存在..."+file.getAbsolutePath());
            return null;
        }

        BufferedInputStream bin = new BufferedInputStream( new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII" ;
                break ;
            default:
                code = "GBK";
        }

        return code;
    }
}
