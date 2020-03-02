更多命令请查看官方文档。

[TOC]

### String 字符串

- 同编程语言中的字符串非常相似。
- 字符串可以存储三种类型值
  - 字节串（byte string）
  - 整数
  - 浮点数

####  相关命令

  

|    命令     | 行为                                                     |
| :---------: | :------------------------------------------------------- |
|     get     | 获取存储在给定键中的值                                   |
|     set     | 设置存储在给定键中的值                                   |
|     del     | 删除存储在给定键中的值                                   |
|    incr     | incr key-name 将键存储的值加上1                          |
|    decr     | *                                                        |
|   incrby    | incrby key-name amout  将键存储的值加上整数 amout        |
|   decrby    | *                                                        |
| incrbyfloat | incrbyfloat key-name amout  将键存储的值加上浮点数 amout |

```js
阿里云:0>set hello word
"OK"
阿里云:0>get hello
"word"
阿里云:0>del hello
"1"
阿里云:0>get hello
null

```



### List 列表

- 列表可以有序的存储多个字符串。
- 实际应用
  - 存储任务信息
  - 最近游览过的文档
  - 常用联系人信息
- 可以阻塞式的弹出命令。timeout
  - 消息传递
  - 任务队列

#### 相关命令

| 命令   | 行为                                   |
| ------ | -------------------------------------- |
| rpush  | 将给定值推入列表的右端                 |
| lrange | 获取列表在给定范围所有的值             |
| lindex | 获取列表在给定位置上的单个元素         |
| lpop   | 返回从列表的左端弹出的第一个值（删除） |

```javascript
阿里云:0>rpush list-key item
"1"
阿里云:0>rpush list-key item2
"2"
阿里云:0>rpush list-key item
"3"
阿里云:0>lrange list-key 0 -1 //索引从0开始，-1结束 等于所有的值
 1)  "item"
 2)  "item2"
 3)  "item"
阿里云:0>lindex list-key 1
"item2"
阿里云:0>lpop list-key
"item"
阿里云:0>lrange list-key 0 -1
 1)  "item2"
 2)  "item"
```



### Set 集合 

- 列表可以存储多个相同的字符串，而集合则通过使用散列表来保证自己存储的每个字符串都是各不相同的（这些散列表只有键，但没有与键相关联的值）
- 集合使用无序的方式存储，所以无法像集合那样从某一端弹出元素。
- 可以随机的删除，返回元素。
- 可以对多个集合进行交集，并集，差集运算。

#### 相关命令

| 命令      | 行为                           |
| --------- | ------------------------------ |
| sadd      | 添加元素                       |
| smembers  | 返回集合包含的所有元素（慎用） |
| sismember | 检查给定元素是否包含在集合中   |
| srem      | 如果给定元素在集合中，删除。   |

```javascript
阿里云:0>sadd set-key item
"1"
阿里云:0>sadd set-key item2
"1"
阿里云:0>sadd set-key item3
"1"
阿里云:0>smembers set-key
 1)  "item"
 2)  "item3"
 3)  "item2"
阿里云:0>sadd set-key item
"0"
阿里云:0>sismember set-key item4
"0"
阿里云:0>sismember set-key item
"1"
阿里云:0>srem set-key item2
"1"
阿里云:0>srem set-key item2
"0"
阿里云:0>smembers set-key
 1)  "item"
 2)  "item3"

```



### Hash 散jianzhi列

- 可以存储多个键值之间的映射
- 使用于将一些相关的数据数据存储在一起。

#### 相关命令

| 命令    | 行为                           |
| ------- | ------------------------------ |
| hset    | 关联给定的键值对               |
| hget    | 获取指定散列键的值             |
| hgetall | 获取散列包含所有的键值对       |
| hdel    | 如果散列存储该键，删除对应的键 |

```javascript
阿里云:0>hset hash-key sub-key1 value1
"1"
阿里云:0>hset hash-key sub-key2 value2
"1"
阿里云:0>hset hash-key sub-key1 value1
"0"
阿里云:0>hgetall hash-key
 1)  "sub-key1"
 2)  "value1"
 3)  "sub-key2"
 4)  "value2"
阿里云:0>hdel hash-key sub-key1
"1"
阿里云:0>hdel hash-key sub-key1
"0"
阿里云:0>hgetall hash-key
 1)  "sub-key2"
 2)  "value2"
阿里云:0>hget hash-key sub-key2
"value2"
```



### Zset 有序集合

- 有序集合和散列一样都是用于存储键值对。
- 有序集合的键称为成员（member）,每个成员各不相同。
- 有序契合的值称为分值（score），分值必须为浮点数。
- 既可以根据成员访问元素（同散列），又可以根据分值及分值的排列顺序来访问元素。

#### 相关命令

| 命令          | 行为                                 |
| ------------- | ------------------------------------ |
| zadd          | 添加                                 |
| zrange        | 根据元素所处位置，获取集合中多个元素 |
| zrangebyscore | 获取有序集合在给定范围内的所有元素   |
| zrem          | 如果集合存在该成员，移除。           |

```javascript
阿里云:0>zadd zset-key 728 number1
"1"
阿里云:0>zadd zset-key 916 number2
"1"
阿里云:0>zadd zset-key 916 number2
"0"
阿里云:0>zadd zset-key 956 number2
"0"
阿里云:0>zrange zset-key 0 -1 withscores
 1)  "number1"
 2)  "728"
 3)  "number2"
 4)  "956"
阿里云:0>zrange zset-key 0 800 withscores
 1)  "number1"
 2)  "728"
 3)  "number2"
 4)  "956"
阿里云:0>zrange zset-key 0 728 withscores //包含728的元素
 1)  "number1"
 2)  "728"
 3)  "number2"
 4)  "956"
阿里云:0>zrem zset-key number1
"1"
阿里云:0>zrem zset-key number1
"0"
阿里云:0>zrange zset-key 0 -1 withscores
 1)  "number2"
 2)  "956"
```

![](https://img-blog.csdnimg.cn/20200302232037275.png)