# Overview

> This project is used to record my experience after reading the source code of JDK.

## Doing List:

- [ ] Arrays
- [ ] System
- [ ] Class
- [ ] Collections


## Done List:

### Collection

- [X] Iterable
- [X] Collection
- [X] AbstractCollection
- [X] List
- [X] AbstractList
- [X] ArrayList * 这里包含了很多 List操作，是一个数组
- [X] LinkedList * 主要实现了 Deque的设计思想
- [X] Queue
- [X] Deque * 一堆杂七杂八的... 包含了Queue的思想和Stack的思想
- [X] Iterator * 迭代器应该实现的一些方法
- [X] RandomAccess * 主要表示一个集合如果实现了该接口，则更适合随机访问；反之迭代器效率更好
- [X] CopyOnWriteArrayList * 写时上锁，并创建新的 list，这个新 list就是后面查询的

### Map

- [X] Map
- [X] Bindings * 等于是 key只能是 String的 Map
- [X] SimpleBindings
- [X] AbstractMap * 实现了 Map接口的大多数操作
- [X] IdentityHashMap * 引用相等为equal, 使用 Object[] 数组实现存储，连续两个index分别用来存储 key,value
- [X] HashMap * 使用 Entry[] 数组来存储数据， 基于 hash 来定位index， 冲突之后，加载 Entry.next下
- [X] LinkedHashMap * 使用一个 Entry header 作为游标，Entry内有属性 before/after，提供双向链表操作
- [X] ExpiringCache * 使用 HashMap存储，Entry里包含了一个过期时间，获取的时候会检查这个过期时间，如果过期了就删除。这个有存储数量上限
- [ ] ConcurrentMap
- [ ] ConcurrentHashMap * [参考 1][ConcurrentHashMap01], [参考 2](ConcurrentHashMap02)
- [X] SortedMap
- [X] NavigableMap
- [X] TreeMap 一个红黑树来维持顺序
- [ ] ConcurrentNavigableMap
- [ ] ConcurrentSkipListMap
- [X] WeakHashMap 内部的 Entry是继承的 WeakReference

## Thinking

1. List 类多用数组来存储数据，而对于数据的变更，熟练应用 `System.arraycopy` 来做数组的修改。

## Meaning:

`com.sun.java_cup.internal.runtime`  
内含了一个 LR 解析器

`com.sun.source.tree`  
存了一些关键词的解析，如 if for 之类的


[ConcurrentHashMap01]: https://www.ibm.com/developerworks/cn/java/java-lo-concurrenthashmap/index.html
[ConcurrentHashMap02]: http://www.infoq.com/cn/articles/ConcurrentHashMap
