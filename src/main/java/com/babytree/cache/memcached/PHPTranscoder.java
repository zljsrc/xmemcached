package com.babytree.cache.memcached;
import net.rubyeye.xmemcached.transcoders.PrimitiveTypeTranscoder;

//定义针对PHP的数据处理器
abstract class PHPTranscoder extends PrimitiveTypeTranscoder<String> {
}
