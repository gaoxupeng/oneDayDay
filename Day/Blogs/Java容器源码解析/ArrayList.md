###  简单介绍

- 有下标,查询效率高
- 数组增删改方法涉及数组拷贝,效率低
- 方法未加锁及其他同步操作.线程不安全
- 使用频率很高,理解较为简单

### 创建

1. 无参构造

   ```java
   	/**
        * 共享的空数组实例，用于默认大小的空实例。
        *我们将其与EMPTY_ELEMENTDATA区别开来，以了解添加第一个元素时需要多少空间
        */
       private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {}; 
   
   
   	/**
        * Constructs an empty list with an initial capacity of ten.
        * 构造一个初始容量为10的空列表。（感觉这句注释并不准确。ArrayList在创建，未指定空间的话，默认是创	建一个空数组）
        */
       public ArrayList() {
           this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
       }
   ```
   
2. 有参构造

   ```java
   public ArrayList(int initialCapacity) {
       if (initialCapacity > 0) {
       	this.elementData = new Object[initialCapacity];
       } else if (initialCapacity == 0) { 
           //传入值==0，则使用EMPTY_ELEMENTDATA，区分无参构造DEFAULTCAPACITY_EMPTY_ELEMENTDATA
       	this.elementData = EMPTY_ELEMENTDATA;
       } else {
       	throw new IllegalArgumentException("Illegal Capacity: "+
       	initialCapacity);
       }
   }
   ```

3.传入某种类型的元素列表

```java
 //传入一个包含指定*集合元素的列表，然后迭代保证其元素的顺序
public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    if ((size = elementData.length) != 0) {
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    } else {
        // replace with empty array.
        this.elementData = EMPTY_ELEMENTDATA;
    }
 }
```



### 添加

- `private static final int DEFAULT_CAPACITY = 10;`指定了数组的初始容量，但当创建数组为空时，不会赋值与长度。等到添加第一个元素，才会赋值数组的长度为10

1. 不带下标的添加

   ```java
      //不带下标的添加，默认插入末尾
      //调用上面的公共方法
      public boolean add(E e) {
          ensureCapacityInternal(size + 1);  // Increments modCount!!
          elementData[size++] = e;
          return true;
      }
   ```

2. 添加的公共方法类（扩容相关）

```java

private void ensureCapacityInternal(int minCapacity) {
    ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
}

//得到最小扩容量
private static int calculateCapacity(Object[] elementData, int minCapacity) {
    //如果数组==空的数组，则比较默认容量与添加元素之和现在数组的长度+1，得较大的数返回
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) { 
        return Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    return minCapacity;
}
//判断是否需要扩容
private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);//扩容方法
}

//要分配的数组的最大大小。 一些虚拟机在数组中保留一些标题字。尝试分配更大的阵列可能会导致     		//OutOfMemoryError：请求的阵列大小超出VM限制
private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

//ArrayList的扩容方法（重点！！！！）
//增加容量，以确保它至少可以容纳最小容量参数指定的*个元素。
//@param minCapacity所需的最小容量
private void grow(int minCapacity) {
    // overflow-conscious code
    //定义未添加数组的长度为旧值
    int oldCapacity = elementData.length;
    
    //定义旧值，jdk8版本之后位移运算1位后的值，也就是1.5倍的值为新值
    //oldCapacity >> 1 == oldCapacity除以2
    //注意区分下jdk的版本
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    
    //新容量是否小于最小容量
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    
    // 如果新容量大于 MAX_ARRAY_SIZE,进入(执行) `hugeCapacity()` 方法来比较 minCapacity 和 MAX_ARRAY_SIZE，
       //如果minCapacity大于最大容量，则新容量则为`Integer.MAX_VALUE`，否则，新容量大小则为 MAX_ARRAY_SIZE 即为 `Integer.MAX_VALUE - 8`。
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

3.  带下标的添加

   ```java
   public void add(int index, E element) {
       //判断是否越界
       rangeCheckForAdd(index);
       //调用2的公共方法
       ensureCapacityInternal(size + 1);  // Increments modCount!!
       //复制数组（也是arraylist不适合大规模数据增删改的原因）
    	//参数解析省略了，很简单
       System.arraycopy(elementData, index, elementData, index + 1,
                        size - index);
       elementData[index] = element;
       size++;
   }
   ```

### 删除

   

```java
	//删除对应下标元素，后续元素左移。涉及了数组拷贝	
    public E remove(int index) {
        //检查了是否大于数组最大值
        rangeCheck(index);
        //这里没懂，后期理解，会补上
        modCount++;
        E oldValue = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
```

### 值得推敲

1. `void ensureCapacity(int minCapacity)`是ArrayList的一个方法,但类中并没有被调用,是个被用户调用的方法.按照方法的注释:**如有必要,增加AyyayList实例的容量,以确保它至少能容纳最小容量参数的元素数量**
2. 按照网上的一些解释,**通过在`add`大量元素前,调用此方法,以减少增量重新分配的次数.即优化了添加方法**.

3. 实例代码

    

   ```java
   public class Demo1 {
   	public static void main(String[] args) {
   		ArrayList<Object> list = new ArrayList<Object>();
   		final int num = 10000000;
   		long startTime = System.currentTimeMillis();
   		for (int i = 0; i < num; i++) {
   			list.add(i);
   		}
   		long endTime = System.currentTimeMillis();
   		System.out.println("使用ensureCapacity方法前："+(endTime - startTime));
   
   	}
   }
   
   
   public class Demo2 {
   	public static void main(String[] args) {
   		ArrayList<Object> list = new ArrayList<Object>();
   		final int num = 10000000;
           list.ensureCapacity(num);  //注意此处不同
   		long startTime = System.currentTimeMillis();
   		for (int i = 0; i < num; i++) {
   			list.add(i);
   		}
   		long endTime = System.currentTimeMillis();
   		System.out.println("使用ensureCapacity方法前："+(endTime - startTime));
   
   	}
   }
   ```

   上图代码,demo2的执行时间要小于demo1的执行时间,这也与网上一些看法结论一致.但当我把 n的数值改为1亿(代码中是1千万),则结论反过来,demo1的执行时间要小于demo2的执行时间.目前这里我还没有想明白.