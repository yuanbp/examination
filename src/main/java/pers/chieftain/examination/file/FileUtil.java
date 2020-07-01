package pers.chieftain.examination.file;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chieftain
 * @date 2020/5/11 09:35
 */
public class FileUtil {

    public static BASE64Encoder encoder = new BASE64Encoder();
    public static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 读取文件内容为string
     * @param path
     * @return
     * @throws IOException
     */
    public static String readStr(String path) throws IOException {
        byte[] bytes = readFileNio(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 读取内容进行base64编码
     * @param path
     * @return
     * @throws IOException
     */
    public static String readBase64(String path) throws IOException {
        byte[] bytes = readFileNio(path);
        return encoder.encode(bytes);
    }

    /**
     * RandomAccessFile 直接读取,效率慢,不推荐
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFile(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        int buffSize = 1024 * 8;
        byte[] buff = new byte[buffSize];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = file.read(buff, 0, buffSize)) != -1) {
            bos.write(buff, 0, len);
        }
        bos.close();
        file.close();
        return bos.toByteArray();
    }

    /**
     * 通过BufferedInputStream读取文件内容
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] bufferReadFile(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bufferedInputStream.available());
        int buffSize = 1024 * 8;
        byte[] buffer = new byte[buffSize];
        int len;
        while ((len = inputStream.read(buffer, 0, buffSize)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        bufferedInputStream.close();
        inputStream.close();
        return bos.toByteArray();
    }

    /**
     * 通过NIO读取内容
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFileNio(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int capacity = 1024 * 8;
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        while (channel.read(buffer) > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                bos.write(buffer.get());
            }
            buffer.clear();
        }
        bos.close();
        channel.close();
        file.close();
        return bos.toByteArray();
    }

    /**
     * 通过NIO内存映射读取,建议大文件使用,超过2G会抛异常
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFileMapped(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        long size = channel.size();
        MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        while (mbb.hasRemaining()) {
            bos.write(mbb.get());
            mbb.clear();
            mbb.flip();
        }
        bos.close();
        channel.close();
        file.close();
        unmap(mbb);
        return bos.toByteArray();
    }

    /**
     * 超过2G应该进行分段内存映射,一般是读取一段处理一段,但全部读取也会导致超过byte数组最大长度导致异常
     * @param path
     * @return
     * @throws IOException
     */
    public static byte[] readFileMappedBig(String path) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "r");
        FileChannel channel = file.getChannel();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        long fileLength = channel.size();
        int number = (int) Math.ceil((double) fileLength / (double) Integer.MAX_VALUE);
        long preLength = 0;
        long regionSize = Integer.MAX_VALUE;// 映射区域的大小
        for (int i = 0; i < number; i++) {// 将文件的连续区域映射到内存文件映射数组中
            if (fileLength - preLength < (long) Integer.MAX_VALUE) {
                regionSize = fileLength - preLength;// 最后一片区域的大小
            }
            MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
            while (mbb.hasRemaining()) {
                bos.write(mbb.get());
                mbb.clear();
                mbb.flip();
            }
            unmap(mbb);
            preLength += regionSize;// 下一片区域的开始
        }
        bos.close();
        channel.close();
        file.close();
        return bos.toByteArray();
    }

    /**
     * 直接通过RandomAccessFile写文件,不建议使用
     * @param path
     * @param bytes
     * @throws IOException
     */
    public static void writeFile(String path, byte[] bytes) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        file.write(bytes);
        file.close();
    }

    /**
     * 通过BufferedOutputStream写文件
     * @param path
     * @param bytes
     * @throws IOException
     */
    public static void bufferWriteFile(String path, byte[] bytes) throws IOException {
        FileOutputStream out = new FileOutputStream(path);
        int capacity = 1024 * 8;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out, capacity);
        bufferedOutputStream.write(bytes);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        out.close();
    }

    /**
     * 通过NIO写文件
     * @param path
     * @param bytes
     * @throws IOException
     */
    public static void writeFileNio(String path, byte[] bytes) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        channel.write(buffer);
        buffer.clear();
        channel.close();
        file.close();
    }

    /**
     * 通过NIO内存映射写文件,建议大文件使用
     * @param path
     * @param bytes
     * @throws IOException
     */
    public static void writeFileMapped(String path, byte[] bytes) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        FileChannel channel = file.getChannel();
        MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
        mbb.put(bytes);
        channel.write(mbb);
        mbb.force();
        mbb.clear();
        channel.close();
        file.close();
        unmap(mbb);
    }

    /**
     * 大文件分段写
     * @param path
     * @param bytes
     * @throws IOException
     */
    public static void writeFileMappedBig(String path, byte[] bytes) throws IOException {
        RandomAccessFile file = new RandomAccessFile(path, "rw");
        FileChannel channel = file.getChannel();
        if(bytes.length >= (Integer.MAX_VALUE)) {
            List<byte[]> list = bytesSlice(bytes, Integer.MAX_VALUE);
            long preLength = 0;
            long regionSize = Integer.MAX_VALUE;
            for (byte[] subBytes : list) {
                if(bytes.length - preLength < Integer.MAX_VALUE) {
                    regionSize = bytes.length - preLength;
                }
                MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, preLength, regionSize);
                mbb.put(subBytes);
                channel.write(mbb);
                mbb.force();
                mbb.clear();
                unmap(mbb);
                preLength += regionSize;
            }
        } else {
            MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.length);
            mbb.put(bytes);
            channel.write(mbb);
            mbb.force();
            unmap(mbb);
        }
        channel.close();
        file.close();
    }

    /**
     * 大文件移动
     * @param from
     * @param to
     * @throws IOException
     */
    public static void fileCopy(String from, String to) throws IOException {
        RandomAccessFile srcFile = new RandomAccessFile(from, "r");
        FileChannel srcChannel = srcFile.getChannel();

        RandomAccessFile destFile = new RandomAccessFile(to, "rw");
        FileChannel destChannel = destFile.getChannel();

        long fileLength = srcChannel.size();
        int number = (int) Math.ceil((double) fileLength / (double) Integer.MAX_VALUE);
        long preLength = 0;
        long regionSize = Integer.MAX_VALUE;// 映射区域的大小
        for (int i = 0; i < number; i++) {// 将文件的连续区域映射到内存文件映射数组中
            if (fileLength - preLength < (long) Integer.MAX_VALUE) {
                regionSize = fileLength - preLength;// 最后一片区域的大小
            }
            MappedByteBuffer mbb = srcChannel.map(FileChannel.MapMode.READ_ONLY, preLength, regionSize);
            while (mbb.hasRemaining()) {
                // 下面两行代码效果一致
                srcChannel.transferTo(preLength, regionSize, destChannel);
//                destChannel.transferFrom(srcChannel, preLength, regionSize);
                mbb.force();
                mbb.clear();
                mbb.flip();
            }
            unmap(mbb);
            preLength += regionSize;// 下一片区域的开始
        }
        srcChannel.close();
        srcFile.close();
    }

    /**
     * 处理MappedByteBuffer内存回收问题,不建议使用
     * @param buffer
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void mappedClose(MappedByteBuffer buffer) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = FileChannelImpl.class.getDeclaredMethod("unmap", MappedByteBuffer.class);
        m.setAccessible(true);
        m.invoke(FileChannelImpl.class, buffer);
    }

    /**
     * 处理MappedByteBuffer内存回收问题
     * @param var0
     */
    private static void unmap(MappedByteBuffer var0) {
        Cleaner var1 = ((DirectBuffer)var0).cleaner();
        if (var1 != null) {
            var1.clean();
        }
    }

    public static List<byte[]> bytesSlice(byte[] bytes, int pieceSize) {
        int depth = ((bytes.length % pieceSize) == 0) ? (bytes.length / pieceSize) : ((bytes.length / pieceSize) + 1);
        List<byte[]> list = new ArrayList<>(depth);
        int length = bytes.length;
        int start = 0;
        int limit = pieceSize;
        for (int i = 0; i < depth; i++) {
            if(length - start < pieceSize) {
                limit = start + (length - start);
            }
            list.add(Arrays.copyOfRange(bytes, start, limit));
            start += pieceSize;
            limit = start + pieceSize;
        }
        return list;
    }
}
