package com.jamie.framework.treenode;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lizheng
 * @date: 19:36 2020/01/07
 * @Description: TreeNode
 */
@Data
public class TreeNode {
    private String id;
    private String text;
    private String parentId;
    private List<TreeNode> children;
    private Map<String, Object> metaMap;
}
