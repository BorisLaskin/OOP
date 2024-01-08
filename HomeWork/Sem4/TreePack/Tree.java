package TreePack;

import java.util.ArrayList;

public class Tree<V extends Comparable<V>> {
    private Node root;

    private class Node {

        private V value;
        private Node left;
        private Node right;
        private Node parent;
        private boolean isBlack;
        private int Hight;

        private Node() {
            this.isBlack = false;
            this.Hight = 1;
        }

        private Node(V value) {
            this.isBlack = false;
            this.Hight = 1;
            this.value = value;
        }

        //------------------------------
        private void setToBlack() {
            this.isBlack = true;
        }

        private void setToRed() {
            this.isBlack = false;
        }

    }

    //------------------------------
    public void addValue(V value) {
        Node newNode = new Node(value);
        if (root == null) {
            newNode.setToBlack();
            root = newNode;
        } else {
            recursiveAdd(value, newNode, root);
            root.Hight = calculateHight(root);
        }
    }

    private boolean recursiveAdd(V value, Node insert, Node current) {
        if (current == null) {
            return true;
        } else {
            if (value.compareTo(current.value) < 0) {
                if (recursiveAdd(value, insert, current.left)) {
                    current.left = insert;
                    insert.parent = current;
                }
            } else {
                if (recursiveAdd(value, insert, current.right)) {
                    current.right = insert;
                    insert.parent = current;
                }
            }
            boolean[] subFlag = new boolean[]{true, false, false};
            boolean flag = subFlag[0] || subFlag[1] || subFlag[2];
            Node temp;
            while (flag) {
                temp = smallRightTurn(current);
                if (temp != null){
                    subFlag[0] = temp != current;
                    current = temp;
                }
                else subFlag[0] = false;

                if (current.left != null && current.left.left != null &&
                        !current.left.isBlack && !current.left.left.isBlack)
                    temp = smallLeftTurn(current);
                else temp = null;
                if (temp != null){
                    subFlag[1] = temp != current;
                    current = temp;
                }
                else subFlag[1] = false;

                subFlag[2] = swapColor(current);
                flag = subFlag[0] || subFlag[1] || subFlag[2];
            }
        }

        return false;
    }

    //------------------------------
    private boolean swapColor(Node current) {
        if (current.left != null && current.right != null) {
            if (!current.left.isBlack && !current.right.isBlack) {
                current.isBlack = false;
                current.left.isBlack = true;
                current.right.isBlack = true;
                if (current == root)
                    current.isBlack = true;
                return true;
            }
        }
        return false;
    }

    private Node smallLeftTurn(Node current) {
        if (current.left != null) {
            if (!current.left.isBlack) {
                Node tempParent = current.parent;
                Node tempLeft = current.left;

                rebaseNodeFromRightToLeft(current.left.right, current.left, current);
                tempLeft.right = current;
                current.parent = tempLeft;
                tempLeft.isBlack = current.isBlack;
                current.setToRed();
                tempLeft.parent = tempParent;

                if (tempParent == null) {
                    root = tempLeft;
                } else {
                    if (current == tempParent.left) tempParent.left = tempLeft;
                    else tempParent.right = tempLeft;
                }
                return tempLeft;
            }
        }
        return null;
    }

    private Node smallRightTurn(Node current) {
        if (current.right != null) {
            if (!current.right.isBlack) {
                Node tempParent = current.parent;
                Node tempRight = current.right;

                rebaseNodeFromLeftToRight(current.right.left, current.right, current);
                tempRight.left = current;
                current.parent = tempRight;
                tempRight.isBlack = current.isBlack;
                current.setToRed();
                tempRight.parent = tempParent;

                if (tempParent == null) {
                    root = tempRight;
                } else {
                    if (current == tempParent.left) tempParent.left = tempRight;
                    else tempParent.right = tempRight;
                }
                return tempRight;
            }
        }
        return null;
    }

    private void rebaseNodeFromLeftToLeft(Node node, Node parent, Node newParent) {
        parent.left = null;
        newParent.left = node;
        if (node != null) node.parent = newParent;
    }

    private void rebaseNodeFromLeftToRight(Node node, Node parent, Node newParent) {
        parent.left = null;
        newParent.right = node;
        if (node != null) node.parent = newParent;
    }

    private void rebaseNodeFromRightToLeft(Node node, Node parent, Node newParent) {
        parent.right = null;
        newParent.left = node;
        if (node != null) node.parent = newParent;
    }

    private void rebaseNodeFromRightToRight(Node node, Node parent, Node newParent) {
        parent.right = null;
        newParent.right = node;
        if (node != null) node.parent = newParent;
    }

    //------------------------------
    private int calculateHight(Node node) {
        int temp = 1;
        int tempLeft = 1;
        int tempRight = 1;
        if (node.left != null)
            tempLeft = calculateHight(node.left);
        if (node.right != null)
            tempRight = calculateHight(node.right);
        temp = Math.max(tempLeft, tempRight);
        if (node.isBlack) temp++;
        node.Hight = temp;
        return temp;
    }

    public boolean contains(V value) {
        Node node = root;
        while (node != null) {
            if (node.value.equals(value)) {
                return true;
            }
            if (node.value.compareTo(value) > 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    //------------------------------
    private ArrayList<Node> getALLItems() {
        ArrayList<Node> items = new ArrayList<>();
        items.add(root);
        int powderCount = 1;
        int nullItemCount = 0;
        int arrayCounter = 0;
        int itemsByLevel = 0;
        while (nullItemCount != Math.pow(2, powderCount)) {
            Node currentNode = items.get(arrayCounter);
            if (currentNode != null) {
                nullItemCount = 0;
                items.add(currentNode.left);
                items.add(currentNode.right);
            } else {
                items.add(null);
                items.add(null);
                nullItemCount++;
            }
            arrayCounter++;
            itemsByLevel++;
            if (itemsByLevel == Math.pow(2, powderCount) && nullItemCount < Math.pow(2, powderCount)) {
                itemsByLevel = 0;
                nullItemCount = 0;
                powderCount++;
            }
        }
        for (int i = items.size() - 1; i > (arrayCounter - nullItemCount); i--) {
            items.remove(i);
        }
        return items;
    }
    public void Print(){
        Print("");
    }
    public void Print(String string) {
        System.out.println(string);
        StringAlignUtils util = new StringAlignUtils(50, StringAlignUtils.Alignment.CENTER);
        ArrayList<Node> items = getALLItems();
        int powderCount = 0;
        int arrayCounter = 0;
        int itemsByLevel = 0;
        StringBuilder sb = new StringBuilder();
        while (arrayCounter < items.size()) {
            Node node = items.get(arrayCounter);
            if (node != null) {
                sb.append(node.value.toString());
                if (node.isBlack) sb.append("b");
                else sb.append("r");
                sb.append("-").append(node.Hight);
            } else sb.append("____");
            arrayCounter++;
            itemsByLevel++;
            if (itemsByLevel == Math.pow(2, powderCount)) {
                itemsByLevel = 0;
                powderCount++;
                String message = sb.toString();
                System.out.println(util.format(message));
                sb = new StringBuilder();
            } else sb.append(" ");
        }
    }
}