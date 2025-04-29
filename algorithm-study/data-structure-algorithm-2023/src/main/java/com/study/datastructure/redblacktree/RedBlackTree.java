package com.study.datastructure.redblacktree;

/**
 * <h3>红黑树</h3>
 */
public class RedBlackTree {

    enum Color {
        RED, BLACK;
    }

    Node root;

    static class Node {
        int key;
        Object value;
        Node left;
        Node right;
        Node parent;              // 父节点
        Color color = Color.RED;  // 颜色

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Node(int key) {
            this.key = key;
        }

        public Node(int key, Color color) {
            this.key = key;
            this.color = color;
        }

        public Node(int key, Color color, Node left, Node right) {
            this.key = key;
            this.color = color;
            this.left = left;
            this.right = right;
            if (left != null) {
                left.parent = this;
            }
            if (right != null) {
                right.parent = this;
            }
        }

        // 是否是左孩子
        boolean isLeftChild() {
            return parent != null && parent.left == this;
        }

        // 叔叔
        Node uncle() {
            if (parent == null || parent.parent == null) {
                return null;
            }
            if (parent.isLeftChild()) {
                return parent.parent.right;
            } else {
                return parent.parent.left;
            }
        }

        // 兄弟
        Node sibling() {
            if (parent == null) {
                return null;
            }
            if (this.isLeftChild()) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }

    // 判断红
    boolean isRed(Node node) {
        return node != null && node.color == Color.RED;
    }

    // 判断黑
    boolean isBlack(Node node) {
//        return !isRed(node);
        return node == null || node.color == Color.BLACK;
    }

    // 右旋 1. parent 的处理 2. 旋转后新根的父子关系
    private void rightRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.left;
        Node green = yellow.right;
        if (green != null) {
            green.parent = pink;
        }
        yellow.right = pink;
        yellow.parent = parent;
        pink.left = green;
        pink.parent = yellow;
        if (parent == null) {
            root = yellow;
        } else if (parent.left == pink) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    // 左旋
    private void leftRotate(Node pink) {
        Node parent = pink.parent;
        Node yellow = pink.right;
        Node green = yellow.left;
        if (green != null) {
            green.parent = pink;
        }
        yellow.left = pink;
        yellow.parent = parent;
        pink.right = green;
        pink.parent = yellow;
        if (parent == null) {
            root = yellow;
        } else if (parent.left == pink) {
            parent.left = yellow;
        } else {
            parent.right = yellow;
        }
    }

    /**
     * 新增或更新
     * <br>
     * 正常增、遇到红红不平衡进行调整
     *
     * @param key   键
     * @param value 值
     */
    public void put(int key, Object value) {
        Node p = root;
        // 新插入节点的父节点
        Node parent = null;
        while (p != null) {
            parent = p;
            if (key < p.key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                p.value = value; // 更新
                return;
            }
        }
        Node inserted = new Node(key, value);
        if (parent == null) {
            root = inserted;
        } else if (key < parent.key) {
            parent.left = inserted;
            inserted.parent = parent;
        } else {
            parent.right = inserted;
            inserted.parent = parent;
        }
        fixRedRed(inserted);
    }

    void fixRedRed(Node x) {
        // case 1 插入节点是根节点，变黑即可
        if (x == root) {
            x.color = Color.BLACK;
            return;
        }
        // case 2 插入节点父亲是黑色，无需调整
        if (isBlack(x.parent)) {
            return;
        }
        /* case 3 当红红相邻，叔叔为红时
            需要将父亲、叔叔变黑、祖父变红，然后对祖父做递归处理
        */
        Node parent = x.parent;
        Node uncle = x.uncle();
        Node grandparent = parent.parent;
        if (isRed(uncle)) {
            parent.color = Color.BLACK;
            uncle.color = Color.BLACK;
            grandparent.color = Color.RED;
            fixRedRed(grandparent);
            return;
        }

        // case 4 当红红相邻，叔叔为黑时
        if (parent.isLeftChild() && x.isLeftChild()) { // LL
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        } else if (parent.isLeftChild()) { // LR
            leftRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            rightRotate(grandparent);
        } else if (!x.isLeftChild()) { // RR
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        } else { // RL
            rightRotate(parent);
            x.color = Color.BLACK;
            grandparent.color = Color.RED;
            leftRotate(grandparent);
        }
    }

    /**
     * 删除
     * <br>
     * 正常删、会用到李代桃僵技巧、遇到黑黑不平衡进行调整
     *
     * @param key 键
     */
    public void remove(int key) {
        Node deleted = find(key);
        if (deleted == null) {
            return;
        }
        doRemove(deleted);
    }

    public boolean contains(int key) {
        return find(key) != null;
    }

    // 查找删除节点
    private Node find(int key) {
        Node p = root;
        while (p != null) {
            if (key < p.key) {
                p = p.left;
            } else if (p.key < key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    // 查找剩余节点
    private Node findReplaced(Node deleted) {
        if (deleted.left == null && deleted.right == null) {
            return null;
        }
        if (deleted.left == null) {
            return deleted.right;
        }
        if (deleted.right == null) {
            return deleted.left;
        }
        Node s = deleted.right;
        while (s.left != null) {
            s = s.left;
        }
        return s;
    }

    /*
     * 处理双黑 (case3、case4、case5)
     * 双黑含义：删除的节点 和 其孩子节点（这个被删除节点只有一个孩子/或者没有孩子） 都是黑色的。
     * 此时删除节点没了后，该路线会因为缺少一个黑色节点而失去平衡。
     */
    private void fixDoubleBlack(Node x) {
        // 此方法需要递归调用，所以第一步就编写终止条件，往上处理到root了肯定得结束。
        if (x == root) {
            return;
        }
        Node parent = x.parent;
        Node sibling = x.sibling();

        /**
         * case 3 兄弟节点是红色
         * 如果兄弟节点是红色的，那么要么该兄弟节点没有孩子节点；如果有孩子节点，那么一定是两个黑色的节点。
         * 通过旋转，将兄弟节点旋转上去，兄弟节点原来的一个孩子节点（及被删除节点的侄子节点）变为被删除节点的兄弟，他是黑色的。
         * 转变了 case 情况, case3 经过上述调整可以变为 case 4 和 case 5
         * 其中一种示例：
                       父节点
                     /      \
            被删除节点(黑)    被删除兄弟节点(红)
                             /          \
                      侄子1节点(黑)      侄子2节点(黑)

                        被删除兄弟节点(红)
                         /          \
                      父节点       侄子2节点(黑)
                     /      \
            被删除节点(黑)    侄子1节点(黑)
         *
         */
        if (isRed(sibling)) {
            if (x.isLeftChild()) {  // 被调整节点是左孩子，左旋父节点（类似上面注解的示例）
                leftRotate(parent);
            } else {                // 被调整节点是右孩子，右旋父节点
                rightRotate(parent);
            }
            // 旋转结束，需要变换颜色，保证平衡
            parent.color = Color.RED;
            sibling.color = Color.BLACK;
            // 被调整节点的兄弟节点颜色变成了黑色，再次递归调用，进入case4或case5
            fixDoubleBlack(x);
            return;
        }

        // 能走到这里说明兄弟节点不可能是红色的，一定是黑色的（为空 或 黑色节点 【两种情况】）
        if (sibling != null) { // 一定是case4或case5的情况
            /**
             *  case 4 兄弟是黑色, 两个侄子也是黑色
             */
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                sibling.color = Color.RED;
                if (isRed(parent)) {
                    parent.color = Color.BLACK;
                } else {
                    fixDoubleBlack(parent);
                }
            }

            /**
             * case 5 兄弟是黑色, 侄子有红色
             */
            else {
                // LL
                if (sibling.isLeftChild() && isRed(sibling.left)) {
                    rightRotate(parent);
                    sibling.left.color = Color.BLACK;
                    sibling.color = parent.color;
                }
                // LR
                else if (sibling.isLeftChild() && isRed(sibling.right)) {
                    // 兄弟节点的右孩子旋转完需要变色，但是如果放在旋转后执行，会出错。因为旋转后，兄弟节点找不到这个右节点了，所以先进行了变色处理。
                    sibling.right.color = parent.color;
                    // 先对兄弟节点进行左旋，将兄弟节点的右节点转上来（红色的），变为被调整节点的新的兄弟节点（变成了LL的情况了）
                    leftRotate(sibling);
                    rightRotate(parent);
                }
                // RL
                else if (!sibling.isLeftChild() && isRed(sibling.left)) {
                    sibling.left.color = parent.color;
                    rightRotate(sibling);
                    leftRotate(parent);
                }
                // RR
                else {
                    leftRotate(parent);
                    sibling.right.color = Color.BLACK;
                    sibling.color = parent.color;
                }
                // 父节点必须变为黑色，因为他最终会跑到被调整节点的那一条路上（被调整节点是黑色的，其被删除了，该路上上就少了一个黑色）
                parent.color = Color.BLACK;
            }
        } else {
            // @TODO 实际也不会出现，触发双黑后，兄弟节点不会为 null
            fixDoubleBlack(parent);
        }
    }

    private void doRemove(Node deleted) {
        Node replaced = findReplaced(deleted);
        Node parent = deleted.parent;

        // 1.删除节点没有左右孩子节点
        if (replaced == null) {
            if (deleted == root) {  // 1.1 case 1 删除的是根节点，并且这个根节点是没有左右孩子的
                root = null;
            } else {                // 1.2 删除不是根节点，但是这个节点没有左右孩子，即删除的是一个叶子节点

                if (isBlack(deleted)) {
                    // 复杂调整
                    fixDoubleBlack(deleted);
                } else {
                    // 红色叶子, 无需任何处理
                }

                /*
                 * 删除不是根节点，但是这个节点没有左右孩子，即删除的是一个叶子节点。
                 * 一下代码执行了删除改节点操作。（但是此操作结束，只是删除了，树并未平衡）
                 * 但是上面的代码会进行先一步的调整，此处在执行就不会出现不平衡的情况。
                 * 上一步调整的节点是删除节点，与 删除节点有一个子结点情况不一样。
                 */
                if (deleted.isLeftChild()) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
                deleted.parent = null; // 便于GC回收

            }
            return;
        }

        // 2.删除节点有一个孩子节点（有一个左节点 或者 有一个右节点）
        if (deleted.left == null || deleted.right == null) {
            if (deleted == root) {               // 第1.1步 case 1 删除的是根节点，并且这个根节点只有一个孩子节点
                /*
                 *  这种情况下，root节点肯定是黑色的，孩子节点必定是红色的，否则root节点的左右字数黑色没法平衡。
                 *  此处使用【李代桃僵】的技巧 将孩子节点的key和value替换根节点，并将根节点的左右孩子置为空
                 */
                root.key = replaced.key;
                root.value = replaced.value;
                // 将root节点左右孩子都置为null
                root.left = root.right = null;

            } else {                            // 第1.2步 删除不是根节点，但是该节点只有一个孩子节点
                /*
                 * 这种情况下，被删除节点deleted、其后继节点replaced(不可能为空的)，
                 * 将deleted的父节点指向对应的后继节点就是执行了删除操作了.（此操作结束，只是删除了，但是树并未平衡）
                 */
                if (deleted.isLeftChild()) {
                    parent.left = replaced;
                } else {
                    parent.right = replaced;
                }
                replaced.parent = parent;
                // 被删除的节点，左右孩子、父亲节点都置为null，有助于垃圾回收
                deleted.left = deleted.right = deleted.parent = null;

                if (isBlack(deleted) && isBlack(replaced)) {  // 这个所谓的双黑实质就是该路上少了一个黑色节点，向上处理
                    // 复杂处理 @TODO 实际不会有这种情况 因为只有一个孩子时 被删除节点是黑色 那么剩余节点只能是红色不会触发双黑
                    fixDoubleBlack(replaced);
                } else if (isBlack(deleted) && isRed(replaced)){
                    // case 2 删除是黑，剩下是红
                    replaced.color = Color.BLACK;
                } else {
                    // 删除的节点不是黑，不影响平衡，不需要进行操作
                }
            }
            return;
        }

        // 3.删除节点有两个孩子节点 （特殊情况，记为 case 0: 将有两个孩子的情况  =>  有一个孩子 或 没有孩子 的情况） 【李代桃僵的技巧】
        int t = deleted.key;
        deleted.key = replaced.key;
        replaced.key = t;

        Object v = deleted.value;
        deleted.value = replaced.value;
        replaced.value = v;
        doRemove(replaced);
    }
}