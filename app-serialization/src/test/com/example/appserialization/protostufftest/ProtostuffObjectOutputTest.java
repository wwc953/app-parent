/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.appserialization.protostufftest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.appserialization.kryo2.KryoSerializer;
import com.example.appserialization.kryo3.KryoUtil;
import com.example.appserialization.protostuff.ProtostuffObjectInput;
import com.example.appserialization.protostuff.ProtostuffObjectOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

public class ProtostuffObjectOutputTest {

    private ByteArrayOutputStream byteArrayOutputStream;
    private ProtostuffObjectOutput protostuffObjectOutput;
    private ProtostuffObjectInput protostuffObjectInput;
    private ByteArrayInputStream byteArrayInputStream;

    @BeforeEach
    public void setUp() throws Exception {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.protostuffObjectOutput = new ProtostuffObjectOutput(byteArrayOutputStream);
    }

    int size = 100;
    boolean flag = true;

    @Test
    public void testObjectList() throws IOException, ClassNotFoundException {
        List<SerializablePerson> args = new ArrayList<SerializablePerson>(size);
        for (int i = 0; i < size; i++) {
            SerializablePerson s = new SerializablePerson();
            if (flag) {
                s.setOneByte((byte) i);
                s.setAge(30);
                s.setBeginTime(new Date());
                s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
                s.setName("我是是" + i);
            }
            args.add(s);
        }
//        System.out.println("protostuff序列化前--"+JSONObject.toJSONString(args));
        long begin = System.currentTimeMillis();
        this.protostuffObjectOutput.writeObject(args);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        int bb = bytes.length / 1024;
        int mm = bb / 1024;
        System.out.println("protostuff序列化时间：" + (System.currentTimeMillis() - begin) + "ms,大小：" + bb + "KB," + mm + "MB");
        this.flushToInput();
        long begin2 = System.currentTimeMillis();
        List<SerializablePerson> serializedTime = (List<SerializablePerson>) protostuffObjectInput.readObject();
        System.out.println("protostuff反序列化时间：" + (System.currentTimeMillis() - begin2) + "ms,反序列化：" + serializedTime.size());
//        System.out.println("protostuff序列化后--"+JSONObject.toJSONString(serializedTime));

    }

    @Test
    public void KryoObject() {
        List<SerializablePerson> args = new ArrayList<SerializablePerson>(size);
        for (int i = 0; i < size; i++) {
            SerializablePerson s = new SerializablePerson();
            if (flag) {
                s.setOneByte((byte) i);
                s.setAge(30);
                s.setBeginTime(new Date());
                s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
                s.setName("我是是" + i);
            }
            args.add(s);
        }
//        System.out.println("Kryo序列化前--"+JSONObject.toJSONString(args));
        long begin = System.currentTimeMillis();
        byte[] bytes = KryoSerializer.serialize(args);
        int bb = bytes.length / 1024;
        int mm = bb / 1024;
        System.out.println("kryo序列化时间：" + (System.currentTimeMillis() - begin) + "ms,大小：" + bb + "KB," + mm + "MB");

        long begin2 = System.currentTimeMillis();
        List<SerializablePerson> deserialize = (List<SerializablePerson>) KryoSerializer.deserialize(bytes);
        System.out.println("kryo反序列化时间：" + (System.currentTimeMillis() - begin2) + "ms,反序列化：" + deserialize.size());
//        System.out.println("Kryo序列化后--"+JSONObject.toJSONString(deserialize));
    }

    @Test
    public void KryoObject3() {
        List<SerializablePerson> args = new ArrayList<SerializablePerson>(size);
        for (int i = 0; i < size; i++) {
            SerializablePerson s = new SerializablePerson();
            if (flag) {
                s.setOneByte((byte) i);
                s.setAge(30);
                s.setBeginTime(new Date());
                s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
                s.setName("我是是" + i);
            }
            args.add(s);
        }
//        System.out.println("Kryo序列化前--"+JSONObject.toJSONString(args));
        long begin = System.currentTimeMillis();
        byte[] bytes = KryoUtil.writeToByteArray(args);
        int bb = bytes.length / 1024;
        int mm = bb / 1024;
        System.out.println("kryo序列化时间：" + (System.currentTimeMillis() - begin) + "ms,大小：" + bb + "KB," + mm + "MB");

        long begin2 = System.currentTimeMillis();
        List<SerializablePerson> deserialize = (List<SerializablePerson>) KryoUtil.readFromByteArray(bytes);
        System.out.println("kryo反序列化时间：" + (System.currentTimeMillis() - begin2) + "ms,反序列化：" + deserialize.size());
//        System.out.println("Kryo序列化后--"+JSONObject.toJSONString(deserialize));
    }

//    public void Kryo(Class clazz) {
//        PooledKryoFactory pooledKryoFactory = new PooledKryoFactory();
//        List<SerializablePerson> args = new ArrayList<SerializablePerson>(100000);
//        for (int i = 0; i < 100000; i++) {
//            SerializablePerson s = new SerializablePerson();
//            s.setOneByte((byte) i);
//            s.setAge(30);
//            s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
//            s.setName("我是是" + i);
//            args.add(s);
//        }
//        Kryo kryo = pooledKryoFactory.getKryo();
//        SynchronizedCollectionsSerializer.registerSerializers(kryo);
////        CollectionSerializer serializer = new CollectionSerializer();
////        serializer.setElementClass(clazz, new BeanSerializer(kryo, clazz));
////        serializer.setElementsCanBeNull(true);
////        kryo.register(clazz, new BeanSerializer(kryo, clazz));
////        kryo.register(ArrayList.class, serializer);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        long begin = System.currentTimeMillis();
//        kryo.writeObject(new Output(outputStream, 1000000), args);
//        byte[] bytes = outputStream.toByteArray();
//        int bb = bytes.length / 1024;
//        int mm = bb / 1024;
//        System.out.println("kryo序列化时间：" + (System.currentTimeMillis() - begin) + "ms,大小：" + bb + "KB," + mm + "MB");
//
//
//        long begin2 = System.currentTimeMillis();
////        SynchronizedCollectionsSerializer.registerSerializers(kryo);
//        Input input = new Input(new ByteArrayInputStream(bytes), 10000000);
////        ArrayList serializedTime = kryo.readObject(input, ArrayList.class, serializer);
//        ArrayList arrayList = kryo.readObject(input,ArrayList.class);
//        System.out.println("kryo反序列化时间：" + (System.currentTimeMillis() - begin2) + "ms,反序列化：" + arrayList.size());
//
//    }

    @Test
    public void json() {
        List<SerializablePerson> args = new ArrayList<SerializablePerson>(size);
        for (int i = 0; i < size; i++) {
            SerializablePerson s = new SerializablePerson();
            if (flag) {
                s.setOneByte((byte) i);
                s.setAge(30);
                s.setBeginTime(new Date());
                s.setBigDecimal(new BigDecimal(i * 10 + Math.random()));
                s.setName("我是是" + i);
            }
            args.add(s);
        }
        long jsonbegin = System.currentTimeMillis();
        String s = JSON.toJSONString(args);
        byte[] bytes1 = s.getBytes();
        int bb1 = bytes1.length / 1024;
        int mm1 = bb1 / 1024;
        System.out.println("fastjson序列化时间：" + (System.currentTimeMillis() - jsonbegin) + "ms,大小：" + bb1 + "KB," + mm1 + "MB");

        long jsonbegin2 = System.currentTimeMillis();
        List<SerializablePerson> serializablePeople = JSONObject.parseArray(s, SerializablePerson.class);
        System.out.println("fastjson反序列化时间：" + (System.currentTimeMillis() - jsonbegin2) + "ms,反序列化：" + serializablePeople.size());

    }

    @Test
    public void testWriteObjectNull() throws IOException, ClassNotFoundException {
        this.protostuffObjectOutput.writeObject(null);
        this.flushToInput();

        assertThat(protostuffObjectInput.readObject(), nullValue());
    }

    @Test
    public void testListObject() throws IOException, ClassNotFoundException {

        List<SerializablePerson> list = new ArrayList<SerializablePerson>();

        list.add(new SerializablePerson());
        list.add(new SerializablePerson());
        list.add(new SerializablePerson());

        SerializablePersonList personList = new SerializablePersonList(list);

        this.protostuffObjectOutput.writeObject(personList);
        this.flushToInput();

        SerializablePersonList serializedTime = protostuffObjectInput.readObject(SerializablePersonList.class);
        assertThat(serializedTime, is(personList));
    }


    private void flushToInput() throws IOException {
        this.protostuffObjectOutput.flushBuffer();
        this.byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        this.protostuffObjectInput = new ProtostuffObjectInput(byteArrayInputStream);
    }

    private static class SerializablePersonList implements Serializable {
        private static final long serialVersionUID = 1L;

        public List<SerializablePerson> personList;

        public SerializablePersonList() {
        }

        public SerializablePersonList(List<SerializablePerson> list) {
            this.personList = list;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            SerializablePersonList list = (SerializablePersonList) obj;
            if (list.personList == null && this.personList == null)
                return true;
            if (list.personList == null || this.personList == null)
                return false;
            if (list.personList.size() != this.personList.size())
                return false;
            for (int i = 0; i < this.personList.size(); i++) {
                if (!this.personList.get(i).equals(list.personList.get(i)))
                    return false;
            }
            return true;
        }
    }

    private static class LocalTimeList implements Serializable {
        private static final long serialVersionUID = 1L;

        List<LocalTime> timeList;

        public LocalTimeList() {
        }

        public LocalTimeList(List<LocalTime> timeList) {
            this.timeList = timeList;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;

            LocalTimeList timeList = (LocalTimeList) obj;
            if (timeList.timeList == null && this.timeList == null)
                return true;
            if (timeList.timeList == null || this.timeList == null)
                return false;
            if (timeList.timeList.size() != this.timeList.size())
                return false;
            for (int i = 0; i < this.timeList.size(); i++) {
                if (!this.timeList.get(i).equals(timeList.timeList.get(i)))
                    return false;
            }
            return true;
        }
    }
}
