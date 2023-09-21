package main.java.ru.geekbrains.lesson4;

import java.util.Iterator;

/**
 * Структура хэш-таблицы
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashMap<K, V> {

    //region Публичные методы

    public V put(K key, V value) {
        if (buckets.length * LOAD_FACTOR <= size)
            recalculate();

        int index = calculateBucketIndex(key);
        Bucket bucket = buckets[index];
        if (bucket == null) {
            bucket = new Bucket();
            buckets[index] = bucket;
        }

        Entity entity = new Entity();
        entity.key = key;
        entity.value = value;

        V buf = (V) bucket.add(entity);
        if (buf == null) {
            size++;
        }
        return buf;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (buckets.length > 0) {
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    result.append(buckets[i].toString());
                    result.append("; ");
                }
            }
        }
        return result.toString();
    }

    //endregion

    //region Методы

    private void recalculate() {
        size = 0;
        Bucket<K, V>[] old = buckets;
        buckets = new Bucket[old.length * 2];
        for (int i = 0; i < old.length; i++) {
            Bucket<K, V> bucket = old[i];
            if (bucket != null) {
                Bucket.Node node = bucket.head;
                while (node != null) {
                    put((K) node.value.key, (V) node.value.value);
                    node = node.next;
                }
            }
        }
    }

    private int calculateBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    //endregion

    //region Конструкторы

    public HashMap() {
        buckets = new Bucket[INIT_BUCKET_COUNT];
    }

    public HashMap(int initCount) {
        buckets = new Bucket[initCount];
    }

    //endregion

    //region Вспомогательные структуры

    /**
     * Элемент хэш-таблицы
     */
    class Entity {

        /**
         * Ключ
         */
        K key;

        /**
         * Значение
         */
        V value;

    }

    /**
     * Элемент массива (связный список) из которого состоит хэш-таблица
     */
    class Bucket<K, V> {


        /**
         * Указатель на первый элемент связного списка
         */
        private Node head;

        /**
         * Узел связного списка
         */
        class Node {

            /**
             * Ссылка на следующий узел (если имеется)
             */
            Node next;

            /**
             * Значение узла
             */
            Entity value;

        }

        public V add(Entity entity) {
            Node node = new Node();
            node.value = entity;

            if (head == null) {
                head = node;
                return null;
            }

            Node currentNode = head;
            while (true) {
                if (currentNode.value.key.equals(entity.key)) {
                    V buf = (V) currentNode.value.value;
                    currentNode.value.value = entity.value;
                    return buf;
                }
                if (currentNode.next != null) {
                    currentNode = currentNode.next;
                } else {
                    currentNode.next = node;
                    return null;
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            if (head != null) {
                Node currentNode = head;
                while (true) {
                    result.append(((K) currentNode.value.key).toString());
                    result.append(":");
                    result.append(((V) currentNode.value.value).toString());
                    if (currentNode.next == null) {
                        break;
                    }
                    result.append("; ");
                    currentNode = currentNode.next;
                }
            }
            return result.toString();
        }
    }

    //endregion

    //region Поля

    /**
     * Массив бакетов (связных списков)
     */
    private Bucket[] buckets;
    private int size;

    //endregion

    //region Константы

    private static final int INIT_BUCKET_COUNT = 16;
    private static final double LOAD_FACTOR = 0.5;

    //endregion
}