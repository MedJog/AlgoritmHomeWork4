package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RedBlackTree {

        private Node root;
        // обработка рутовой ноды
        public boolean add(int value){
            if(root != null){
                boolean result = addNode(root, value);
                root = rebalance(root);
                root.color = Color.BLACK;
                return result;
            } else {
                root = new Node();
                root.color = Color.BLACK;
                root.value = value;
                return true;
            }
        }

        // ребалансировка
        private Node rebalance(Node node){
            Node result = node;
            boolean needRebalance;
            do{
                needRebalance = false;
                if(result.rightChild != null && result.rightChild.color == Color.RED &&
                        (result.leftChild == null || result.leftChild.color == Color.BLACK)){
                    needRebalance = true;
                    result = rightSwap(result);
                }
                if(result.leftChild != null && result.leftChild.color == Color.RED &&
                        result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED){
                    needRebalance = true;
                    result = leftSwap(result);
                }
                if(result.leftChild != null && result.leftChild.color == Color.RED &&
                        result.rightChild != null && result.rightChild.color == Color.RED){
                    needRebalance = true;
                    colorSwap(result);
                }
            }
            while (needRebalance);
            return result;
        }
        // добавление новой ноды
        private boolean addNode(Node node, int value){
            if (node.value == value){ // если такое значение уже есть возвращаем false
                return  false;
            }
            else {
                if (node.value > value){ // если родитель больше
                    if(node.leftChild != null){ //и его левый ребенок не null
                        boolean result = addNode(node.leftChild, value); // поиск вниз по дереву для поиска места
                        node.leftChild = rebalance(node.leftChild); // нужно ли делать ребалансировку
                        return result;
                    }else{                              // если левого ребёнка нет
                        node.leftChild = new Node();    // создаём новую ноду
                        node.leftChild.color = Color.RED; // присваиваем ей красный цвет
                        node.leftChild.value = value;   // присваиваем значение
                        return true;
                    }
                }else {
                    if(node.rightChild != null){
                        boolean result = addNode(node.rightChild, value);
                        node.rightChild = rebalance(node.rightChild);
                        return result;
                    } else {
                        node.rightChild = new Node();
                        node.rightChild.color = Color.RED; // для новых нод цвет всегда красный
                        node.rightChild.value = value;
                        return  true;
                    }
                }
            }
        }
        // левосторонний перевод
        private Node leftSwap(Node node){
            Node leftChild = node.leftChild;
            Node betweenChild = leftChild.rightChild;
            leftChild.rightChild = node;
            node.leftChild = betweenChild;
            leftChild.color = Color.RED;
            return leftChild;
        }
        // правосторонний поворот
        private Node rightSwap(Node node){
            Node rightChild = node.rightChild;
            Node betweenChild = rightChild.leftChild;
            rightChild.leftChild = node;
            node.rightChild = betweenChild;
            rightChild.color = node.color;
            node.color = Color.RED;
            return rightChild;
        }

        // смена цвета
        // происходит только в ситуациях, когда у ноды два красных ребёнка
        private void colorSwap(Node node){
            node.rightChild.color = Color.BLACK;
            node.leftChild.color = Color.BLACK;
            node.color = Color.RED;
        }


        private class Node{
            private int value;
            private Color color;
            private Node leftChild;
            private Node rightChild;

            @Override
            public String toString(){
                return "Node{" +
                        "value = " + value +
                        ", color = " + color +
                        "}";
            }
        }
        private enum Color{
            RED, BLACK
        }

        public static void main(String[] args) {
            final RedBlackTree tree = new RedBlackTree();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
                while (true){
                    try{
                        int value = Integer.parseInt(reader.readLine());
                        tree.add(value);
                        System.out.println("finish");
                        System.out.println();
                    } catch (IOException ignored){

                    }
                }
            }catch (IOException e){
                throw new RuntimeException(e);

            }
        }
    }
