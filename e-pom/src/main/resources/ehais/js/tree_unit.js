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


function toTree(data) {
    // 删除 所有 children,以防止多次调用
    data.forEach(function (item) {
        delete item.children;
    });

    // 将数据存储为 以 id 为 KEY 的 map 索引数据列
    var map = {};
    data.forEach(function (item) {
        map[item.id] = item;
    });
//    console.log(map);

    var val = [];
    data.forEach(function (item) {

        // 以当前遍历项，的pid,去map对象中找到索引的id
        var parent = map[item.parent_id];

        // 好绕啊，如果找到索引，那么说明此项不在顶级当中,那么需要把此项添加到，他对应的父级中
        if (parent) {

            (parent.children || ( parent.children = [] )).push(item);

        } else {
            //如果没有在map中找到对应的索引ID,那么直接把 当前的item添加到 val结果集中，作为顶级
            val.push(item);
        }
    });

    return val;
}


function getTreeLevel(list,parentId){
	var data = [];
	var level = 0;
	for (let i = 0; i < list.length; i++) {
		if(list[i].parent_id == parentId){
			
			list[i].level = level;
			data.push(list[i]);
			getSubTreeLevel(data,list,list[i].id,level);
		}
	}
	return data;
}

function getSubTreeLevel(data,list,parentId,level){
	level ++;
	for (let i = 0; i < list.length; i++) {
		if(list[i].parent_id == parentId){			
			list[i].level = level;
			data.push(list[i]);
			getSubTreeLevel(data,list,list[i].id,level);
		}
	}
}

