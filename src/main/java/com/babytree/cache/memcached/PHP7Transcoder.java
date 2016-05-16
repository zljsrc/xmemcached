package com.babytree.cache.memcached;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;

//针对新版本的php-memcached，由于在PHP7中使用了这个版本，所以叫做PHP7
class PHP7Transcoder extends PHPTranscoder {

    final int COMPRESSION_THRESHOLD = 2000;
    final int COMPRESSION_MASK = 3 << 4;

    public String decode(CachedData d) {
        byte[] data = d.getData();
        int flag = d.getFlag();
        if ((flag & COMPRESSION_MASK) == COMPRESSION_MASK) {
            setCompressionMode(CompressionMode.ZIP);
            //解压缩时需要处理size_t hack，存储时开头使用了一个size_t保存了压缩前数据的长度，为了支持fastlz
            byte[] realdata = new byte[data.length - 4];
            System.arraycopy(data, 4, realdata, 0, data.length - 4);
            data = decompress(realdata);
        }
        return new String(data);
    }

    public CachedData encode(String o) {
        byte[] realdata = o.getBytes();
        byte[] data = realdata;
        int flag = 0;
        if (realdata.length > COMPRESSION_THRESHOLD) {
            //上面提到的size_t hack，将长度转换为四个字节
            byte[] lenbytes = new byte[4];
            for (int i = 0; i < lenbytes.length; i++) {
                lenbytes[i] = (byte) ((realdata.length >> (8 * i)) & 0xff);
            }
            //压缩数据
            setCompressionMode(CompressionMode.ZIP);
            realdata = compress(realdata);           
            //合并数据
            data = new byte[realdata.length + 4];
            System.arraycopy(lenbytes, 0, data, 0, 4);
            System.arraycopy(realdata, 0, data, 4, realdata.length);
            flag = COMPRESSION_MASK;
        }
        return new CachedData(flag, data);
    }
//
//	@Override
//	public String decode(CachedData arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public CachedData encode(String arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}