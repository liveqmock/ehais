var  list = [
	  {id: 1, name: '超级管理', parent_id: 0},
	  {id: 2, name: '用户管理', parent_id: 1},
	  {id: 3, name: '部门管理', parent_id: 1},
	  {id: 4, name: '日志管理', parent_id: 1},
	  {id: 5, name: '操作用户', parent_id: 2},
	  {id: 6, name: '查看用户', parent_id: 2},
	  {id: 7, name: '用户新增', parent_id: 5},
	  {id: 8, name: '用户删除', parent_id: 5},
	  {id: 9, name: '用户修改', parent_id: 5},
	  {id: 10, name: '操控部门', parent_id: 3},
	  {id: 11, name: '查看部门', parent_id: 3},
	  {id: 12, name: '部门新增', parent_id: 10},
	  {id: 13, name: '部门删除', parent_id: 10},
	  {id: 14, name: '部门修改', parent_id: 10},
	  {id: 15, name: '日志查看', parent_id: 4},
	  {id: 16, name: '日志导出', parent_id: 4}
	];
/**
 * 树状的算法
 * @params list     代转化数组
 * @params parentId 起始节点
 */
function getTrees(list, parentId) {
    let items= {};
    // 获取每个节点的直属子节点，*记住是直属，不是所有子节点
    for (let i = 0; i < list.length; i++) {
         let key = list[i].parent_id;
         if (items[key]) {
             items[key].push(list[i]);
         } else {
             items[key] = [];
             items[key].push(list[i]);
         }
     }
     return formatTree(items, parentId);
}

/**
 * 利用递归格式化每个节点
 */
function formatTree(items, parentId) {
    let result = [];
    if (!items[parentId]) {
        return result;
    }
    for (let t of items[parentId]) {
        t.children = formatTree(items, t.id)
        result.push(t);
    }
  return result;
}
