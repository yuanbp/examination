package pers.chieftain.examination.file;

import java.io.IOException;

/**
 * @author chieftain
 * @date 2020/5/8 10:51
 */
public class FileTest {

    private static String read = "/Users/chieftain/Downloads/TheBacks/read.txt";
    private static String write = "/Users/chieftain/Downloads/TheBacks/write.txt";
    private static String readBig = "/Users/chieftain/Downloads/TheWins/cn_windows_7_ultimate_with_sp1_x64_dvd_u_677408.iso";
    private static String writeBig = "/Users/chieftain/Downloads/TheWins/cn_windows_7_ultimate_with_sp1_x64_dvd_u_677408.java.iso";

    public static void main(String[] args) throws IOException {
//        byte[] bytes = FileUtil.readFile(read);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//        FileUtil.writeFile(write, bytes);

//        byte[] bytes = FileUtil.bufferReadFile(read);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//        FileUtil.bufferWriteFile(write, bytes);

//        byte[] bytes = FileUtil.readFileNio(read);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//        FileUtil.writeFileNio(write, bytes);

//        byte[] bytes = FileUtil.readFileMapped(readBig);
//        FileUtil.writeFileMapped(writeBig, bytes);

//        byte[] bytes = FileUtil.readFileNio(read);
//        List<byte[]> list = FileUtil.bytesSlice(bytes, 3000);

//        byte[] bytes = FileUtil.readFileNio(read);
//        FileUtil.writeFileMappedBig(write, bytes);

//        byte[] bytes = FileUtil.readFileMappedBig(readBig);
//        FileUtil.writeFileMappedBig(writeBig, bytes);

        FileUtil.fileCopy(readBig, writeBig);
//        Files.copy(new File(readBig).toPath(), new File(writeBig).toPath());
    }
}
