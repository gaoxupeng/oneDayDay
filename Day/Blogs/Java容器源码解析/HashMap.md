## 简单介绍

## 属性

```java
//默认初始容量必须为2的幂（此处默认为16） 
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

//最大容量为2的30次方
static final int MAXIMUM_CAPACITY = 1 << 30;

//在构造函数未指定时，使用的负载系数，即默认负载系数
 static final float DEFAULT_LOAD_FACTOR = 0.75f;

//当一个桶中的元素大于8时，进行树化
static final int TREEIFY_THRESHOLD = 8;

//当一个桶中元素小于等于6时，退化成链表
static final int UNTREEIFY_THRESHOLD = 6;

//当桶的个数达到64时，进行树化
static final int MIN_TREEIFY_CAPACITY = 64;

//桶，也即数组
transient Node<K,V>[] table;

//作为entrySet()的缓存
transient Set<Map.Entry<K,V>> entrySet;

//元素的数量
transient int size;

//修改次数，用于在迭代的时候执行快速失败策略
transient int modCount;

//当桶的使用数量达到多少时进行扩容，threshold = capacity * loadFactor
int threshold;

//装载因子
final float loadFactor;
```



## 创建

1.无参构造

```java
//无参构造指定默认的负载系数 0.75f
public HashMap() {
    this.loadFactor = DEFAULT_LOAD_FACTOR; 
}
```

