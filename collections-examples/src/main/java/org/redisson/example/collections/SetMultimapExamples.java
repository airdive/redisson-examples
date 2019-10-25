/**
 * Copyright (c) 2016-2019 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson.example.collections;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class SetMultimapExamples {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
        Config config = new Config();
        config.useSingleServer().setTimeout(1000000).setAddress("redis://192.168.3.76:6379");
        RedissonClient redisson = Redisson.create(config);
        
        RSetMultimap<String, Integer> multimap = redisson.getSetMultimap("myMultimap");
        multimap.put("1", 1);
        multimap.put("1", 2);
        multimap.put("1", 3);
        multimap.put("2", 5);
        multimap.put("2", 6);
        multimap.put("4", 7);
        multimap.put("3", 7);
        multimap.put("3", 8);
        
        RSet<Integer> values1 = multimap.get("1");
        RSet<Integer> values2 = multimap.get("2");
        RSet<Integer> values3 = multimap.get("3");
        RSet<Integer> values4 = multimap.get("4");
        RSet<Integer> tmp = multimap.get("tmp");
        System.out.println(values1.readUnion(values2.getName()));
//        System.out.println(values2.readUnion("3","4"));
//        System.out.println(values3.readAll());
        List<String> list = Arrays.asList(values2.getName(),values4.getName());
//        System.out.println(list);
        System.out.println(values2.readUnion(list.get(0),list.get(1)));
        System.out.println(tmp.readUnion((String[])list.toArray()));;

//        boolean hasEntry = multimap.containsEntry("1", 3);
//        Set<Entry<String, Integer>> entries = multimap.entries();
//        Collection<Integer> values = multimap.values();
//
//        boolean isRemoved = multimap.remove("1", 3);
//        Set<Integer> removedValues = multimap.removeAll("1");
//
//        Collection<? extends Integer> newValues = Arrays.asList(5, 6, 7, 8, 9);
//        boolean isNewKey = multimap.putAll("5", newValues);
//
//        Set<Integer> oldValues = multimap.replaceValues("2", newValues);
//        Set<Integer> allValues = multimap.getAll("2");
        
        redisson.shutdown();
    }
    
}
