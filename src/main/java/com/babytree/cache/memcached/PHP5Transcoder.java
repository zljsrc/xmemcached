package com.babytree.cache.memcached;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;

//针对旧版本的php-memcached，由于在PHP5中使用了这个版本，所以叫做PHP5
class PHP5Transcoder extends PHPTranscoder {

    //使用压缩的最小长度
    final int COMPRESSION_THRESHOLD = 100;
    //压缩的标志位
    final int COMPRESSION_MASK = 1 << 1;

    //取出数据的处理
    public String decode(CachedData d) {
        byte[] data = d.getData();
        int flag = d.getFlag();
        //如果带有压缩标志位，则进行解压缩处理
        if ((flag & COMPRESSION_MASK) == COMPRESSION_MASK) {
            setCompressionMode(CompressionMode.ZIP);
            data = decompress(data);
        }
        return new String(data);
    }

    //存储数据的处理
    public CachedData encode(String o) {
        byte[] data = o.getBytes();
        int flag = 0;
        //如果数据超过了压缩的最小长度，则进行压缩，并更改标志位
        if (data.length > COMPRESSION_THRESHOLD) {
            setCompressionMode(CompressionMode.ZIP);
            data = compress(data);
            flag = COMPRESSION_MASK;
        }
        return new CachedData(flag, data);
    }

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