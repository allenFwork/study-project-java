package com.study;

/**
 * 树节点类
 */
class TreeNode {
    // 以英文单词为数据,只有26个字母,所以每个节点的子节点数量最多26
    final static int MAX_SIZE = 26;
    // 表示当前节点存的字母
    char data;
    // 表示是否为叶子节点
    boolean isEnd = false;
    // 表示子节点
    TreeNode[] children;

    public TreeNode() {
        // 因为英文最多26个字母
        children = new TreeNode[MAX_SIZE];
        isEnd = false;
    }
}

/**
 * 数据结构-字典树 TireTree
 */
public class TireTree {

    // redis 同时只会执行一条命令.inc 0 ++, 释放 setnx set 一个设置失效时间. lua zookeeper 节点一定有序 TCC MQ

    /**
     * 创建字典树, 单词全部转成小写
     *
     * @param node
     * @param str
     */
    public static void createTireTree(TreeNode node, String str) {
        /**
         * 技巧：将字符转为数字
         * ASCII码值：A => 65, a=>97 (对应关系)
         * 自定义规则：a对应的ascii码是97,所以用 -97 的值来表示字符
         * 			  a->0, b->1, c->2
         */
        char data[] = str.toCharArray();
        for (int i = 0; i < data.length; i++) {
            // 转成0~25之间的数字了 这里是一个技巧
            int location = data[i] - 'a';
            // 我们把英文字母存到一个数组里面0~25  a['a'] === a[97] => a[0] = 'a' 缩小空间，0+97
            if (node.children[location] == null) {
                node.children[location] = new TreeNode();
                node.children[location].data = data[i];
            }
            // 通过下面代码省去了使用递归
            node = node.children[location];
        }
        node.isEnd = true;
    }

    /**
     * 字典树中查找是否包含字符串
     * 查询时间复杂度：O(n)
     * @param str
     * @param node
     * @return
     */
    public static boolean findString(String str, TreeNode node) {
    	// 字符串转化为存储字符的数组
        char[] data = str.toCharArray();
        for (int i = 0; i < data.length; i++) {
        	// 计算此时字符串应该存储在数组的位置,及索引值
            int location = data[i] - 'a';
            if (node.children[location] != null) {
                node = node.children[location];
            } else {
                return false;
            }
        }
        // 返回是否是叶子节点
        return node.isEnd;
    }

    public static void main(String[] args) {
        String[] strings = {"java", "ps", "php", "ui", "css", "js"};
        TreeNode root = new TreeNode();
        for (String string : strings) {
            createTireTree(root, string);
        }
        System.out.println("插入完成");
        System.out.println(findString("java", root));
		// 找前缀就是自动补全
        System.out.println(findString("jav", root));
    }
}
