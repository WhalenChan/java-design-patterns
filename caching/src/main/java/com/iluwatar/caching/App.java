/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.caching;

import lombok.extern.slf4j.Slf4j;

/**
 * The Caching pattern describes how to avoid expensive re-acquisition of resources by not releasing
 * the resources immediately after their use. The resources retain their identity, are kept in some
 * fast-access storage, and are re-used to avoid having to acquire them again. There are four main
 * caching strategies/techniques in this pattern; each with their own pros and cons. They are;
 * <code>write-through</code> which writes data to the cache and DB in a single transaction,
 * <code>write-around</code> which writes data immediately into the DB instead of the cache,
 * <code>write-behind</code> which writes data into the cache initially whilst the data is only
 * written into the DB when the cache is full, and <code>cache-aside</code> which pushes the
 * responsibility of keeping the data synchronized in both data sources to the application itself.
 * The <code>read-through</code> strategy is also included in the mentioned four strategies --
 * returns data from the cache to the caller <b>if</b> it exists <b>else</b> queries from DB and
 * stores it into the cache for future use. These strategies determine when the data in the cache
 * should be written back to the backing store (i.e. Database) and help keep both data sources
 * synchronized/up-to-date. This pattern can improve performance and also helps to maintain
 * consistency between data held in the cache and the data in the underlying data store.
 *
 * <p>In this example, the user account ({@link UserAccount}) entity is used as the underlying
 * application data. The cache itself is implemented as an internal (Java) data structure. It adopts
 * a Least-Recently-Used (LRU) strategy for evicting data from itself when its full. The four
 * strategies are individually tested. The testing of the cache is restricted towards saving and
 * querying of user accounts from the underlying data store ( {@link DbManager}). The main class (
 * {@link App} is not aware of the underlying mechanics of the application (i.e. save and query) and
 * whether the data is coming from the cache or the DB (i.e. separation of concern). The AppManager
 * ({@link AppManager}) handles the transaction of data to-and-from the underlying data store
 * (depending on the preferred caching policy/strategy).
 * <p>
 * <i>{@literal App --> AppManager --> CacheStore/LRUCache/CachingPolicy --> DBManager} </i>
 * </p>
 *
 * <p>缓存模式描述了如何通过在使用后不立即释放资源来避免昂贵的资源重新获取。资源保留其身份，保存在一些快速访问的存储中，并重新使用以避免再次获取它们。
 * 这种模式有四种主要的缓存策略/技术；每个都有自己的优点和缺点。它们是：
 * <code>write-through</code> 在单个事务中将数据写入缓存和 DB；
 * <code>write-around</code> 将数据立即写入 DB 而不是缓存；
 * <code >write-behind</code> 最初将数据写入缓存，而数据仅在缓存已满时才写入 DB；
 * <code>cache-aside</code> 推动保持数据同步的责任在应用程序本身的两个数据源中；
 * <code>read-through</code> 策略也包含在上述四种策略中——将数据从缓存返回给调用者，<b>如果</b> 它存在 <b>否则</b>查询来自DB，并将其存储到缓存中以备将来使用。
 * 这些策略确定何时应将缓存中的数据写回后备
 * 存储（即数据库）并帮助保持两个数据源同步/最新。这种模式可以提高性能，还有助于保持缓存中保存的数据与底层数据存储中的数据之间的一致性。
 *
 * <p>在此示例中，用户帐户 ({@link UserAccount}) 实体用作底层应用程序数据。 缓存本身是作为内部 (Java) 数据结构实现的。
 * 它采用最近最少使用 (LRU) 策略，以便在数据已满时将其从自身中逐出。 这四种策略分别进行了测试。 缓存测试仅限于从底层数据存储 ({@link DbManager})
 * 保存和查询用户帐户。 主类（{@link App} 不知道应用程序的底层机制（即保存和查询）以及数据来自缓存还是数据库（即关注点分离）。
 * AppManager（{@link AppManager}) 处理进出底层数据存储的数据事务（取决于首选缓存策略/策略）。
 *
 * @see CacheStore
 * @see LruCache
 * @see CachingPolicy
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        AppManager.initDb(false); // VirtualDB (instead of MongoDB) was used in running the JUnit tests
        // and the App class to avoid Maven compilation errors. Set flag to
        // true to run the tests with MongoDB (provided that MongoDB is
        // installed and socket connection is open).
        AppManager.initCacheCapacity(3);
        var app = new App();

        //一起打印太乱 不易于理解 注释后分开执行
        app.useReadAndWriteThroughStrategy();
        app.useReadThroughAndWriteAroundStrategy();
        app.useReadThroughAndWriteBehindStrategy();
        app.useCacheAsideStrategy();
    }

    /**
     * Read-through and write-through.<br/>
     * 先从缓存读取，到数据直接返回，否则就从数据库读取并设置到缓存并返回;
     * 将数据写入缓存和D
     */
    public void useReadAndWriteThroughStrategy() {
        LOGGER.info("# CachingPolicy.THROUGH");
        AppManager.initCachingPolicy(CachingPolicy.THROUGH);

        var userAccount1 = new UserAccount("001", "John", "He is a boy.");

        AppManager.save(userAccount1);
        LOGGER.info(AppManager.printCacheContent());

        //以上是write-through 将数据写入缓存和DB

        //以下是read-through  先从缓存读取，到数据直接返回，否则就从数据库读取并设置到缓存并返回
        AppManager.find("001");
        AppManager.find("001");
    }

    /**
     * Read-through and write-around. <br/>
     * 缓存找不到 从DB里找到并设置到缓存；
     * 将数据立即写入 DB 而不是缓存
     */
    public void useReadThroughAndWriteAroundStrategy() {
        LOGGER.info("# CachingPolicy.AROUND");
        AppManager.initCachingPolicy(CachingPolicy.AROUND); //将数据立即写入 DB 而不是缓存

        var userAccount2 = new UserAccount("002", "Jane", "She is a girl.");

        AppManager.save(userAccount2);
        LOGGER.info(AppManager.printCacheContent()); //Cache Miss! 缓存里没有数据

        AppManager.find("002"); //缓存找不到 从DB里找到并设置到缓存
        LOGGER.info(AppManager.printCacheContent()); //缓存里有数据

        userAccount2 = AppManager.find("002"); //Cache Hit!
        userAccount2.setUserName("Jane G.");
        AppManager.save(userAccount2); //002 has been updated! Removing older version from cache... 修改之后删除缓存 保存缓存一致性
        LOGGER.info(AppManager.printCacheContent()); //缓存里没数据
        AppManager.find("002"); //# Cache Miss! 缓存找不到 从DB里找到并设置到缓存
        LOGGER.info(AppManager.printCacheContent()); //UserAccount(userId=002, userName=Jane G., additionalInfo=She is a girl.)
        AppManager.find("002"); // # Cache Hit!
    }

    /**
     * Read-through and write-behind. <br/>
     * 最初将数据写入缓存，而数据仅在缓存已满时才写入
     */
    public void useReadThroughAndWriteBehindStrategy() {
        LOGGER.info("# CachingPolicy.BEHIND");
        AppManager.initCachingPolicy(CachingPolicy.BEHIND);

        var userAccount3 = new UserAccount("003", "Adam", "He likes food.");
        var userAccount4 = new UserAccount("004", "Rita", "She hates cats.");
        var userAccount5 = new UserAccount("005", "Isaac", "He is allergic to mustard.");

        AppManager.save(userAccount3);
        AppManager.save(userAccount4);
        AppManager.save(userAccount5);
        LOGGER.info(AppManager.printCacheContent()); //打印上面的三组数据
        AppManager.find("003"); //# Cache Hit!
        LOGGER.info(AppManager.printCacheContent()); //打印上面三组数据


        UserAccount userAccount6 = new UserAccount("006", "Yasha", "She is an only child.");
        //1.Cache is FULL! Writing LRU data to DB...存储3 4 5； 2.Cache is FULL! Removing 004 from cache...
        AppManager.save(userAccount6);

        LOGGER.info(AppManager.printCacheContent()); //打印除了004外的三组数据

        //1.# Cache Miss! 2. Cache is FULL! Writing LRU data to DB...  3.Cache is FULL! Removing 005 from cache...
        AppManager.find("004");

        LOGGER.info(AppManager.printCacheContent()); //打印除了005外的三组数据
    }

    /**
     * Cache-Aside.<br/>
     * 保持数据同步的责任在应用程序本身的两个数据源中 <- 这是翻译 看不太明白 <br/>
     *
     * 以下是查资料：
     * 该模式是从数据仓库中将数据加载到缓存中，从而提高访问速度的一种模式。该模式可以有效的提高性能，
     * 同时也能一定程度上保证缓存中的数据和数据仓库中的数据的一致性，和同步数据到数据仓库中。
     */
    public void useCacheAsideStrategy() {
        LOGGER.info("# CachingPolicy.ASIDE");
        AppManager.initCachingPolicy(CachingPolicy.ASIDE);
        LOGGER.info(AppManager.printCacheContent()); //空

        var userAccount3 = new UserAccount("003", "Adam", "He likes food.");
        var userAccount4 = new UserAccount("004", "Rita", "She hates cats.");
        var userAccount5 = new UserAccount("005", "Isaac", "He is allergic to mustard.");
        AppManager.save(userAccount3); //只保存在数据库而不保存在缓存？
        AppManager.save(userAccount4);
        AppManager.save(userAccount5);

        LOGGER.info(AppManager.printCacheContent()); //空
        AppManager.find("003");
        LOGGER.info(AppManager.printCacheContent()); //打印003
        AppManager.find("004");
        LOGGER.info(AppManager.printCacheContent()); //打印003 004
    }
}
