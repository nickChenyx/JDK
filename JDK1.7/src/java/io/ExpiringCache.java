/*
 * Copyright (c) 2002, 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 */

package java.io;

import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;

// 2017年12月6日
/**
 * 有过期的缓存实现
 */
class ExpiringCache {
    private long millisUntilExpiration;
    private Map  map;
    // Clear out old entries every few queries
    private int queryCount;
    // 达到这个值的时候，会清理一次过期 entry。
    private int queryOverflow = 300;
    // 最大的 entry 数量，达到了这个数值就无法存储新的值了。
    private int MAX_ENTRIES = 200;

    static class Entry {
        private long   timestamp;
        private String val;

        Entry(long timestamp, String val) {
            this.timestamp = timestamp;
            this.val = val;
        }

        long   timestamp()                  { return timestamp;           }
        void   setTimestamp(long timestamp) { this.timestamp = timestamp; }

        String val()                        { return val;                 }
        void   setVal(String val)           { this.val = val;             }
    }

    ExpiringCache() {
        this(30000);
    }

    ExpiringCache(long millisUntilExpiration) {
        this.millisUntilExpiration = millisUntilExpiration;
        map = new LinkedHashMap() {
            protected boolean removeEldestEntry(Map.Entry eldest) {
              return size() > MAX_ENTRIES;
            }
          };
    }

    synchronized String get(String key) {
        if (++queryCount >= queryOverflow) {
            cleanup();
        }
        Entry entry = entryFor(key);
        if (entry != null) {
            return entry.val();
        }
        return null;
    }

    synchronized void put(String key, String val) {
        if (++queryCount >= queryOverflow) {
            cleanup();
        }
        Entry entry = entryFor(key);
        if (entry != null) {
            entry.setTimestamp(System.currentTimeMillis());
            entry.setVal(val);
        } else {
            map.put(key, new Entry(System.currentTimeMillis(), val));
        }
    }

    synchronized void clear() {
        map.clear();
    }

    private Entry entryFor(String key) {
        Entry entry = (Entry) map.get(key);
        if (entry != null) {
            long delta = System.currentTimeMillis() - entry.timestamp();
            if (delta < 0 || delta >= millisUntilExpiration) {
                map.remove(key);
                entry = null;
            }
        }
        return entry;
    }

    private void cleanup() {
        Set keySet = map.keySet();
        // Avoid ConcurrentModificationExceptions
        String[] keys = new String[keySet.size()];
        int i = 0;
        for (Iterator iter = keySet.iterator(); iter.hasNext(); ) {
            String key = (String) iter.next();
            keys[i++] = key;
        }
        for (int j = 0; j < keys.length; j++) {
            entryFor(keys[j]);
        }
        queryCount = 0;
    }
}
